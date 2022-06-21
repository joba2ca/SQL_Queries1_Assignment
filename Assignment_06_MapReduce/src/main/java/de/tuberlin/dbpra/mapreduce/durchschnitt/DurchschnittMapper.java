package de.tuberlin.dbpra.mapreduce.durchschnitt;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class DurchschnittMapper extends Mapper<LongWritable, Text, Text, Text> {

	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		Text outputValue = new Text();
		Text outputKey = new Text();
		String line = value.toString();
		String[] lineArray = line.split("[|]");

		outputKey.set(lineArray[1]);
		outputValue.set(lineArray[4]);

		context.write(outputKey, outputValue);
	}


}
