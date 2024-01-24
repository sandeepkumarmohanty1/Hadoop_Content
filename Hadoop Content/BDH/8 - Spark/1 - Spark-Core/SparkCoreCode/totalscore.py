from pyspark import SparkContext
sc = SparkContext(appName = "TotalScore")
text_file = sc.textFile("/home/notroot/datasets/CricketScore.txt")
counts = text_file.map(lambda x:(x.split("\t")[0],int(x.split("\t")[1]))).reduceByKey(lambda a, b: a + b)
counts.saveAsTextFile("/home/notroot/Output/") 

