package com.programs.MR.InputFormatKeyValue;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Driver {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		conf.set("mapreduce.input.keyvaluelinerecordreader.key.value.separator",",");
		Job job = new Job(conf);		
		job.setJobName("crickMAX");
		job.setInputFormatClass(KeyValueTextInputFormat.class);
		//job.setNumReduceTasks(0);
		job.setJarByClass(Driver.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		job.setMapperClass(MaxScoreKVMapper.class);		
		job.setReducerClass(MaxScoreReducer.class);		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));	
		FileSystem.get(conf).delete(new Path(args[1]), true);
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
