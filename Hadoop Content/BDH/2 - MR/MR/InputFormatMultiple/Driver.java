package com.programs.MR.InputFormatMultiple;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class Driver extends Configured implements Tool {
	public int run(String[] args) throws Exception 
	{
		Configuration conf = new Configuration();
		Job job = new Job(conf, "MultiInput");
		job.setJarByClass(Driver.class);
		
		MultipleInputs.addInputPath(job,new Path(args[0]),TextInputFormat.class,Mapper1.class);
		MultipleInputs.addInputPath(job,new Path(args[1]),TextInputFormat.class,Mapper2.class);
		job.setReducerClass(MyReducer.class);		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		//job.setNumReduceTasks(0);
		FileOutputFormat.setOutputPath(job, new Path(args[2]));
		FileSystem.get(conf).delete(new Path(args[2]), true);		
		return (job.waitForCompletion(true) ? 0 : 1);
	}

	public static void main(String[] args) throws Exception {  
		int ecode = ToolRunner.run(new Driver(), args);
		System.exit(ecode);  
	}
}
