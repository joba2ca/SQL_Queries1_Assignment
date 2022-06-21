package de.tuberlin.dima.dbpra.exercises;

import de.tuberlin.dima.dbpra.config.ConnectionConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Dieser Test führt einfache Checks durch und prüft NICHT auf
 * absolute Korrektheit der Queries für die SQL 2 Aufgabe.
 * Ein Bestehen der Tests bedeutet nicht, dass die Anfragen definitiv
 * korrekt sind. Für die Benotung der Abgabe werden volle Tests
 * durchgeführt.
 * <p/>
 * Dieser Test führt nur einfach Check durch, wie zum Beispiel:
 * - korrektes Schema des Ergebnisses (Anzahl + Namen der Ergebnisfelder)
 * - korrekte Anzahl der Rückgabezeilen
 * - ob einige erwartete Zeilen im Ergebnis enthalten sind
 * - bei angefragter Ordnung, die erste und/oder letzte Zeile
 */
public class PartialSQL2ExerciseTest {

	private Statement statement;

	@Before
	public void setUp() throws Exception {
		// connect to database
		Connection con = DriverManager.getConnection("jdbc:db2://gnu.dima.tu-berlin.de:50000/" + ConnectionConfig.DB2_DB, ConnectionConfig.DB2_USER, ConnectionConfig.DB2_PW);

		// create statement
		statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		//throw new Exception ("database not found exception");
	}

	@After
	public void cleanUp() throws Exception {
		statement.getConnection().close();
	}

	@Test
	public void getSQLQuery01Test() {
		try {
			statement.execute(SQL2Exercises.getSQLQuery01());
			ResultSet result = statement.getResultSet();
			String[] expectedSchema = new String[]{"Marke", "D_Preis"};
			assertTrue("Task 01: Schema incorrect. Should be: " + Arrays.toString(expectedSchema), checkResultSchema(result, expectedSchema));
			assertTrue("Resultset is empty!", result.next());
		} catch (SQLException e) {
			fail("Task 01: SQL Exception: " + e.getMessage());
		}
	}

	@Test
	public void getSQLQuery02Test() {
		try {
			statement.execute(SQL2Exercises.getSQLQuery02());
			ResultSet result = statement.getResultSet();
			String[] expectedSchema = new String[]{"Name", "Adresse"};
			assertTrue("Task 02: Schema incorrect. Should be: " + Arrays.toString(expectedSchema), checkResultSchema(result, expectedSchema));
			assertTrue("Resultset is empty!", result.next());
		} catch (SQLException e) {
			fail("Task 02: SQL Exception: " + e.getMessage());
		}
	}

	@Test
	public void getSQLQuery03Test() {
		try {
			statement.execute(SQL2Exercises.getSQLQuery03());
			ResultSet result = statement.getResultSet();
			String[] expectedSchema = new String[]{"Artikel_Nr", "Lieferant_Name", "Lieferpreis"};
			assertTrue("Task 03: Schema incorrect. Should be: " + Arrays.toString(expectedSchema), checkResultSchema(result, expectedSchema));
			assertTrue("Resultset is empty!", result.next());
		} catch (SQLException e) {
			fail("Task 03: SQL Exception: " + e.getMessage());
		}
	}

	@Test
	public void getSQLQuery04Test() {
		try {
			statement.execute(SQL2Exercises.getSQLQuery04());
			ResultSet result = statement.getResultSet();
			String[] expectedSchema = new String[]{"Bearbeiter"};
			assertTrue("Task 04: Schema incorrect. Should be: " + Arrays.toString(expectedSchema), checkResultSchema(result, expectedSchema));
			assertTrue("Resultset is empty!", result.next());
		} catch (SQLException e) {
			fail("Task 04: SQL Exception: " + e.getMessage());
		}
	}

	@Test
	public void getSQLQuery05Test() {
		try {
			statement.execute(SQL2Exercises.getSQLQuery05());
			ResultSet result = statement.getResultSet();
			String[] expectedSchema = new String[]{"Prioritaet", "Anzahl"};
			assertTrue("Task 05: Schema incorrect. Should be: " + Arrays.toString(expectedSchema), checkResultSchema(result, expectedSchema));
			assertTrue("Resultset is empty!", result.next());
		} catch (SQLException e) {
			fail("Task 05: SQL Exception: " + e.getMessage());
		}
	}

	@Test
	public void getSQLQuery06Test() {
		try {
			statement.execute(SQL2Exercises.getSQLQuery06());
			ResultSet result = statement.getResultSet();
			String[] expectedSchema = new String[]{"Marke"};
			assertTrue("Task 06: Schema incorrect. Should be: " + Arrays.toString(expectedSchema), checkResultSchema(result, expectedSchema));
			assertTrue("Resultset is empty!", result.next());
		} catch (SQLException e) {
			fail("Task 06: SQL Exception: " + e.getMessage());
		}
	}

	@Test
	public void getSQLQuery07Test() {
		try {
			statement.execute(SQL2Exercises.getSQLQuery07());
			ResultSet result = statement.getResultSet();
			String[] expectedSchema = new String[]{"Versandart", "Marke", "Anzahl"};
			assertTrue("Task 07: Schema incorrect. Should be: " + Arrays.toString(expectedSchema), checkResultSchema(result, expectedSchema));
			assertTrue("Resultset is empty!", result.next());
		} catch (SQLException e) {
			fail("Task 07: SQL Exception: " + e.getMessage());
		}
	}

	@Test
	public void getSQLQuery08Test() {
		try {
			statement.execute(SQL2Exercises.getSQLQuery08());
			ResultSet result = statement.getResultSet();
			String[] expectedSchema = new String[]{"Marke", "Name"};
			assertTrue("Task 08: Schema incorrect. Should be: " + Arrays.toString(expectedSchema), checkResultSchema(result, expectedSchema));
			assertTrue("Resultset is empty!", result.next());
		} catch (SQLException e) {
			fail("Task 08: SQL Exception: " + e.getMessage());
		}
	}

	@Test
	public void getSQLQuery09Test() {
		try {
			statement.execute(SQL2Exercises.getSQLQuery09());
			ResultSet result = statement.getResultSet();
			String[] expectedSchema = new String[]{"KundenName", "Anzahl"};
			assertTrue("Task 09: Schema incorrect. Should be: " + Arrays.toString(expectedSchema), checkResultSchema(result, expectedSchema));
			assertTrue("Resultset is empty!", result.next());
		} catch (SQLException e) {
			fail("Task 09: SQL Exception: " + e.getMessage());
		}
	}

	@Test
	public void getSQLQuery10Test() {
		try {
			statement.execute(SQL2Exercises.getSQLQuery10());
			ResultSet result = statement.getResultSet();
			String[] expectedSchema = new String[]{"Lieferanten_Nr", "Artikel", "Wert"};
			assertTrue("Task 10: Schema incorrect. Should be: " + Arrays.toString(expectedSchema), checkResultSchema(result, expectedSchema));
			assertTrue("Resultset is empty!", result.next());
		} catch (SQLException e) {
			System.out.println(e.toString());
			fail("Task 10: SQL Exception: " + e.getMessage());

		}
	}

	private boolean checkResultSchema(ResultSet result, String[] expectedSchema) throws SQLException {

		ResultSetMetaData meta = result.getMetaData();

		if (meta.getColumnCount() != expectedSchema.length)
			return false;

		for (int i = 0; i < meta.getColumnCount(); i++) {
			if (!meta.getColumnLabel(i + 1).equalsIgnoreCase(expectedSchema[i]))
				return false;
		}

		return true;
	}
}

