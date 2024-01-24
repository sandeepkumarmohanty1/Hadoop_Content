package com.programs.MR.JoinReduce;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class PlayerScoreMapper extends Mapper<Object, Text, IntWritable, Text> {
	public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
		String record = value.toString();
		String parts[]=record.split(",");
		context.write(new IntWritable(Integer.valueOf(parts[0])), new Text("pscore\t" + parts[1]));
	}
}
