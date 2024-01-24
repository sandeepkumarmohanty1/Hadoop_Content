import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
object totalscore {
  def main(args: Array[String]) {
    // create Spark context with Spark configuration
    val sc = new SparkContext(new SparkConf().setAppName("Find Total Score"))
    //val input = sc.textFile("file:///home/notroot/SampleDataFile/CricketScore.txt")
    val input = sc.textFile("/user/notroot/SampleDataFile/CricketScore.txt")
    val a = input.flatMap(x => x.split(","))
    val b = a.map(x => (x.split("\t")(0), x.split("\t")(1).toInt))
    val res = b.reduceByKey((a, v) => a + v)
    res.saveAsTextFile("/user/notroot/spark-output-totalscore")
  }
}
