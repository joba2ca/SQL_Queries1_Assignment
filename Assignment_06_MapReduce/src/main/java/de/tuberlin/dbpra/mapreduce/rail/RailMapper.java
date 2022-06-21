package de.tuberlin.dbpra.mapreduce.rail;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class RailMapper extends Mapper<LongWritable, Text, Text, IntWritable> {


	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		IntWritable outputValue = new IntWritable();
		Text outputKey = new Text();
		String line = value.toString();
		String[] lineArray = line.split("[|]");

		if( lineArray[14].equals("RAIL")) {
			int anzahl = Integer.valueOf(lineArray[4]);
			String datumFull = lineArray[10];
			String datumOhneTag = datumFull.substring(0, 7);

			outputValue.set(anzahl);
			outputKey.set(datumOhneTag);

			context.write(outputKey, outputValue);
		}
	}
}
