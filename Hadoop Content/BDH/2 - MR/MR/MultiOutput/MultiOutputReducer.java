package com.programs.MR.MultiOutput;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

public class MultiOutputReducer extends Reducer<Text, IntWritable, Text, IntWritable> 
{
	private MultipleOutputs<Text, IntWritable> mos = null;
	
	@Override
	public void setup(Context context)
	{
		mos = new MultipleOutputs<Text, IntWritable>(context);
	}
	@Override
	public void reduce(Text key, Iterable<IntWritable> values,Context context)
            throws IOException, InterruptedException {
		int totalScore= 0;
		int count = 0;
		int avgScore = 0;
		String outputFileName1 = "Below_10_AVG"; //set to absolute file path where this should write
		String outputFileName2 = "Below_20_AVG";	
		String outputFileName3 = "Other_AVG";
		for (IntWritable val : values)
		{	
			totalScore += val.get();
			count += 1;
			avgScore = totalScore/count;				
		}
		if (avgScore <= 10)
		{					
			mos.write("Avg10",key,avgScore,outputFileName1);
		}
		else if (avgScore <= 20)
		{
			mos.write("Avg20",key,avgScore,outputFileName2);
		}
		else
		{
			mos.write("Avgrest",key,avgScore,outputFileName3);
		}			
	}
	@Override
	protected void cleanup(Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		mos.close();
		super.cleanup(context);
	}
}
