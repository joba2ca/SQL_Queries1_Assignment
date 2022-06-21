package de.tuberlin.dima.dbpra.exercises;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SQL2Exercises {

	/**
	 * Queryergebnis:
	 * Finden Sie die Marken deren durchschnittlicher Produktpreis höher als der Durchschnittspreis aller Produkte ist.
	 * <p/>
	 * Ergebnisschema:
	 * [Marke(↑) | d_preis]
	 * <p/>
	 * Punkte:
	 * 0.5
	 *
	 * @return SQL Query für Aufgabe 1
	 */
	static public String getSQLQuery01() {
		return getQueryString(1);
	}

	/**
	 * Queryergebnis:
	 * Finden Sie alle Lieferanten, die keine Artikel der Marke "Brand#53" führen.
	 * <p/>
	 * Ergebnisschema:
	 * [Name(↑) | Adresse]
	 * <p/>
	 * Punkte:
	 * 0.5
	 *
	 * @return SQL Query für Aufgabe 2
	 */
	static public String getSQLQuery02() {
		return getQueryString(2);
	}

	/**
	 * Queryergebnis:
	 * Finden sie für jeden Artikel des Typs "MEDIUM ANODIZED TIN" den Lieferanten aus der Region 'ASIA',
	 * der für den Artikel den niedrigsten Lieferpreis hat.
	 * Der Lieferpreis eines Lieferanten für einen Artikel ist das gleichnamige Attribut auf der "Liefert" Relation.
	 * <p/>
	 * Ergebnisschema:
	 * [Artikel_Nr (↑) | Lieferant_Name (↑) | Lieferpreis]
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
	 * Finden Sie die Sachbearbeiter, deren durchschnittliche Bestellungsbearbeitung (Versanddatum - Bestelldatum)
	 * mehr als 5% über dem Gesamtdurchschnitt liegt.
	 * <p/>
	 * Ergebnisschema:
	 * [Bearbeiter(↑)]
	 * <p/>
	 * Punkte:
	 * 1.0
	 *
	 * @return SQL Query für Aufgabe 4
	 */
	static public String getSQLQuery04() {
		return getQueryString(4);
	}

	/**
	 * Queryergebnis:
	 * Finden Sie Bestellungen, die zwischen dem 15.07.1996 und dem 15.10.1996 (jeweils inkl.) aufgegeben wurden,
	 * und bei denen mindestens ein Bestellposten später angeliefert wurde, als zugesichert.
	 * Geben sie pro Bestellpriorität aus, für wie viele Bestellungen dies der Fall war.
	 * Hinweis: Das Attribut "Empfangsdatum" beschreibt, wann ein Bestellposten angekommen ist.
	 * Das Attribute "Bestätigungsdatum" beschreibt, für wann die Ankunft des Bestellpostens zugesichert wurde.
	 * <p/>
	 * Ergebnisschema:
	 * [Prioritaet (↑) | Anzahl]
	 * <p/>
	 * Punkte:
	 * 1.0
	 *
	 * @return SQL Query für Aufgabe 5
	 */
	static public String getSQLQuery05() {
		return getQueryString(5);
	}

	/**
	 * Queryergebnis:
	 * Bestimmen die Sie Marke(n), die von den meisten Lieferanten angeboten wird.
	 * <p/>
	 * Ergebnisschema:
	 * [Marke] (↑)
	 * <p/>
	 * Punkte:
	 * 1.0
	 *
	 * @return SQL Query für Aufgabe 6
	 */
	static public String getSQLQuery06() {
		return getQueryString(6);
	}

	/**
	 * Queryergebnis:
	 * Finden Sie für jede Versandart die Marke, deren Bestellposten am häufigsten mit dieser Methode versandt worden sind.
	 * <p/>
	 * Ergebnisschema:
	 * [Versandart (↑) | Marke | Anzahl]
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
	 * Finden Sie für alle Marken diejenigen Artikel, die in den obersten 1% der Preisspanne der Marke sind.
	 * Die Preisspanne ist die Differenz zwischen dem billigsten und dem teuersten Artikel.
	 * <p/>
	 * Ergebnisschema:
	 * [Marke (↑1) | Name (↑2) ]
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
	 * Berechnen sie für jeden Kunden, wie viele seiner Bestellungen vollständig mit Artikeln von lokalen Lieferanten erfüllt worden sind.
	 * Lokaler Lieferant bedeutet, dass der Lieferant in dem gleichen Land wie der Kunde ansässig ist.
	 * Geben sie alle Kunden aus, die mindestens eine solche lokale Bestellung hatten, absteigend nach der Anzahl der lokalen Bestellungen.
	 * <p/>
	 * Ergebnisschema:
	 * [KundenName(↑2) | Anzahl (↓1)]
	 * <p/>
	 * Punkte:
	 * 1.5
	 *
	 * @return SQL Query für Aufgabe 9
	 */
	static public String getSQLQuery09() {
		return getQueryString(9);
	}

	/**
	 * Queryergebnis:
	 * Bestimmen Sie für alle Lieferanten aus "FRANCE" jeweils die Artikel, bei denen der verfügbare Lieferwert mehr ist,
	 * als 1/500 des verfügbaren Lieferwertes aller Artikel dieses Lieferanten.
	 * Der verfügbare Lieferwert eines Artikels ist das Produkt aus der verfügbaren Anzahl und dem Lieferpreis.
	 * <p/>
	 * Ergebnisschema:
	 * [Lieferanten_Nr (↑1) | Artikel (ID) | Wert (↓2)]
	 * <p/>
	 * Punkte:
	 * 1.5
	 *
	 * @return SQL Query für Aufgabe 10
	 */
	static public String getSQLQuery10() {
		return getQueryString(10);
	}

	static private String getQueryString(int i) {
		StringBuilder query = new StringBuilder();

		try {
			String path = String.format("de/tuberlin/dima/dbpra/exercises/SQL2/SQL2Query%02d.sql", i);
			InputStream is = SQL2Exercises.class.getClassLoader().getResourceAsStream(path);
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
