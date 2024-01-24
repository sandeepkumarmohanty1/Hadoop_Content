import sys

from pyspark import SparkContext
from pyspark.streaming import StreamingContext

#if threading.current_thread().__class__.__name__ == '_MainThread'
if __name__ == "__main__":

    sc = SparkContext(appName="StreamingErrorCount")
    ssc = StreamingContext(sc, 10) # 10(seconds) is batch interval which forms an RDD with the data received in that timeframe.

    ssc.checkpoint("file:///tmp/spark") #checkpointing used for fault tolerance by backing of data for streaming process.

    lines = ssc.socketTextStream(sys.argv[1], int(sys.argv[2])) # socketTextStream is used to listen data from a soket that is passed from command line, first is host and second is port, here lines is a stream where data will constantly change per batch interval.

    counts = lines.flatMap(lambda line: line.split(" "))\
                  .filter(lambda word:"ERROR" in word)\
                  .map(lambda word: (word, 1))\
                  .reduceByKey(lambda a, b: a+b)

    counts.pprint()

    ssc.start()
    ssc.awaitTermination()
