package de.tuberlin.dima.dbpra.exercises.transactions;

import de.tuberlin.dima.dbpra.config.ConnectionConfig;
import de.tuberlin.dima.dbpra.interfaces.transactions.Bestellposten;
import de.tuberlin.dima.dbpra.interfaces.transactions.Bestellung;
import de.tuberlin.dima.dbpra.interfaces.transactions.Lieferant;
import de.tuberlin.dima.dbpra.interfaces.transactions.LiefertEntry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;


/**
 * Verwaltet Wissen über die Datenbank. Diese Klasse darf _NICHT_ zur Lösung der Übungsaufgabe verwendet werden!
 * 
 * @author mleich
 */
class DataManager {

	Random rand;
	
	String[] artikel;
	
	String[] lieferanten;
	
	String[] versandart;
	
	String[] versandanweisung;
	
	String[] bestellids;
	
	Vector<LiefertEntry> entries;
	
	Index<String, LiefertEntry> lieferantIndex;
	
	Index<String, LiefertEntry> artikelIndex;
	
	int maxBestellung;
	
	int maxLieferant;
	
	protected class Index<K,V> {
		Map<K, List<V>> map;
		
		Index () {
			this.map = new HashMap<K,List<V>> ();
		}
		
		void insert (K key, V value) {
			List<V> list = this.map.get(key);
			if (list == null) {
				list = new LinkedList<V> ();
				this.map.put(key, list);
			}
			list.add(value);
		}
		
		List<V> get (K key) {
			return this.map.get(key);
		}
	}
	
