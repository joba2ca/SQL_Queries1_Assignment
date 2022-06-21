package de.tuberlin.dima.dbpra.exercises.transactions;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import de.tuberlin.dima.dbpra.config.ConnectionConfig;
import de.tuberlin.dima.dbpra.exercises.TransactionsExercises;
import de.tuberlin.dima.dbpra.interfaces.transactions.Bestellposten;
import de.tuberlin.dima.dbpra.interfaces.transactions.Bestellung;
import de.tuberlin.dima.dbpra.interfaces.transactions.Lieferant;
import de.tuberlin.dima.dbpra.interfaces.transactions.LiefertEntry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class PartialTransactionsTest {
	
	@SuppressWarnings("unused")
	private class TestPosten {
		
		double preis;
		
		String artikel;
		
		int anzahl;
		
		String lieferant;
		
		TestPosten(ResultSet set) throws SQLException {
			this.artikel = set.getString(1).trim();
			this.anzahl = set.getInt(2);
			this.preis = set.getDouble(3);
			this.lieferant = set.getString(4).trim();
		}
	}
	
	private Connection connection;
	
	private Connection testcon;
	
	private DataManager manager;
	
	private Statement statement;

	private TransactionsExercises solution;

	@Before
	public void setUp() throws Exception {
		
		// instantiate sql1 solution
		this.solution = new TransactionsExercises();
					
		// connect to database
		this.connection = DriverManager.getConnection( "jdbc:db2://gnu.dima.tu-berlin.de:50000/"+ ConnectionConfig.DB2_DB, ConnectionConfig.DB2_USER, ConnectionConfig.DB2_PW);
		this.testcon = DriverManager.getConnection( "jdbc:db2://gnu.dima.tu-berlin.de:50000/"+ConnectionConfig.DB2_DB, ConnectionConfig.DB2_USER, ConnectionConfig.DB2_PW);
		
		// create statement
		this.statement = this.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		
		this.manager = new DataManager(this.testcon);
	}
	
	@After
	public void cleanUp() throws Exception {
		this.connection.close();
		this.testcon.close();
	}
	
	@Test
	public void bestellungEinfuegenTest1 () {
		Bestellung bestellung = this.manager.createRandomBestellung(0);
		this.solution.bestellungBearbeiten(this.connection,  bestellung);
 		testBestellung (bestellung);
	}
	
	@Test
	public void bestellungEinfuegenTest2 () {
		Bestellung bestellung = this.manager.createRandomBestellung(1);
		this.solution.bestellungBearbeiten(this.connection,  bestellung);
		testBestellung (bestellung);
	}
	
	@Test
	public void bestellungEinfuegenTest3 () {
		Bestellung bestellung = this.manager.createRandomBestellung(2);
		this.solution.bestellungBearbeiten(this.connection,  bestellung);
		testBestellung (bestellung);
	}
	
	@Test
	public void lieferantTest1 () {
		Lieferant lieferant = this.manager.createRandomLieferant(0, 0);
		this.solution.neueLieferdatenEinfuegen(this.connection,  lieferant);
		testLieferant(lieferant);
	}
	
	@Test
	public void lieferantTest2 () {
		Lieferant lieferant = this.manager.createRandomLieferant(0, 1);
		this.solution.neueLieferdatenEinfuegen(this.connection,lieferant);
		testLieferant(lieferant);
	}
	
	@Test
	public void lieferantTest3 () {
		Lieferant lieferant = this.manager.createRandomLieferant(0, 2);
		this.solution.neueLieferdatenEinfuegen(this.connection,  lieferant);
		testLieferant(lieferant);
	}
	
	@Test
	public void lieferantTest4 () {
		Lieferant lieferant = this.manager.createRandomLieferant(0, 3);
		this.solution.neueLieferdatenEinfuegen(this.connection, lieferant);
		testLieferant(lieferant);
	}
	
	@Test
	public void lieferantTest5 () {
		Lieferant lieferant = this.manager.createRandomLieferant(-1, 0);
		this.solution.neueLieferdatenEinfuegen(this.connection,  lieferant);
		testLieferant(lieferant);
	}
	
	@Test
	public void lieferantTest6 () {
		Lieferant lieferant = this.manager.createRandomLieferant(-1, 1);
		this.solution.neueLieferdatenEinfuegen(this.connection,  lieferant);
		testLieferant(lieferant);
	}
	
	@Test
	public void lieferantTest7 () {
		Lieferant lieferant = this.manager.createRandomLieferant(-1, 2);
		this.solution.neueLieferdatenEinfuegen(this.connection, lieferant);
		testLieferant(lieferant);
	}
	
	private boolean testBestellung(Bestellung bestellung) {
		try {
			// bestellposten finden
			Statement stmt = this.testcon.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.execute("select artikel, anzahl, preis, lieferant from bestellposten where bestell_nr = '" + bestellung.getBestell_Nr() + "' order by postennummer");
			
			List<TestPosten> posten = new LinkedList<TestPosten> ();
			ResultSet set = stmt.getResultSet();
			while (set.next()) {
				posten.add (new TestPosten(set));
			}
			set.close();
			
			if (!bestellung.isValid()) {
				if (posten.size() > 0) {
					fail ("Daten einer ungültigen oder nicht erfüllbaren Bestellung wurden in die Datenbank geschrieben!");
				} else {
					// es wurden keine bestellposten eingefügt, wir gehen davon aus, dass auch sonst nichts kaputt gemacht wurde
					return true;
				}
			}
			
			Iterator<Bestellposten> bposten = bestellung.getBestellposten();
			Iterator<TestPosten> tposten = posten.iterator();
			while (bposten.hasNext() && tposten.hasNext()) {
				TestPosten tp = tposten.next();
				Bestellposten bp = bposten.next();
				if (!bp.getCandidates().contains(tp.lieferant)) {
					fail ("Bestellposten wurde einem falschen Lieferanten zugewiesen (nicht der günstigste oder benötigte Anzahl nicht verfügbar)");
				}
			}
			if (bposten.hasNext() || tposten.hasNext()) {
				fail ("Anzahl der eingefügten Bestellposten entspricht nicht der Anzahl der erwartetetn Bestellposten");
			}

		} catch (SQLException e) {
			System.err.println("Data base check failed, not sure if test code is incorrect, the database is broken, or solution is wrong :-(");
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
			fail ("SQL Exception: " + e.getMessage());
			return false;
		}
		return true;
	}
	
	private boolean testLieferant (Lieferant lieferant) {
		try {
			// ursprungsdaten für diesen Lieferanten
			List<LiefertEntry> oldentries = this.manager.lieferantIndex.get(Integer.toString(lieferant.getLieferanten_nr()));
			// aktuelle Daten in der Datenbank
			Statement state = this.testcon.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// bestellposten finden
			state.execute("select artikel, anzahl_verfuegb, lieferpreis from liefert where lieferant = '" + lieferant.getLieferanten_nr() + "'");
			ResultSet set = state.getResultSet();
			if (oldentries == null) {
				// lieferant war neu
				boolean hasResult = set.next();
				if (lieferant.getType() != 0 && hasResult) {
					fail ("Lieferliste von neuem Lieferanten war ungültig, Lieferdaten wurden aber in die Datenbank geschrieben");
					set.close();
					return false;
				} else {
					// anzahl der einträge testen
					set.last();
					int row = set.getRow();
					if (row == 0 && lieferant.getType()!= 0) {
						set.close();
						return true;
					}
					if (row != lieferant.liefertentries.size()) {
						fail ("Anzahl der liefert Einträge für neuen Lieferanten ist nicht korrekt");
						set.close();
						return false;
					}
					set.close();
					return true;
				}
			} else {
				// lieferant gab es schon
				while (set.next()) {
					int artikel = set.getInt(1);
					LiefertEntry entry = find (oldentries, artikel);
					if (entry == null) {
						fail ("Eintrag mit falschem Artikel in liefert Tabelle gefunden");
						set.close();
						return false;
					}
					int anz_old = entry.getAnzahl();
					int anz_now = set.getInt(2);
					boolean numequal = anz_old == anz_now;  
					if ((!numequal && lieferant.getType() != 0) || (numequal && lieferant.getType() == 0)) {
						fail ("Artikel mit falscher Anzahl in der Tabelle gefunden");
						set.close();
						return false;
					}
				}
				set.last();
				int row = set.getRow();
				if (row != lieferant.liefertentries.size()) {
					fail ("Anzahl der liefert Einträge für bestehenden Lieferanten ist nicht korrekt");
					set.close();
					return false;
				}
				set.close();
				return true;
			}
		} catch (SQLException e) {
			System.err.println("Data base check failed, not sure if test code is incorrect, the database is broken, or solution is wrong :-(");
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
			fail ("SQL Exception: " + e.getMessage());
			return false;
		}
	}
	
	private LiefertEntry find (List<LiefertEntry> list, int artikel) {
		for (LiefertEntry entry : list) {
			if (artikel == Integer.parseInt(entry.getArtikel())) {
				return entry;
			}
		}
		return null;
	}
}
