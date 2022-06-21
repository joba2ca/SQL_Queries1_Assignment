package de.tuberlin.dima.dbpra.exercises;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SQL1Exercises {
	//------------------------------------
	//  SQL-Statements über eine Tabelle
	//------------------------------------

	/**
	 * Queryergebnis:
	 * Geben sie alle Ländernamen in absteigender alphabetischer Reihenfolge aus.
	 * <p/>
	 * Ergebnisschema:
	 * [Name (↓)]
	 * <p/>
	 * Punkte:
	 * 1.0
	 *
	 * @return SQL Query für Aufgabe 1
	 */
	static public String getSQLQuery01() {
		return getQueryString(1);
	}

	/**
	 * Queryergebnis:
	 * Geben sie alle möglichen Hersteller eines Artikels aus (ohne Duplikate), in alphabetischer Reihenfolge aus.
	 * <p/>
	 * Ergebnisschema:
	 * [Hersteller (↑)]
	 * <p/>
	 * Punkte:
	 * 1.0
	 *
	 * @return SQL Query für Aufgabe 2
	 */
	static public String getSQLQuery02() {
		return getQueryString(2);
	}

	/**
	 * Queryergebnis:
	 * Geben sie die Namen und Telefonnumern aller Kunden aus, die einen negativen Kontostand haben.
	 * Ordnen sie diese alphabetisch.
	 * <p/>
	 * Ergebnisschema:
	 * [Name (↑) | Telefon]
	 * <p/>
	 * Punkte:
	 * 1.0
	 *
	 * @return SQL Query für Aufgabe 3
	 */

	static public String getSQLQuery03() {
		return getQueryString(3);
	}

	/**
	 * Queryergebnis:
	 * Geben sie die Namen aller Artikel aus, deren Preis größer als 20 ist und deren Typ „POLISHED“ enthält. Ordnen sie die Namen alphabetisch absteigend.
	 * <p/>
	 * Ergebnisschema:
	 * [Name (↓)]
	 * <p/>
	 * Punkte:
	 * 2.0
	 *
	 * @return SQL Query für Aufgabe 4
	 */

	static public String getSQLQuery04() {
		return getQueryString(4);
	}

	/**
	 * Queryergebnis:
	 * Geben Sie die Bestellnummer, das Datum und den Status von Bestellungen aus, deren Status "F" ist, die in einem Oktober vor 1994 aufgegeben wurden und
	 * entweder eine Bestellnummer kleiner als 10000 oder größer als 500000 besitzen. Ordnen Sie nach Bestellnummern absteigend.
	 * <p/>
	 * Ergebnisschema:
	 * [Bestell_Nr (↓) | Bestelldatum | Status]
	 * <p/>
	 * Punkte:
	 * 3.0
	 *
	 * @return SQL Query für Aufgabe 5
	 */

	static public String getSQLQuery05() {
		return getQueryString(5);
	}

	/**
	 * Queryergebnis:
	 * Geben sie die Bestellposten (Bestellnummer) aus, bei denen die Lieferanten ID 785 ist und der Rabatt mindestens doppelt so groß wie die Steuer ist.
	 * Ordnen sie aufsteigend nach Preis.
	 * <p/>
	 * Ergebnisschema:
	 * [Bestell_Nr]
	 * <p/>
	 * Punkte:
	 * 2.0
	 *
	 * @return SQL Query für Aufgabe 6
	 */

	static public String getSQLQuery06() {
		return getQueryString(6);
	}

	/**
	 * Queryergebnis:
	 * Finden Sie Name und Kontostand von Kunden, die sich in einer Branche mit der Endung „E“ befinden.
	 * Ordnen Sie das Ergebnis alphabetisch nach Name.
	 * <p/>
	 * Ergebnisschema:
	 * [Name (↑) | Kontostand]
	 * <p/>
	 * Punkte:
	 * 1.0
	 *
	 * @return SQL Query für Aufgabe 7
	 */

	static public String getSQLQuery07() {
		return getQueryString(7);
	}

	/**
	 * Queryergebnis:
	 * Geben Sie die Anzahl und die Summe der Kontostände (Bilanz) aller Kunden aus der Branche AUTOMOBILE aus.
	 * <p/>
	 * Ergebnisschema:
	 * [Anzahl|Bilanz]
	 * <p/>
	 * Punkte:
	 * 1.0
	 *
	 * @return SQL Query für Aufgabe 8
	 */

	static public String getSQLQuery08() {
		return getQueryString(8);
	}

	/**
	 * Queryergebnis:
	 * Geben sie den durchschnittlichen Preis von Artikeln aus, deren Typ „POLISHED“ enthält.
	 * <p/>
	 * Ergebnisschema:
	 * [durchschnitt]
	 * <p/>
	 * Punkte:
	 * 1.0
	 *
	 * @return SQL Query für Aufgabe 9
	 */

	static public String getSQLQuery09() {
		return getQueryString(9);
	}

	/**
	 * Queryergebnis:
	 * Ermitteln sie für alle Versandarten die Gesamtanzahl und den durchschnittlichen Preis aller Bestellposten, bei denen der Rabatt kleiner als 0.08 ist.
	 * Sortieren sie absteigend nach der Versandart.
	 * <p/>
	 * Ergebnisschema:
	 * [Versandart ↓ | Summe | Durchschnitt]
	 * <p/>
	 * Punkte:
	 * 2.0
	 *
	 * @return SQL Query für Aufgabe 10
	 */

	static public String getSQLQuery10() {
		return getQueryString(10);
	}

	/**
	 * Queryergebnis:
	 * Ermitteln Sie für alle Kombinationen aus Versandart und Versandanweisung den Gesamtumsatz ohne Steuern aller Bestellposten,
	 * die zurückgesendet wurden (Status R). (Das Feld Preis beinhaltet bereits die Steuern, das Feld Steuer beinhaltet den Steuersatz.
	 * Ein Steuersatz von 19% hat hier beispielsweise den Wert 0,19).
	 * <p/>
	 * Ergebnisschema:
	 * [Versandart↑2|Versandanweisung↑1|Gesamtumsatz]
	 * <p/>
	 * Punkte:
	 * 2.0
	 *
	 * @return SQL Query für Aufgabe 11
	 */

	static public String getSQLQuery11() {
		return getQueryString(11);
	}

	/**
	 * Queryergebnis:
	 * Geben sie für jede Bearbeitungsdauer (Differenz in Tagen zwischen Empfangs- und Versanddatum) die Anzahl der Bestellposten aus.
	 * <p/>
	 * Ergebnisschema:
	 * [Bearbeitungsdauer | Bestellposten (↑)]
	 * <p/>
	 * Punkte:
	 * 2.0
	 *
	 * @return SQL Query für Aufgabe 12
	 */

	static public String getSQLQuery12() {
		return getQueryString(12);
	}

	/**
	 * Queryergebnis:
	 * Geben sie für alle Branchen den jeweils geringsten und größten Kontostand aller Kunden aus. Ordnen Sie nach Branchen alphabetisch.
	 * <p/>
	 * Ergebnisschema:
	 * [Branche (↑) | MinKonto | MaxKonto]
	 * <p/>
	 * Punkte:
	 * 2.0
	 *
	 * @return SQL Query für Aufgabe 13
	 */

	static public String getSQLQuery13() {
		return getQueryString(13);
	}

	/**
	 * Queryergebnis:
	 * Finden Sie für jede mögliche Kombination aus Versandart und Retourstatus die maximale Bearbeitungszeit und
	 * die mittlere Bearbeitungszeit (zwischen Empfang und Versand des Postens) absteigend sortiert nach durchschnittlicher Bearbeitungszeit.
	 * Betrachten sie nur Kombinationen deren Bestellposten einen Gesamt-Mindestumsatz (siehe Q11) von 1000000000 erreichen.
	 * <p/>
	 * Ergebnisschema:
	 * [versandart | retourstatus | max_zeit | durch_zeit↓]
	 * <p/>
	 * Punkte:
	 * 4.0
	 *
	 * @return SQL Query für Aufgabe 14
	 */

	static public String getSQLQuery14() {
		return getQueryString(14);
	}

	/**
	 * Queryergebnis:
	 * Geben sie alle Kundennamen zusammen mit ihren Ländern aus. Ordnen sie das Ergebnis nach dem Kundennamen alphabetisch.
	 * <p/>
	 * Ergebnisschema:
	 * [Kunde↑ | Land]
	 * <p/>
	 * Punkte:
	 * 2.0
	 *
	 * @return SQL Query für Aufgabe 15
	 */

	static public String getSQLQuery15() {
		return getQueryString(15);
	}

	/**
	 * Queryergebnis:
	 * Geben sie die minimale Steuer aus, die auf Artikel mit Preis kleiner 2000 erhoben wurde.
	 * <p/>
	 * Ergebnisschema:
	 * [MinSteuer]
	 * <p/>
	 * Punkte:
	 * 2.0
	 *
	 * @return SQL Query für Aufgabe 16
	 */

	static public String getSQLQuery16() {
		return getQueryString(16);
	}

	/**
	 * Queryergebnis:
	 * Geben Sie aus, wie viele Kunden aus jeder Region kommen, aufsteigend sortiert nach Namen der Region.
	 * <p/>
	 * Ergebnisschema:
	 * [Region (↑) | Anzahl]
	 * <p/>
	 * Punkte:
	 * 3.0
	 *
	 * @return SQL Query für Aufgabe 17
	 */

	static public String getSQLQuery17() {
		return getQueryString(17);
	}

	/**
	 * Queryergebnis:
	 * Geben sie die Lieferantennummern aller Lieferanten aus, die entweder aus Deutschland kommen
	 * oder einen Artikel teurer als 990 liefern. Jede Lieferantennummer darf nur einmal auftauchen.
	 * Sie brauchen keine Sortierung zu berücksichtigen.
	 * <p/>
	 * Ergebnisschema:
	 * [Lieferanten_Nr]
	 * <p/>
	 * Punkte:
	 * 6.0
	 *
	 * @return SQL Query für Aufgabe 18
	 */

	static public String getSQLQuery18() {
		return getQueryString(18);
	}

	/**
	 * Queryergebnis:
	 * Geben sie für jeden Hersteller aus, wie viel Rabatt im Durchschnitt beim Verkaufen ihrer Artikel gewährt wird.
	 * Hinweis: Das Attribut "Rabatt" der Tabelle "Bestellposten" enthält den prozentualen Rabatt, bezogen auf den Gesamtpreis.
	 * Geben Sie im Ergebnis den absoluten Rabatt aus.
	 * <p/>
	 * Ergebnisschema:
	 * [Hersteller | Rabatt]
	 * <p/>
	 * Punkte:
	 * 4.0
	 *
	 * @return SQL Query für Aufgabe 19
	 */

	static public String getSQLQuery19() {
		return getQueryString(19);
	}

	/**
	 * Queryergebnis:
	 * Geben Sie die Anzahl der Bestellungen und die Kundennamen aus, die mehr als 30 Bestellungen getätigt haben,
	 * absteigend sortiert nach Anzahl der Bestellungen.
	 * <p/>
	 * Ergebnisschema:
	 * [Name | Bestell_Anzahl (↓)]
	 * <p/>
	 * Punkte:
	 * 4.0
	 *
	 * @return SQL Query für Aufgabe 20
	 */

	static public String getSQLQuery20() {
		return getQueryString(20);
	}

	/**
	 * Queryergebnis:
	 * Finden sie alle Kunden (ohne Dublikate), dessen Gesamtpreis der Bestellung höher ist als der Gesamtpreis
	 * der Bestellung von einem Kunden am gleichen Bestelldatum beim gleichen Bearbeiter.
	 * <p/>
	 * Ergebnisschema:
	 * [Name]
	 * <p/>
	 * Punkte:
	 * 6.0
	 *
	 * @return SQL Query für Aufgabe 21
	 */

	static public String getSQLQuery21() {
		return getQueryString(21);
	}

	/**
	 * Queryergebnis:
	 * Finden Sie Kunden (mit Kundennummer, Name, Telefonnummer und deren Land) und erzeugtem Verlust, den sie durch zurückgegebene Artikel erzeugt haben.
	 * Geben Sie nur die Top 20 Kunden aus, die den meisten Verlust erzeugt haben. Hinweis, Retourstatus 'R' bedeutet zurückgegeben.
	 * <p/>
	 * Ergebnisschema:
	 * [Kunden_Nr | Name | Telefon | Land | Verlust (↓)]
	 * <p/>
	 * Punkte:
	 * 8.0
	 *
	 * @return SQL Query für Aufgabe 22
	 */

	static public String getSQLQuery22() {
		return getQueryString(22);
	}

	// DO NOT TOUCH

	static private String getQueryString(int i) {
		StringBuilder query = new StringBuilder();

		try {
			String path = String.format("de/tuberlin/dima/dbpra/exercises/SQL1/SQL1Query%02d.sql", i);
			InputStream is = SQL1Exercises.class.getClassLoader().getResourceAsStream(path);
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

			String line;
			while ((line = br.readLine()) != null) {
				if (!(line.startsWith("--") && line.isEmpty())) {
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