	public DataManager (Connection con) {
		this.rand = new Random ();
		try {
			Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			// alle möglichen artikel, versandhinweise, usw...
			this.artikel = getAllValues (statement, "SELECT Artikel_Nr FROM Artikel");
			this.versandart = getAllValues (statement, "SELECT DISTINCT Versandart FROM Bestellposten");
			this.versandanweisung = getAllValues (statement, "SELECT DISTINCT Versandanweisung FROM Bestellposten");
			this.lieferanten = getAllValues(statement, "SELECT lieferanten_nr FROM Lieferant");
			this.bestellids = getAllValues (statement, "SELECT bestell_nr FROM bestellung");
			
			// groesste bisherige bestell ID
			statement.execute("select max(bestell_nr) from bestellung");
			ResultSet set = statement.getResultSet();
			set.next();
			this.maxBestellung = set.getInt(1);
			set.close();
			
			// groesste bisherige lieferant ID
			statement.execute("select max(Lieferanten_nr) from Lieferant");
			set = statement.getResultSet();
			set.next();
			this.maxLieferant = set.getInt(1);
			set.close();
			
			// lieferanten und verfügbare artikel + preise laden
			this.lieferantIndex = new Index<String, LiefertEntry> ();
			this.artikelIndex = new Index<String, LiefertEntry> ();
			statement.execute("SELECT Artikel, Lieferant, Anzahl_Verfuegb, Lieferpreis FROM Liefert");
			set = statement.getResultSet();
			
			while (set.next()) {
				LiefertEntry entry = new LiefertEntry (set.getString(2).trim(), set.getString(1).trim(), set.getInt(3), set.getDouble(4));
				this.lieferantIndex.insert (entry.getLieferant(), entry);
				this.artikelIndex.insert(entry.getArtikel(), entry);
			}
			set.close();
						
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	private String[] getAllValues (Statement statement, String query) throws SQLException {
		statement.execute(query);
		ResultSet set = statement.getResultSet();
		LinkedList<String> tmp = new LinkedList<String> ();
		while (set.next()) {
			tmp.add(set.getString(1).trim());
		}
		set.close();
		return tmp.toArray(new String[tmp.size()]);
	}
	
	protected String getRandomArtikel () {
		return this.artikel[this.rand.nextInt(this.artikel.length)];
	}
	
	protected String getRandomVersandart () {
		return this.versandart[this.rand.nextInt(this.versandart.length)];
	}
	
	protected String getRandomVersandhinweis () {
		return this.versandanweisung[this.rand.nextInt(this.versandanweisung.length)];
	}
	
	protected String getRandomBestell_Nr () {
		return this.bestellids[this.rand.nextInt(this.bestellids.length)];
	}
	
	protected int getNextBestell_Nr () {
		return ++this.maxBestellung;
	}
	
	protected Bestellung createRandomBestellung (int type) {
		// größe der bestellung
		int size = this.rand.nextInt(8) + 2;
		Vector<String> artikel = new Vector<String> (size);
		// select keyposition
		int keypos = this.rand.nextInt(size);
		// bestellposten erstellen
		List<Bestellposten> posten = new LinkedList<Bestellposten> ();
		for (int i = 0; i < size; i++) {
			// artikel aussuchen
			String art = null;
			do {
				art = getRandomArtikel();
			} while (artikel.contains(art));
			artikel.add(art);
			// passende Lieferanten suchen
			List<LiefertEntry> lieferanten = this.artikelIndex.get(art);
			// groeste verfuegbare menge
			int max = 0;
			for (LiefertEntry entry : lieferanten) {
				max = Math.max(max, entry.getAnzahl());
			}
			int amount;
			if (i == keypos && type == 1) {
				amount = max + this.rand.nextInt(100) + 1;	
			} else {
				amount = this.rand.nextInt(max)+1;
			}
			
			Bestellposten b;
			if (i == keypos && type == 2) {
				b = null;
			} else {
				// kandidaten finden
				List<LiefertEntry> candidates = new LinkedList<LiefertEntry> ();
				// 1. kandidaten mit ausreichender menge filtern, dabei minimalen stückpreis bestimmen
				double min_price = Integer.MAX_VALUE;
				for (LiefertEntry entry : lieferanten) {
					if (entry.getAnzahl() >= amount) {
						candidates.add(entry);
						min_price = Math.min(min_price, entry.getPreis());
					}
				}
				HashSet<String> candidatenames = new HashSet<String> ();
				for (LiefertEntry entry : candidates) {
					if (entry.getPreis() == min_price) {
						candidatenames.add(entry.getLieferant());
					}
				}
				b = new Bestellposten (art, i+1, amount, 0.0, "N", "O", new Date (), new Date (), new Date(), getRandomVersandhinweis(), getRandomVersandart(), candidatenames);
			}
			posten.add(b);
		}
		Bestellung bestellung = new Bestellung (getNextBestell_Nr(), posten, type == 0);
		return bestellung;
	}
	
	protected Bestellung createChangedBestellung (Statement statement, int type) {
		// bestellnummer wählen
		String bestell_nr = getRandomBestell_Nr();
		// alle Posten lesen
		String sql = "select ARTIKEL,POSTENNUMMER,ANZAHL,RABATT,RETOURSTATUS,POSTENSTATUS,VERSANDDATUM,BESTAETIGUNGSDATUM,EMPFANGSDATUM,VERSANDANWEISUNG,VERSANDART,LIEFERANT,PREIS,STEUER from bestellposten where BESTELL_NR = ";
		LinkedList<Bestellposten> tmp = new LinkedList<Bestellposten> ();
		try {
			statement.execute(sql + bestell_nr);
			ResultSet set = statement.getResultSet();
			
			while (set.next()) {
				tmp.add(new Bestellposten(set.getString(1).trim(), set.getInt(2), set.getInt(3), set.getDouble(4), set.getString(5).trim(), set.getString(6).trim(), set.getDate(7), set.getDate(8), set.getDate(9), set.getString(10).trim(), set.getString(11).trim(), null));
			}
			set.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		// pick random bestellposten and modify
		int index = this.rand.nextInt(tmp.size());
		Bestellposten posten = tmp.get(index);
		
		List<LiefertEntry> lieferanten = this.artikelIndex.get(posten.getArtikel());
		// groeste verfuegbare menge
		int max = 0;
		for (LiefertEntry entry : lieferanten) {
			max = Math.max(max, entry.getAnzahl());
		}
		
		int amount;
		if (type == 1) {
			amount = max + this.rand.nextInt(100) + 1;	
		} else {
			amount = this.rand.nextInt(max)+1;
		}
		posten.setAnzahl( amount ) ;
		
		Bestellung bestellung = new Bestellung(Integer.parseInt(bestell_nr), tmp, type == 0);
		return bestellung;
	}
	
	protected Lieferant createRandomLieferant (int id, int type) {
		if (id < 0) {
			// neuen lieferanten anlegen
			id = ++this.maxLieferant;
			// neue posten generieren
			int size = this.rand.nextInt(17)+3;
			// select keyposition
			int keypos = this.rand.nextInt(size);
			
			List<LiefertEntry> angebot = new LinkedList<LiefertEntry> ();
			Vector<String> artikel = new Vector<String> (size);
			for (int i = 0; i < size; i++) {
				if (i == keypos && type == 2) {
					angebot.add(null);
				} else {
					// artikel aussuchen
					String art = null;
					do {
						art = getRandomArtikel();
					} while (artikel.contains(art));
					artikel.add(art);
					// aktuellen durchschnittspreis für diesen artikel ermitteln
					List<LiefertEntry> entries = this.artikelIndex.get(art);
					double preis = 0.0d;
					for (LiefertEntry e : entries) {
						preis += e.getPreis();
					}
					preis /= (double) entries.size();
					double newprice;
					if (i == keypos && type == 1) {
						// zu großen preis wählen
						newprice = preis * ((this.rand.nextInt(40) + 111) / 100.0);
					} else {
						// akzeptablen preis wählen
						newprice = preis * ((this.rand.nextInt(40) + 69) / 100.0);
					}
					newprice = Math.round(newprice * 10) / 10;
					angebot.add(new LiefertEntry (Integer.toString(id), art, this.rand.nextInt(400)+50, newprice));
				}
			}
			
			return new Lieferant (id, "Lieferant" + id, "42 Wallaby Way", this.rand.nextInt(25), "13-715-945-6730", angebot, type);
			
		} else {
			// bestehendes angebot ändern
			// irgendeinen lieferanten wählen
			String lieferant = this.lieferanten[this.rand.nextInt(this.lieferanten.length)];
			List<LiefertEntry> angebot = this.lieferantIndex.get (lieferant);
			List<LiefertEntry> neuesangebot = new LinkedList<LiefertEntry> ();
			int keypos = this.rand.nextInt(angebot.size());
			int i = 0;
			for (LiefertEntry entry : angebot) {
				
				double newprice = 0.0d;
				if (i == keypos && type == 2) {
					neuesangebot.add (null);
				} else {
					if (type == 1) {
						// preis zu hoch ansetzen
						newprice = entry.getPreis() * ((this.rand.nextInt(40) + 120) / 100.0);
					} else {
						// akzeptablen preis generieren
						newprice = entry.getPreis() * ((this.rand.nextInt(40) + 69) / 100.0);
					}
					
					int anzahl = 0;
					if (i == keypos && type == 3) {
						// zu geringe anzahl generieren
						anzahl = this.rand.nextInt (entry.getAnzahl());
					} else {
						// ausreichend große anzahl
						anzahl = entry.getAnzahl() + 1 + this.rand.nextInt(400);
					}
					neuesangebot.add(new LiefertEntry (lieferant, entry.getArtikel(), anzahl, newprice));
				}
				i++;
			}
			
			return new Lieferant (Integer.parseInt(lieferant), neuesangebot, type);
			
		}
	}
	
	public static void main(String[] args) {
		Connection con = null;
		try {
			con = DriverManager.getConnection( "jdbc:db2://gnu.dima.tu-berlin.de:50000/"+ ConnectionConfig.DB2_DB, ConnectionConfig.DB2_USER, ConnectionConfig.DB2_PW);
			DataManager manager = new DataManager (con);
			System.out.println("created data manager");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
