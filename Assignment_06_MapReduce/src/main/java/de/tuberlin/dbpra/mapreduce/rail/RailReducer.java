package de.tuberlin.dbpra.mapreduce.rail;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class RailReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

	@Override
	public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
		IntWritable outputValue = new IntWritable();
		int gesamtAnzahl = 0;

		for(IntWritable it : values){
			gesamtAnzahl += it.get();
		}

		outputValue.set(gesamtAnzahl);

		context.write(key, outputValue);
	}


}
