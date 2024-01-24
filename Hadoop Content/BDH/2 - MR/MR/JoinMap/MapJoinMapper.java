package com.programs.MR.JoinMap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class MapJoinMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	
	private Map<Integer, String> playerData = new HashMap<Integer, String>();
	private Text outputKey = new Text();
	//private Text outputValue = new Text();
	private IntWritable outputValue = new IntWritable();
	
	protected void setup(Context context) throws IOException
	{
		Path[] files = DistributedCache.getLocalCacheFiles(context.getConfiguration());
		for (Path p : files)
		{
			if (p.getName().equals("PlayerID_Name.csv"))
			{
				@SuppressWarnings("resource")
				BufferedReader reader = new BufferedReader(new FileReader(p.toString()));
				String line = reader.readLine();
				while(line!=null)
				{
					String[] token = line.split(",");
					int pid = Integer.valueOf(token[0]);
					String pname = token[1];
					playerData.put(pid, pname);
					line = reader.readLine();
				}
			}
		}
		if (playerData.isEmpty())
		{
			throw new IOException("Unable to load Player Data");
		}
	}
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String record = value.toString();
		String[] token = record.split(",");
		int pid = Integer.valueOf(token[0]);
		String pname = playerData.get(pid);
		int score = Integer.valueOf(token[1]);
		outputKey.set(pname);
		outputValue.set(score);
		context.write(outputKey,outputValue);
	}
}		