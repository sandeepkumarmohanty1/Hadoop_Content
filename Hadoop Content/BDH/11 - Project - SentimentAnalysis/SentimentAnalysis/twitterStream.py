from pyspark import SparkContext
from pyspark.streaming import StreamingContext
from pyspark.streaming.kafka import KafkaUtils
from pyspark.sql import Row,SQLContext
import operator

def main():
    sc = SparkContext(appName="StreamingErrorCount")
    # Creating a streaming context with batch interval of 10 sec
    ssc = StreamingContext(sc, 10)
    ssc.checkpoint("checkpoint")
    pwords = load_wordlist("code/SentimentAnalysis/Dataset/positive.txt")
    nwords = load_wordlist("code/SentimentAnalysis/Dataset/negative.txt")
    counts = stream(ssc, pwords, nwords)
  
def load_wordlist(filename):
    """ 
    This function returns a list or set of words from the given filename.
    """ 
    words = {}
    f = open(filename, 'rU')
    text = f.read()
    text = text.split('\n')
    for line in text:
        words[line] = 1
    f.close()
    return words

def updateFunction(newValues, runningCount):
    if runningCount is None:
        runningCount = 0
    return sum(newValues, runningCount) 

def stream(ssc, pwords, nwords):   
    kstream = KafkaUtils.createStream(ssc, "localhost:5181","raw-event-streaming-consumer",{"twitterstream":1})
    tweets = kstream.map(lambda x: x[1])
    #Count the number of tweets per batch
    tweets.count().map(lambda x:'Tweets in this batch: %s' % x).pprint() 
   
    # Each element of tweets will be the text of a tweet.
    # We keep track of a running total counts and print it at every time step.
    words = tweets.flatMap(lambda line:line.split(" "))
    positive = words.map(lambda word: ('Positive', 1) if word in pwords else ('Positive', 0))
    negative = words.map(lambda word: ('Negative', 1) if word in nwords else ('Negative', 0))
    allSentiments = positive.union(negative)
    sentimentCounts = allSentiments.reduceByKey(lambda x,y: x+y)
    runningSentimentCounts = sentimentCounts.updateStateByKey(updateFunction)
    runningSentimentCounts.pprint()

    #COUNT HashTags using SPARK SQL
    # filter the words to get only hashtags, then map each hashtag to be a pair of (hashtag,1)
    hashtags = words.filter(lambda w: '#' in w).map(lambda x: (x, 1))
    # adding the count of each hashtag to its last count
    tags_totals = hashtags.updateStateByKey(aggregate_tags_count)
    # do processing for each RDD generated in each interval
    tags_totals.foreachRDD(process_rdd)

    # The counts variable hold the word counts for all time steps
    counts = []
    sentimentCounts.foreachRDD(lambda t, rdd: counts.append(rdd.collect()))
    
    # Start the computation
    ssc.start() 
    ssc.awaitTerminationOrTimeout(100)
    ssc.stop(stopGraceFully = True)
    return counts

#COUNT HashTags using SPARK SQL
def aggregate_tags_count(new_values, total_sum):
    return sum(new_values) + (total_sum or 0)

def get_sql_context_instance(spark_context):
    if ('sqlContextSingletonInstance' not in globals()):
        globals()['sqlContextSingletonInstance'] = SQLContext(spark_context)
    return globals()['sqlContextSingletonInstance']

def process_rdd(time, rdd):
    print("----------- %s -----------" % str(time))
    try:
    # Get spark sql singleton context from the current context
        sql_context = get_sql_context_instance(rdd.context)
    # convert the RDD to Row RDD
        row_rdd = rdd.map(lambda w: Row(hashtag=w[0], hashtag_count=w[1]))
    # create a DF from the Row RDD
        hashtags_df = sql_context.createDataFrame(row_rdd)
    # Register the dataframe as table
        hashtags_df.registerTempTable("hashtags")
    # get the top 10 hashtags from the table using SQL and print them
        hashtag_counts_df = sql_context.sql("select hashtag, hashtag_count from hashtags order by hashtag_count desc limit 10")
        hashtag_counts_df.show()   
    except:
        e = sys.exc_info()[0]
        print("Error: %s" % e)

if __name__=="__main__":
    main()
