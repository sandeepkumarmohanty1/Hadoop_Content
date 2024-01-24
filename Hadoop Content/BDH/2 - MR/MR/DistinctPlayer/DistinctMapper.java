package com.programs.MR.DistinctPlayer;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class DistinctMapper extends Mapper<LongWritable,Text, Text, NullWritable> {
	private final static Text player = new Text();
    @Override
    public void map(LongWritable ignore, Text value, Context context)
        throws java.io.IOException, InterruptedException {
    	String line = value.toString();
	    String[] KeyAndVal = line.split("\t");	        
	    player.set(KeyAndVal[0]);
        context.write(player,NullWritable.get());
    }  
}