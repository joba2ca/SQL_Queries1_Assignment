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
 * absolute Korrektheit der Queries für die SQL 1 Aufgabe.
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
public class PartialSQL1ExerciseTest {

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
			statement.execute(SQL1Exercises.getSQLQuery01());
			ResultSet result = statement.getResultSet();

			String[] expectedSchema = new String[]{"Name"};
			assertTrue("Task 01: Schema incorrect. Should be: " + Arrays.toString(expectedSchema), checkResultSchema(result, expectedSchema));

			if (result.next()) {
				if (result.getString(1).startsWith("VIETNAM")) {
					System.out.println("Passed task 01");
					result.close();
					return;
				}
			}
			fail("Task 01: Definitely incorrect result");
		} catch (SQLException e) {
			fail("Task 01: SQL Exception: " + e.getMessage());
		}
	}

	@Test
	public void getSQLQuery02Test() {
		try {
			statement.execute(SQL1Exercises.getSQLQuery02());
			ResultSet result = statement.getResultSet();

			String[] expectedSchema = new String[]{"Hersteller"};
			assertTrue("Task 02: Schema incorrect. Should be: " + Arrays.toString(expectedSchema), checkResultSchema(result, expectedSchema));

			if (result.last()) {
				if (result.getString(1).startsWith("Manufacturer#5") && result.getRow() == 5) {
					System.out.println("Passed task 02");
					result.close();
					return;
				}
			}
			fail("Task 02: Definitely incorrect result");
		} catch (SQLException e) {
			fail("Task 02: SQL Exception: " + e.getMessage());
		}
	}

	@Test
	public void getSQLQuery03Test() {
		try {
			statement.execute(SQL1Exercises.getSQLQuery03());
			ResultSet result = statement.getResultSet();

			String[] expectedSchema = new String[]{"Name", "Telefon"};
			assertTrue("Task 03: Schema incorrect. Should be: " + Arrays.toString(expectedSchema), checkResultSchema(result, expectedSchema));

			if (result.last()) {
				if (result.getString(1).startsWith("Customer#000014987") && result.getRow() == 1404) {
					System.out.println("Passed task 03");
					result.close();
					return;
				}
			}
			fail("Task 03: Definitely incorrect result");
		} catch (SQLException e) {
			fail("Task 03: SQL Exception: " + e.getMessage());
		}
	}

	@Test
	public void getSQLQuery04Test() {
		try {
			statement.execute(SQL1Exercises.getSQLQuery04());
			ResultSet result = statement.getResultSet();

			String[] expectedSchema = new String[]{"Name"};
			assertTrue("Task 04: Schema incorrect. Should be: " + Arrays.toString(expectedSchema), checkResultSchema(result, expectedSchema));

			if (result.last()) {
				if (result.getString(1).startsWith("almond azure slate aquamarine lace") && result.getRow() == 3992) {
					System.out.println("Passed task 04");
					result.close();
					return;
				}
			}
			fail("Task 04: Definitely incorrect result");
		} catch (SQLException e) {
			fail("Task 04: SQL Exception: " + e.getMessage());
		}
	}

	@Test
	public void getSQLQuery05Test() {
		try {
			statement.execute(SQL1Exercises.getSQLQuery05());
			ResultSet result = statement.getResultSet();

			String[] expectedSchema = new String[]{"Bestell_Nr", "Bestelldatum", "Status"};
			assertTrue("Task 05: Schema incorrect. Should be: " + Arrays.toString(expectedSchema), checkResultSchema(result, expectedSchema));

			if (result.last()) {
				if (result.getString(1).startsWith("3")) {
					System.out.println("Passed task 05");
					result.close();
					return;
				}
			}

			fail("Task 05: Definitely incorrect result");
		} catch (SQLException e) {
			fail("Task 05: SQL Exception: " + e.getMessage());
		}
	}

	@Test
	public void getSQLQuery06Test() {
		try {
			statement.execute(SQL1Exercises.getSQLQuery06());
			ResultSet result = statement.getResultSet();

			String[] expectedSchema = new String[]{"Bestell_Nr"};
			assertTrue("Task 06: Schema incorrect. Should be: " + Arrays.toString(expectedSchema), checkResultSchema(result, expectedSchema));

			if (result.first()) {
				if (result.getString(1).startsWith("579139")) {
					System.out.println("Passed task 06");
					result.close();
					return;
				}
			}

			fail("Task 06: Definitely incorrect result");
		} catch (SQLException e) {
			fail("Task 06: SQL Exception: " + e.getMessage());
		}
	}

	@Test
	public void getSQLQuery07Test() {
		try {
			statement.execute(SQL1Exercises.getSQLQuery07());
			ResultSet result = statement.getResultSet();

			String[] expectedSchema = new String[]{"Name", "Kontostand"};
			assertTrue("Task 07: Schema incorrect. Should be: " + Arrays.toString(expectedSchema), checkResultSchema(result, expectedSchema));

			if (result.first()) {
				if (result.getString(1).startsWith("Customer#000000002")) {
					System.out.println("Passed task 07");
					result.close();
					return;
				}
			}

			fail("Task 07: Definitely incorrect result");
		} catch (SQLException e) {
			fail("Task 07: SQL Exception: " + e.getMessage());
		}
	}

	@Test
	public void getSQLQuery08Test() {
		try {
			statement.execute(SQL1Exercises.getSQLQuery08());
			ResultSet result = statement.getResultSet();

			String[] expectedSchema = new String[]{"Anzahl", "Bilanz"};
			assertTrue("Task 08: Schema incorrect. Should be: " + Arrays.toString(expectedSchema), checkResultSchema(result, expectedSchema));

			if (result.getMetaData().getColumnCount() == 2) {
				if (result.first()) {
					if (result.getString(1).startsWith("3013")) {
						System.out.println("Passed task 08");
						result.close();
						return;
					}
				}
			}

			fail("Task 08: Definitely incorrect result");
		} catch (SQLException e) {
			fail("Task 08: SQL Exception: " + e.getMessage());
		}
	}

	@Test
	public void getSQLQuery09Test() {
		try {
			statement.execute(SQL1Exercises.getSQLQuery09());
			ResultSet result = statement.getResultSet();

			String[] expectedSchema = new String[]{"Durchschnitt"};
			assertTrue("Task 09: Schema incorrect. Should be: " + Arrays.toString(expectedSchema), checkResultSchema(result, expectedSchema));

			if (result.getMetaData().getColumnCount() == 1) {
				if (result.first()) {
					if (result.getString(1).startsWith("1408")) {
						System.out.println("Passed task 09");
						result.close();
						return;
					}
				}
			}

			fail("Task 09: Definitely incorrect result");
		} catch (SQLException e) {
			fail("Task 09: SQL Exception: " + e.getMessage());
		}
	}

	@Test
	public void getSQLQuery10Test() {
		try {
			statement.execute(SQL1Exercises.getSQLQuery10());
			ResultSet result = statement.getResultSet();

			String[] expectedSchema = new String[]{"Versandart", "Summe", "Durchschnitt"};
			assertTrue("Task 10: Schema incorrect. Should be: " + Arrays.toString(expectedSchema), checkResultSchema(result, expectedSchema));

			if (result.getMetaData().getColumnCount() == 3) {
				if (result.first()) {
					if (result.getString(1).startsWith("TRUCK") && result.getString(2).startsWith("1594")) {
						System.out.println("Passed task 10");
						result.close();
						return;
					}
				}
			}

			fail("Task 10: Definitely incorrect result");
		} catch (SQLException e) {
			fail("Task 10: SQL Exception: " + e.getMessage());
		}
	}

	@Test
	public void getSQLQuery11Test() {
		try {
			statement.execute(SQL1Exercises.getSQLQuery11());
			ResultSet result = statement.getResultSet();

			String[] expectedSchema = new String[]{"Versandart", "Versandanweisung", "Gesamtumsatz"};
			assertTrue("Task 11: Schema incorrect. Should be: " + Arrays.toString(expectedSchema), checkResultSchema(result, expectedSchema));

			if (result.getMetaData().getColumnCount() == 3) {
				if (result.first()) {
					if (result.getString(3).startsWith("17966")) {
						System.out.println("Passed task 11");
						result.close();
						return;
					}
				}
			}

			fail("Task 11: Definitely incorrect result");
		} catch (SQLException e) {
			fail("Task 11: SQL Exception: " + e.getMessage());
		}
	}

	@Test
	public void getSQLQuery12Test() {
		try {
			statement.execute(SQL1Exercises.getSQLQuery12());
			ResultSet result = statement.getResultSet();


			String[] expectedSchema = new String[]{"Bearbeitungsdauer", "Bestellposten"};
			assertTrue("Task 12: Schema incorrect. Should be: " + Arrays.toString(expectedSchema), checkResultSchema(result, expectedSchema));

			if (result.getMetaData().getColumnCount() == 2) {
				if (result.first()) {
					if (result.getString(1).startsWith("20") && result.getString(2).startsWith("1978")) {
						System.out.println("Passed task 12");
						result.close();
						return;
					}
				}
			}

			fail("Task 12: Definitely incorrect result");
		} catch (SQLException e) {
			fail("Task 12: SQL Exception: " + e.getMessage());
		}
	}

	@Test
	public void getSQLQuery13Test() {
		try {
			statement.execute(SQL1Exercises.getSQLQuery13());
			ResultSet result = statement.getResultSet();

			String[] expectedSchema = new String[]{"Branche", "MinKonto", "MaxKonto"};
			assertTrue("Task 13: Schema incorrect. Should be: " + Arrays.toString(expectedSchema), checkResultSchema(result, expectedSchema));

			if (result.getMetaData().getColumnCount() == 3) {
				if (result.last()) {
					if (result.getRow() == 5) {
						System.out.println("Passed task 13");
						result.close();
						return;
					}
				}
			}

			fail("Task 13: Definitely incorrect result");
		} catch (SQLException e) {
			fail("Task 13: SQL Exception: " + e.getMessage());
		}
	}

	@Test
	public void getSQLQuery14Test() {
		try {
			statement.execute(SQL1Exercises.getSQLQuery14());
			ResultSet result = statement.getResultSet();

			String[] expectedSchema = new String[]{"versandart", "retourstatus", "max_zeit", "durch_zeit"};
			assertTrue("Task 14: Schema incorrect. Should be: " + Arrays.toString(expectedSchema), checkResultSchema(result, expectedSchema));

			if (result.getMetaData().getColumnCount() == 4) {
				if (result.last()) {
					if (result.getRow() == 7) {
						System.out.println("Passed task 14");
						result.close();
						return;
					}
				}
			}

			fail("Task 14: Definitely incorrect result");
		} catch (SQLException e) {
			fail("Task 14: SQL Exception: " + e.getMessage());
		}
	}

	@Test
	public void getSQLQuery15Test() {
		try {
			statement.execute(SQL1Exercises.getSQLQuery15());
			ResultSet result = statement.getResultSet();

			String[] expectedSchema = new String[]{"Kunde", "Land"};
			assertTrue("Task 15: Schema incorrect. Should be: " + Arrays.toString(expectedSchema), checkResultSchema(result, expectedSchema));

			if (result.getMetaData().getColumnCount() == 2) {
				if (result.last()) {
					if (result.getRow() == 15000) {
						System.out.println("Passed task 15");
						result.close();
						return;
					}
				}
			}

			fail("Task 15: Definitely incorrect result");
		} catch (SQLException e) {
			fail("Task 15: SQL Exception: " + e.getMessage());
		}
	}

	@Test
	public void getSQLQuery16Test() {
		try {
			statement.execute(SQL1Exercises.getSQLQuery16());
			ResultSet result = statement.getResultSet();

			String[] expectedSchema = new String[]{"MinSteuer"};
			assertTrue("Task 16: Schema incorrect. Should be: " + Arrays.toString(expectedSchema), checkResultSchema(result, expectedSchema));

			if (result.getMetaData().getColumnCount() == 1) {
				if (result.last()) {
					if (result.getRow() == 1) {
						System.out.println("Passed task 16");
						result.close();
						return;
					}
				}
			}

			fail("Task 16: Definitely incorrect result");
		} catch (SQLException e) {
			fail("Task 16: SQL Exception: " + e.getMessage());
		}
	}

	@Test
	public void getSQLQuery17Test() {
		try {
			statement.execute(SQL1Exercises.getSQLQuery17());
			ResultSet result = statement.getResultSet();

			String[] expectedSchema = new String[]{"Region", "Anzahl"};
			assertTrue("Task 17: Schema incorrect. Should be: " + Arrays.toString(expectedSchema), checkResultSchema(result, expectedSchema));

			if (result.getMetaData().getColumnCount() == 2) {
				if (result.last()) {
					if (result.getRow() == 5) {
						System.out.println("Passed task 17");
						result.close();
						return;
					}
				}
			}

			fail("Task 17: Definitely incorrect result");
		} catch (SQLException e) {
			fail("Task 17: SQL Exception: " + e.getMessage());
		}
	}

	@Test
	public void getSQLQuery18Test() {
		try {
			statement.execute(SQL1Exercises.getSQLQuery18());
			ResultSet result = statement.getResultSet();

			String[] expectedSchema = new String[]{"Lieferanten_Nr"};
			assertTrue("Task 18: Schema incorrect. Should be: " + Arrays.toString(expectedSchema), checkResultSchema(result, expectedSchema));

			if (result.getMetaData().getColumnCount() == 1) {
				if (result.last()) {
					if (result.getRow() == 562) {
						System.out.println("Passed task 18");
						result.close();
						return;
					}
				}
			}

			fail("Task 18: Definitely incorrect result");
		} catch (SQLException e) {
			fail("Task 18: SQL Exception: " + e.getMessage());
		}
	}

	@Test
	public void getSQLQuery19Test() {
		try {
			statement.execute(SQL1Exercises.getSQLQuery19());
			ResultSet result = statement.getResultSet();

			String[] expectedSchema = new String[]{"Hersteller", "Rabatt"};
			assertTrue("Task 19: Schema incorrect. Should be: " + Arrays.toString(expectedSchema), checkResultSchema(result, expectedSchema));

			if (result.getMetaData().getColumnCount() == 2) {
				if (result.last()) {
					if (result.getRow() == 5) {
						System.out.println("Passed task 19");
						result.close();
						return;
					}
				}
			}

			fail("Task 19: Definitely incorrect result");
		} catch (SQLException e) {
			fail("Task 19: SQL Exception: " + e.getMessage());
		}
	}

	@Test
	public void getSQLQuery20Test() {
		try {
			statement.execute(SQL1Exercises.getSQLQuery20());
			ResultSet result = statement.getResultSet();

			String[] expectedSchema = new String[]{"Name", "Bestell_Anzahl"};
			assertTrue("Task 20: Schema incorrect. Should be: " + Arrays.toString(expectedSchema), checkResultSchema(result, expectedSchema));

			if (result.getMetaData().getColumnCount() == 2) {
				if (result.last()) {
					if (result.getRow() == 70) {
						System.out.println("Passed task 20");
						result.close();
						return;
					}
				}
			}

			fail("Task 20: Definitely incorrect result");
		} catch (SQLException e) {
			fail("Task 20: SQL Exception: " + e.getMessage());
		}
	}

	@Test
	public void getSQLQuery21Test() {
		try {
			statement.execute(SQL1Exercises.getSQLQuery21());
			ResultSet result = statement.getResultSet();

			String[] expectedSchema = new String[]{"Name"};
			assertTrue("Task 21: Schema incorrect. Should be: " + Arrays.toString(expectedSchema), checkResultSchema(result, expectedSchema));

			if (result.getMetaData().getColumnCount() == 1) {
				if (result.last()) {
					if (result.getRow() == 3548) {
						System.out.println("Passed task 21");
						result.close();
						return;
					}
				}
			}

			fail("Task 21: Definitely incorrect result");
		} catch (SQLException e) {
			fail("Task 21: SQL Exception: " + e.getMessage());
		}
	}

	@Test
	public void getSQLQuery22Test() {
		try {
			statement.execute(SQL1Exercises.getSQLQuery22());
			ResultSet result = statement.getResultSet();

			String[] expectedSchema = new String[]{"kunden_nr", "Name", "Telefon", "Land", "Verlust"};
			assertTrue("Task 22: Schema incorrect. Should be: " + Arrays.toString(expectedSchema), checkResultSchema(result, expectedSchema));

			if (result.getMetaData().getColumnCount() == 5) {
				if (result.last()) {
					if (result.getRow() == 20) {
						System.out.println("Passed task 22");
						result.close();
						return;
					}
				}
			}

			fail("Task 22: Definitely incorrect result");
		} catch (SQLException e) {
			fail("Task 22: SQL Exception: " + e.getMessage());
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
