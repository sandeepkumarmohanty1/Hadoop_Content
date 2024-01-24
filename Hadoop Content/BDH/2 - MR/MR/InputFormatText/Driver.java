package com.programs.MR.InputFormatText;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class Driver implements Tool {
	private Configuration conf;
	public Configuration getConf() {
		return conf; // getting the configuration
	}
	public void setConf(Configuration conf) {
		this.conf = conf; // setting the configuration
	}
	public int run(String[] args) throws Exception {
		// initializing the job configuration
		Job findMaxScorejob = new Job(getConf());
		// setting the job name
		findMaxScorejob.setJobName("Find MaxScore");
		// to call this as a jar
		findMaxScorejob.setJarByClass(this.getClass());
		// setting custom mapper class
		findMaxScorejob.setMapperClass(MaxScoreMapper.class);
		// setting custom reducer class
		findMaxScorejob.setReducerClass(MaxScoreReducer.class);
		// setting mapper output key class: K2
		findMaxScorejob.setMapOutputKeyClass(Text.class);
		// setting mapper output value class: V2
		findMaxScorejob.setMapOutputValueClass(IntWritable.class);
		// setting reducer output key class: K3
		findMaxScorejob.setOutputKeyClass(Text.class);
		// setting reducer output value class: V3
		findMaxScorejob.setOutputValueClass(IntWritable.class);
		// setting the input format class ,i.e for K1, V1
		findMaxScorejob.setInputFormatClass(TextInputFormat.class);	
		// setting the output format class
		findMaxScorejob.setOutputFormatClass(TextOutputFormat.class);
		//setting number of reduce task
		findMaxScorejob.setNumReduceTasks(2);
		// setting the input file path
		FileInputFormat.addInputPath(findMaxScorejob, new Path(args[0]));
		// setting the output folder path
		FileOutputFormat.setOutputPath(findMaxScorejob, new Path(args[1]));
		Path outputpath = new Path(args[1]);
		// delete the output folder if exists
		outputpath.getFileSystem(conf).delete(outputpath, true);
		// to execute the job and return the status
		return findMaxScorejob.waitForCompletion(true) ? 0 : -1;
	}
	public static void main(String[] args) throws Exception {
		// start the job providing arguments and configurations
		ToolRunner.run(new Configuration(), new Driver(), args);
	}
}

