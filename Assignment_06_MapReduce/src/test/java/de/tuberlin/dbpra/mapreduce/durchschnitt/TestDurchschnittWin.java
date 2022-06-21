package de.tuberlin.dbpra.mapreduce.durchschnitt;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.eclipse.jdt.internal.core.SourceType;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by mleich on 08/12/2016.
 */
public class TestDurchschnittWin {

    static final String[] SampleRows = {
            "32|11615|4117|6|6|9159.66|0.04|0.03|N|O|1995-07-21|1995-09-23|1995-07-25|COLLECT COD|RAIL|requests integrat|\n",
            "5990021|2887|7888|1|43|76964.84|0.00|0.04|N|O|1997-03-04|1997-01-21|1997-03-16|NONE|AIR|express deposits k|\n"
    };
    MapReduceDriver<LongWritable, Text, Text, Text, Text, DoubleWritable> mapReduceDriver;
    MapDriver<LongWritable, Text, Text, Text> mapDriver;
    ReduceDriver<Text, Text, Text, DoubleWritable> reduceDriver;
    ReduceDriver<Text, Text, Text, Text> combineDriver;

    public  void loadSampleInput(ArrayList<Pair<LongWritable, Text>> input) throws IOException {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(getResource("input/btest.tbl")));
        String line;
        while ((line = br.readLine()) != null) {
            input.add(new Pair<LongWritable, Text>(new LongWritable(1), new Text(line)));
        }
    }

    public void FeedMapReduceDriverWithTestInput(MapReduceDriver<LongWritable, Text, ?, ?, ?, ?> driver) throws IOException {
        final ArrayList<Pair<LongWritable, Text>> input = new ArrayList<Pair<LongWritable, Text>>();
        loadSampleInput(input);
        driver.resetOutput();
        for (Pair<LongWritable, Text> pair : input) {
            driver.addInput(pair);
        }
    }

    @Before
    public void setUp() {
        DurchschnittMapper mapper = new DurchschnittMapper();
        DurchschnittReducer reducer = new DurchschnittReducer();
        DurchschnittCombiner combiner = new DurchschnittCombiner();
        mapDriver = new MapDriver<LongWritable, Text, Text, Text>();
        mapDriver.setMapper(mapper);
        reduceDriver = new ReduceDriver<Text, Text, Text, DoubleWritable>();
        reduceDriver.setReducer(reducer);
        combineDriver = new ReduceDriver<Text, Text, Text, Text>();
        combineDriver.setReducer(combiner);
        mapReduceDriver = new MapReduceDriver<LongWritable, Text, Text, Text, Text, DoubleWritable>();
        mapReduceDriver.setMapper(mapper);
        mapReduceDriver.setCombiner(combiner);
        mapReduceDriver.setReducer(reducer);
    }

    public void FeedMapReduceDriverWithTestInput() throws IOException {
        FeedMapReduceDriverWithTestInput(mapReduceDriver);
        mapReduceDriver.resetOutput();
    }

    public List<Text> SampleMapList() throws IOException {
        List<Text> values = new ArrayList<Text>();
        List<Pair<Text, Text>> res;
        for (int i = 0; i < SampleRows.length; i++) {
            mapDriver.withInput(new LongWritable(1), new Text(SampleRows[i]));
            res = mapDriver.run();
            values.add(new Text(res.get(0).getSecond().toString()));
        }
        return values;
    }

    @Test
    public void testMapReduce() throws IOException {
        FeedMapReduceDriverWithTestInput();
        List<Pair<Text, DoubleWritable>> res = mapReduceDriver.run();
        assertTrue("received an empty result", res.size() > 0);

        for (int i = 0; i < 10; i++) {
            Pair<Text, DoubleWritable> pair = res.get(i);
            System.out.println(pair.getFirst() + " | " + pair.getSecond());
        }

    }

    public InputStream getResource (String name) throws IOException {
        File file = new File(getClass().getClassLoader().getResource(name).getFile());
        return new FileInputStream(file);
    }

}
