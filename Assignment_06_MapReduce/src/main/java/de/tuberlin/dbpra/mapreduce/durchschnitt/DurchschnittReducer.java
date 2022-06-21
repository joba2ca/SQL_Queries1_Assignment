package de.tuberlin.dbpra.mapreduce.durchschnitt;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class DurchschnittReducer extends Reducer<Text, Text, Text, DoubleWritable> {

	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		DoubleWritable outputValue = new DoubleWritable();
		Double anzahl = 0.0;
		int count = 0;

		for(Text t : values){
			String[] value_array = t.toString().split("[|]");
			if(value_array.length >= 2) {
				anzahl += Double.parseDouble(value_array[0]);
				count += Integer.parseInt(value_array[1]);
			}
			else{
				//System.out.println("Value: " + t.toString() + "Key: " +key.toString());
				anzahl += Double.parseDouble(value_array[0]);
				count++;
			}
		}
		outputValue.set(anzahl/count);

		context.write(key, outputValue);
	}
}
