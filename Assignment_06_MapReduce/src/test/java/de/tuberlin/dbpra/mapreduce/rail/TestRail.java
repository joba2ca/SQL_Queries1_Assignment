package de.tuberlin.dbpra.mapreduce.rail;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.fail;


public class TestRail {

	@Test
	public void testRail () {
		try {
			File file = new File("src/test/resources/output_rail");
			if (file.exists()) {
				FileUtils.deleteDirectory(file);
			}
			AufgabeRail.main(new String[]{"src/test/resources/input", "src/test/resources/output_rail"});
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
