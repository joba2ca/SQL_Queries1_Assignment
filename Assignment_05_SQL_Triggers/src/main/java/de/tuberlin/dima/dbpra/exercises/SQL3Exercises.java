package de.tuberlin.dima.dbpra.exercises;

import com.ibm.db2.jcc.am.SqlException;
import de.tuberlin.dima.dbpra.interfaces.SQL3ExercisesInterface;

import java.sql.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SQL3Exercises implements SQL3ExercisesInterface {

	/**
	 * Funktion um die Tabelle KundenKontaktDaten zu erstellen. Falls die Tabelle
	 * bereits existiert, soll ihr Inhalt gelöscht werden.
	 *
	 * @param con Connection Objekt für die Datenbankverbindung.
	 * @throws SQLException Falls ein Fehler auftritt. Es soll keine(!) Exception
	 *                      geworfen werden, falls die Tabelle bereits existiert!
	 */
	@Override
	public void initKundenKontaktTable(Connection con) throws SQLException {
		// AUFGABE 1 (Teil 1), 0.5P
		Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		try {
			statement.execute(getQueryString(1));
		} catch (SQLException e) {
			//TODO: Fehlerbehandlung
			statement.execute("DELETE FROM KundenKontaktDaten");
		}
	}

	/**
	 * Funktion um die Tabelle KundenKontaktDaten zu löschen.
	 *
	 * @param con Connection Objekt für die Datenbankverbindung.
	 * @throws SQLException Falls ein Fehler auftritt. Es soll keine(!) Exception
	 *                      geworfen werden, falls die Tabelle nicht existiert!
	 */
	@Override
	public void dropKundenKontaktTable(Connection con) throws SQLException {
		// AUFGABE 1 (Teil 2), 0.5P
		Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		try {
			statement.execute("DROP TABLE KundenKontaktDaten");
		}catch (SQLException e){
			//ignore
		}
	}

	/**
	 * Funktion um der Tabelle KundenKontaktDaten ein referential constraint
	 * mit dem Namen KundenKontaktDaten_Kunden_FK auf Kunde hinzuzufügen.
	 *
	 * @param con Connection Objekt für die Datenbankverbindung.
	 * @throws SQLException Falls ein Fehler auftritt.
	 */
	@Override
	public void addRefConstraint(Connection con) throws SQLException {
		// AUFGABE 2, 1P
		executeStatement(con, getQueryString(2));
	}

	/**
	 * Funktion um der Tabelle KundenKontaktDaten ein check constraint
	 * mit dem Namen TwitterID_Check auf das Attribut Twitter_ID hinzuzufügen.
	 *
	 * Das Check Constraint soll sicher stellen dass:
	 * - Alle Twitter_IDs mit @ anfangen.
	 * - Und nach dem @ eine Zeichenkette folgt, die
	 * o Maximal 31 Zeichen lang ist
	 * o Nur aus kleinen lateinische Buchstaben, Zahlen, und '_' besteht.
	 *
	 * @param con Connection Objekt für die Datenbankverbindung.
	 * @throws SQLException Falls ein Fehler auftritt.
	 */
	@Override
	public void addCheckTwitterConstraint(Connection con) throws SQLException {
		// AUFGABE 3, 2P
		executeStatement(con, getQueryString(3));
	}

	/**
	 * Funktion um der Tabelle KundenKontaktDaten ein check constraint
	 * mit dem Namen GoogleUndFacebookID_CHECK auf die Attribute Google_ID und
	 * Facebook_ID hinzuzufügen.
	 *
	 * Das Check Constraint soll sicher stellen dass:
	 * - Alle Google und FacebookIDs im Intervall [10000000,99999999] liegen.
	 * - Keine verbotenen IDs verwendet werden.
	 * o Google verbietet: 10000042, 10000007, 10000666
	 * o Facebook verbietet: 10004321, 10097242, 12345678
	 *
	 * @param con Connection Objekt für die Datenbankverbindung.
	 * @throws SQLException Falls ein Fehler auftritt.
	 */
	@Override
	public void addCheckFacebookGoogleConstraint(Connection con) throws SQLException {
		// AUFGABE 4, 1P
		executeStatement(con, getQueryString(4));
	}

	/**
	 * Funktion um der Tabelle KundenKontaktDaten ein check constraint
	 * mit dem Namen Telefonnummer_CHECK auf das Attribut Telefonnummer
	 * hinzuzufügen.
	 *
	 * Das Check Constraint soll sicher stellen dass:
	 * - Jede Nummer die Landvorwahl für Deutschland +49 hat
	 * - Nach der +49 Vorwahl eine Zahl zwischen 150 und 180 folgt
	 * - Nach der Netzvorwahl zwischen 8 und 10 Ziffern kommen
	 * - Landesvorwahl, Netzvorwahl und Nummer durch ein Leerzeichen getrennt
	 * werden
	 *
	 * @param con Connection Objekt für die Datenbankverbindung.
	 * @throws SQLException Falls ein Fehler auftritt.
	 */
	@Override
	public void addTelefonNummerConstraint(Connection con) throws SQLException {
		// AUFGABE 5, 2P
		executeUpdateStatement(con, getQueryString(5));
	}

	/**
	 * Funktion um der Tabelle KundenKontaktDaten ein check constraint
	 * mit dem Namen MindestensEinKontakt_CHECK hinzuzufügen.
	 *
	 * Das Check Constraint soll sicher stellen dass mindestens eines der Attribute
	 * TWITTER_ID, GOOGLE_ID, FACEBOOK_ID, SKYPE_ID, TELEFONNUMMER nicht NULL ist.
	 *
	 * @param con Connection Objekt für die Datenbankverbindung.
	 * @throws SQLException Falls ein Fehler auftritt.
	 */
	@Override
	public void addMindestensEinsConstraint(Connection con) throws SQLException {
		// AUFGABE 6, 1P
		executeStatement(con, getQueryString(6));
	}

	/**
	 * Funktion um der Tabelle KundenKontaktDaten einen Trigger hinzuzufügen.
	 * Die Funktion soll zunächst die Tabelle TelefonnummerAenderungen
	 * (Definition siehe Aufgabenstellung) anlegen. Falls die Tabelle bereits
	 * existiert, soll der Inhalt der Tabelle gelöscht werden.
	 *
	 * Anschließend soll die Funktion Trigger für die Tabelle Kundenkontakdaten
	 * anlegen, so dass:
	 * - Eine Änderung der Telefonnummer für einen Kunden kann frühestens
	 * 15 Sekunden nach der letzten Änderungen vorgenommen werden.
	 * o Ansonsten wird ein SIGNAL SQLSTATE '70001' Statement geworfen.
	 * - Bei einer erfolgreichen Änderung wird ein entsprechender Log-Eintrag
	 * in die Tabelle TelefonnummerAenderungen vorgenommen.
	 * - Sind alte und neue Telefonnummer identisch, wird keine Aktion
	 * vorgenommen.
	 *
	 * @param con Connection Objekt für die Datenbankverbindung.
	 * @throws SQLException Falls ein Fehler auftritt.
	 */
	@Override
	public void triggerAnlegen(Connection con) throws SQLException {
		// AUFGABE 7, 2P
		Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		try {
			statement.execute(getQueryString(7));
		}catch (SQLException e){
			statement.execute("DELETE FROM TelefonnummerAenderungen");
		}
		statement.execute(getQueryString(8));
	}

	private void executeStatement(Connection con, String query) throws SQLException {
		Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		statement.execute(query);
	}

	private void executeUpdateStatement(Connection con, String query) throws SQLException {
		Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		statement.executeUpdate(query);
		statement.close();
	}

	private String getQueryString(int i) {
		StringBuilder query = new StringBuilder();

		try {
			String path = String.format("de/tuberlin/dima/dbpra/exercises/SQL3/SQL3Query%02d.sql", i);
			InputStream is = SQL3Exercises.class.getClassLoader().getResourceAsStream(path);
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

			String line;
			while ((line = br.readLine()) != null) {
				if (!(line.startsWith("--") || line.isEmpty())) {
					query.append(line);
					query.append("\n");
				}
			}
		} catch(Exception e) {
			throw new RuntimeException("Error while reading resource for query " + i + ": ", e);
		}

		int limit = query.lastIndexOf(";") < 0 ? query.length() : query.lastIndexOf(";");
		return query.substring(0, limit);
	}
}
