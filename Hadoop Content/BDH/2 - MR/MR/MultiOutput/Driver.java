package com.programs.MR.MultiOutput;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class Driver {
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = new Job(conf, "Multi Output");
		job.setJarByClass(Driver.class);
		job.setMapperClass(MultiInputMapper.class);
		job.setReducerClass(MultiOutputReducer.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		FileSystem.get(conf).delete(new Path(args[1]), true);
		MultipleOutputs.addNamedOutput(job, "Avg10", TextOutputFormat.class, Text.class, IntWritable.class);
		MultipleOutputs.addNamedOutput(job, "Avg20", TextOutputFormat.class, Text.class, IntWritable.class);	
		MultipleOutputs.addNamedOutput(job, "Avgrest", TextOutputFormat.class, Text.class, IntWritable.class);	
		job.waitForCompletion(true);
	}
}
