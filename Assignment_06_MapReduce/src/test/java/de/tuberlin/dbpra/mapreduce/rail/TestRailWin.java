package de.tuberlin.dbpra.mapreduce.rail;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.apache.hadoop.mrunit.types.Pair;
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
public class TestRailWin {

    MapReduceDriver<LongWritable, Text, Text, IntWritable, Text, IntWritable> mapReduceDriver;
    MapDriver<LongWritable, Text, Text, IntWritable> mapDriver;
    ReduceDriver<Text, IntWritable, Text, IntWritable> reduceDriver;
    ReduceDriver<Text, IntWritable, Text, IntWritable> combineDriver;

    @Before
    public void setUp() {
        RailMapper mapper = new RailMapper();
        RailReducer reducer = new RailReducer();
        RailCombiner combiner = new RailCombiner();
        mapDriver = new MapDriver<LongWritable, Text, Text, IntWritable>();
        mapDriver.setMapper(mapper);
        reduceDriver = new ReduceDriver<Text, IntWritable, Text, IntWritable>();
        reduceDriver.setReducer(reducer);
        combineDriver = new ReduceDriver<Text, IntWritable, Text, IntWritable>();
        combineDriver.setReducer(combiner);
        mapReduceDriver = new MapReduceDriver<LongWritable, Text, Text, IntWritable, Text, IntWritable>();
        mapReduceDriver.setMapper(mapper);
        mapReduceDriver.setCombiner(combiner);
        mapReduceDriver.setReducer(reducer);
    }

    @Test
    public void testMapReducdRails() throws IOException {
        FeedMapReduceDriverWithTestInput(mapReduceDriver);
        List<Pair<Text, IntWritable>> res = mapReduceDriver.run();
        assertTrue("received an empty result", res.size() > 0);
        for (int i = 0; i < 10; i++) {
            Pair<Text, IntWritable> pair = res.get(i);
            System.out.println(pair.getFirst() + " | " + pair.getSecond());
        }
    }

    public void loadSampleInput(ArrayList<Pair<LongWritable, Text>> input) throws IOException {
        InputStream stream = getResource("input/btest.tbl");
        BufferedReader br = new BufferedReader(
                new InputStreamReader(stream));
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

    public InputStream getResource (String name) throws IOException {
        File file = new File(getClass().getClassLoader().getResource(name).getFile());
        return new FileInputStream(file);
    }


}
