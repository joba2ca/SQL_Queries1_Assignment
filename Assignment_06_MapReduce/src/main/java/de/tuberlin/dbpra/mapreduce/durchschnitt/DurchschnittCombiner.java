package de.tuberlin.dbpra.mapreduce.durchschnitt;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class DurchschnittCombiner extends Reducer<Text, Text, Text, Text> {

	@Override
	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		Text textAnzahl = new Text();
		Double anzahl = 0.0;
		int count = 0;

		for(Text t : values){
			anzahl += Double.parseDouble(t.toString());
			count++;
		}

		textAnzahl.set(anzahl.toString() + "|" + count);

		context.write(key, textAnzahl);
	}
}

