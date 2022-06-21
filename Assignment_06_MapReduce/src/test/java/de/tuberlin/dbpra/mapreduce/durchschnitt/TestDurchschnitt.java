package de.tuberlin.dbpra.mapreduce.durchschnitt;

import org.junit.Test;
import org.apache.commons.io.*;

import java.io.File;

import static org.junit.Assert.fail;

public class TestDurchschnitt {

	@Test
	public void testDurchschnitt () {
		try {
			File file = new File("src/test/resources/output_avg");
			if (file.exists()) {
				FileUtils.deleteDirectory(file);
			}
			AufgabeDurchschnitt.main(new String[]{"src/test/resources/input", "src/test/resources/output_avg"});
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

}
