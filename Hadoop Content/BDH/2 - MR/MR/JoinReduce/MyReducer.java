package com.programs.MR.JoinReduce;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MyReducer extends Reducer<IntWritable, Text, Text, Text>
{
	public void reduce(IntWritable key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		String name = "";
		double total = 0;
		int count = 0;
		for(Text val:values)
		{
			String parts[]= val.toString().split("\t");
			if(parts[0].equals("pscore"))
			{
				count ++;
				total += Float.parseFloat(parts[1]);				
			}
			else if(parts[0].equals("pname"))
			{
				name = parts[1];
			}
		}
		//String result = String.format("%d\t%f", "hello");
		String result = Double.toString(Math.round(total/count));
		context.write(new Text(name), new Text(result));
	}
}