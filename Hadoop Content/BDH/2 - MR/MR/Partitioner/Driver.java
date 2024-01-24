package com.programs.MR.Partitioner;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Driver {
	public static void main (String args[]) throws IOException, ClassNotFoundException, InterruptedException
	{
	Configuration conf = new Configuration();
	Job job = new Job(conf);
	job.setJobName("com.programs.MR.Partitioner Example");
	job.setJarByClass(Driver.class);
	//Set to run 3 Reducers
	job.setNumReduceTasks(3);		
	job.setMapperClass(MaxScoreMapper.class);
	job.setMapOutputKeyClass(Text.class);
	job.setMapOutputValueClass(IntWritable.class);		
	job.setReducerClass(MaxScoreReducer.class);
	job.setPartitionerClass(CustomPartitioner.class);		
	job.setOutputKeyClass(Text.class);
	job.setOutputValueClass(IntWritable.class);		
	FileInputFormat.addInputPath(job, new Path(args[0]));
	FileOutputFormat.setOutputPath(job, new Path(args[1]));
	FileSystem.get(conf).delete(new Path(args[1]), true);
	job.waitForCompletion(true);
	}
}
