package com.programs.MR.JoinMap;

import java.net.URI;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Driver
{
	public static void main(String[] args) throws Exception {	
		//Job job = new Job(new Configuration(), "MAP Side Join Example");	
		//Configuration config = job.getConfiguration();
		Configuration config = new Configuration();
		DistributedCache.addCacheFile(new URI("SampleDataFile/PlayerID_Name.csv"), config);
		Job job = new Job(config, "MAP Side Join Example");			
		job.setJarByClass(Driver.class);		
		job.setMapperClass(MapJoinMapper.class);
		job.setReducerClass(MapJoinReducer.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);	
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		Path inputPath = new Path(args[0]);
		Path outputPath = new Path(args[1]);
		FileInputFormat.addInputPath(job, inputPath);
		FileOutputFormat.setOutputPath(job, outputPath);
		FileSystem.get(config).delete(new Path(args[1]), true);
		job.waitForCompletion(true);		
	}
}

