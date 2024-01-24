import sys

from pyspark import SparkContext
from pyspark.streaming import StreamingContext

if __name__ == "__main__":

    sc = SparkContext(appName="StreamingWindowSum")
    ssc = StreamingContext(sc, 2)
    
    ssc.checkpoint("file:///tmp/spark")

    lines = ssc.socketTextStream(sys.argv[1], int(sys.argv[2]))

    sum = lines.reduceByWindow(
           lambda x, y: int(x) + int(y), 
    	   lambda x, y: int(x) - int(y),
    	   10, 2)
        #Unlike countByWindow which just counts the messages in DStream, reduceByWindow accepts lambda function for applying different 		operations like sum,avg etc. that to different operations for Summary and Inverse functions.
 	#Summary Function, operation applies to the new RDDs that gets added to DStream
	#Inverse Function, operation applies to the RDDs that leaves the stream
	# 10 is window interval and 2 is sliding interval.
    sum.pprint()
    ssc.start()
    ssc.awaitTermination()
