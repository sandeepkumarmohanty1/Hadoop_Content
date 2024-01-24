package com.programs.MR.Partitioner;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class CustomPartitioner extends Partitioner<Text, IntWritable>
{
	public int getPartition(Text key, IntWritable value, int parttionnumber) {		
		String myKey = key.toString().toLowerCase();
		if(myKey.startsWith("s"))
		{
			return 0;
		}
		else if(myKey.startsWith("m"))
		{
			return 1;
		}
		else 
		{
			return 2;
		}
	}
}
