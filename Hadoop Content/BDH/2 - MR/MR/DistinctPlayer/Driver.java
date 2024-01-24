package com.programs.MR.DistinctPlayer;
import java.io.IOException;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Driver {
	public static void main(String[] args) 
            throws IOException, ClassNotFoundException, InterruptedException {

		Job job = new Job();
		job.setJarByClass(Driver.class);
		job.setJobName("RemoveDup");

		job.setMapperClass(DistinctMapper.class);
		job.setReducerClass(DistinctReducer.class);

		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);

		job.waitForCompletion(true);
	}
}
