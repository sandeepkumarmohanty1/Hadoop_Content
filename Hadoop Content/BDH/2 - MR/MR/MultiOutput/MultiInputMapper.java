package com.programs.MR.MultiOutput;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MultiInputMapper extends Mapper<Object, Text, Text, IntWritable> 
{
	private final static Text player = new Text();
    private final static IntWritable score = new IntWritable();

	  public void map(Object key, Text value, Context context) 
	        throws IOException, InterruptedException {
	        String line = value.toString();
	        String[] KeyAndVal = line.split("\t");	        
	        player.set(KeyAndVal[0]);
	        score.set(Integer.valueOf(KeyAndVal[1]));
	        context.write(player, score);
	  }	
}
