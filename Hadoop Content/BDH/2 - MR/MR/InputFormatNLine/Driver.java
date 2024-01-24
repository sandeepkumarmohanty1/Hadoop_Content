package com.programs.MR.InputFormatNLine;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.NLineInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Driver {
	public static void main(String[] args)throws Exception {
		Configuration conf = new Configuration();
		Job job = new Job(conf, "Find Max Score");
		job.setJarByClass(Driver.class);
		job.setMapperClass(MaxScoreMapper.class);
		job.setReducerClass(MaxScoreReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		job.setInputFormatClass(NLineInputFormat.class);
		NLineInputFormat.addInputPath(job, new Path(args[0]));
		job.getConfiguration().setInt("mapreduce.input.lineinputformat.linespermap", 20);	
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		FileSystem.get(conf).delete(new Path(args[1]), true);
		System.exit(job.waitForCompletion(true) ? 0 : 1);
		}
}
