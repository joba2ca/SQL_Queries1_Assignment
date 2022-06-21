package de.tuberlin.dima.dbpra.exercises;

import com.ibm.db2.jcc.am.SqlWarning;
import de.tuberlin.dima.dbpra.interfaces.SQL5ExercisesInterface;
import de.tuberlin.dima.dbpra.interfaces.transactions.Bestellposten;
import de.tuberlin.dima.dbpra.interfaces.transactions.Bestellung;
import de.tuberlin.dima.dbpra.interfaces.transactions.Lieferant;
import de.tuberlin.dima.dbpra.interfaces.transactions.LiefertEntry;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

import static java.sql.Connection.TRANSACTION_SERIALIZABLE;

public class TransactionsExercises implements SQL5ExercisesInterface {

	public void bestellungBearbeiten(Connection connection, Bestellung bestellung) {
		/**
		 * WO COMMITTEN?
		 */
		Iterator<Bestellposten> bestellpostenIterator;
		Savepoint savepoint;
		DatabaseMetaData dbmd;
		ResultSet lieferant;
		PreparedStatement findLieferant;
		int lieferpreis;
		int bpPreis;
		int gesamtpreis = 0;

		try {
			connection.setAutoCommit(false); 												//PRÜFEN!! KANN MAN BEI AUTOCOMMIT = TRUE EINEN ROLLBACK DURCHFÜHREN?
			//savepoint = connection.setSavepoint();
			dbmd = connection.getMetaData();
			//if (dbmd.supportsTransactionIsolationLevel(Connection.TRANSACTION_SERIALIZABLE)){
				connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED); 	//richtiges Level?
			//}

			bestellpostenIterator = bestellung.getBestellposten();
			if(bestellpostenIterator == null){							//Ist das so gemeint in der Aufgabe?
				connection.rollback();
				return;
			}

			createBestellung(connection, bestellung, gesamtpreis);

			while(bestellpostenIterator.hasNext()){
				Bestellposten currentBP = bestellpostenIterator.next();
				if (currentBP == null){									//Ist das so gemeintin der Aufgabe?
					connection.rollback();
					return;
				}
				/**
				 * 1. find the cheapest lieferant
				 */
				findLieferant = connection.prepareStatement("SELECT LIEFERANT, LIEFERPREIS, ANZAHL_VERFUEGB FROM Liefert " +
						"WHERE ARTIKEL = ? AND ANZAHL_VERFUEGB >= ? " +
						"ORDER BY LIEFERPREIS ASC " +
						"FETCH FIRST 1 ROWS ONLY");
				findLieferant.setString(1, currentBP.getArtikel());
				findLieferant.setInt(2, currentBP.getAnzahl());
				lieferant = findLieferant.executeQuery();

				if( !lieferant.next() ){
					//ResultSet lieferant is empty
					connection.rollback();
					return;
				}
				/**
				 * 2. set prices
				 */
				lieferpreis = lieferant.getInt(2);
				bpPreis = ( lieferpreis * currentBP.getAnzahl() ) + 1; // + 1 wegen Aufgabenstellung
				gesamtpreis += bpPreis;
				/**
				 * 3. create new entry in BESTELLPOSTEN
				 */
				//createBestellposten(connection, bestellung, lieferant, bpPreis, currentBP);
				PreparedStatement prepPosten;
				prepPosten = connection.prepareStatement("INSERT INTO BESTELLPOSTEN" +
						"(BESTELL_NR, ARTIKEL, LIEFERANT, POSTENNUMMER, ANZAHL, PREIS, RABATT, STEUER, RETOURSTATUS, " +
						"POSTENSTATUS, VERSANDDATUM, BESTAETIGUNGSDATUM, EMPFANGSDATUM, VERSANDANWEISUNG, VERSANDART) " +
						"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				prepPosten.setInt(1, bestellung.getBestell_Nr());
				prepPosten.setString(2, currentBP.getArtikel());
				prepPosten.setInt(3, lieferant.getInt(1));
				prepPosten.setInt(4, currentBP.getPostennummer());
				prepPosten.setInt(5, currentBP.getAnzahl());
				prepPosten.setInt(6, bpPreis);
				prepPosten.setDouble(7, currentBP.getRabatt());
				prepPosten.setDouble(8, 0.0);
				prepPosten.setString(9, currentBP.getRetourstatus());
				prepPosten.setString(10, currentBP.getPostenstatus());
				prepPosten.setString(11, currentBP.getVersanddatum());
				prepPosten.setString(12, currentBP.getBestaetigungsdatum());
				prepPosten.setString(13, currentBP.getEmpfangsdatum());
				prepPosten.setString(14, currentBP.getVersandanweisung());
				prepPosten.setString(15, currentBP.getVersandart());
				prepPosten.executeUpdate();
				/**
				 * 4. Update Table LIEFERT with new, lowered amounts
				 */
				PreparedStatement prepLiefertUpdate;
				prepLiefertUpdate = connection.prepareStatement("UPDATE LIEFERT " +
						"SET ANZAHL_VERFUEGB = ? " +
						"WHERE ARTIKEL = ? AND LIEFERANT = ?");
				prepLiefertUpdate.setInt(1, lieferant.getInt(3) - currentBP.getAnzahl());
				prepLiefertUpdate.setString(2, currentBP.getArtikel());
				prepLiefertUpdate.setString(3, lieferant.getString(1));
				prepLiefertUpdate.executeUpdate();
			}

			PreparedStatement prepBestellungUpdate;
			prepBestellungUpdate = connection.prepareStatement("UPDATE BESTELLUNG " +
					"SET GESAMTPREIS = ? " +
					"WHERE BESTELL_NR = ?");
			prepBestellungUpdate.setInt(1, gesamtpreis);
			prepBestellungUpdate.setInt(2, bestellung.getBestell_Nr());
			prepBestellungUpdate.executeUpdate();

			connection.commit();
		}catch (SQLException e){
			e.printStackTrace();
			try{
				connection.rollback();
			}catch (SQLException se){
				e.printStackTrace();
			}
		}
	}

	private void createBestellung(Connection connection, Bestellung bestellung, int gesamtpreis) throws SQLException {
		PreparedStatement prepBestellung;
		prepBestellung = connection.prepareStatement("INSERT INTO BESTELLUNG " +
				"(BESTELL_NR, KUNDE, STATUS, GESAMTPREIS, BESTELLDATUM, BESTELLPRIORITAET, BEARBEITER, " +
				"VERSANDPRIORITAET)" +
				" VALUES (?,?,?,?,?,?,?,?)");
		prepBestellung.setInt(1, bestellung.getBestell_Nr());
		prepBestellung.setInt(2, bestellung.getKunde());
		prepBestellung.setString(3, bestellung.getStatus());
		prepBestellung.setInt(4, gesamtpreis);
		prepBestellung.setString(5, DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now()) );
		prepBestellung.setString(6, bestellung.getBestellprioritaet());
		prepBestellung.setString(7, bestellung.getBearbeiter());
		prepBestellung.setInt(8, bestellung.getVersandprioritaet());
		prepBestellung.executeUpdate();
	}

	public void neueLieferdatenEinfuegen(Connection connection,  Lieferant lieferant) {
		Iterator<LiefertEntry> angebot;
		Savepoint savepoint;
		DatabaseMetaData dbmd;
		PreparedStatement prepAvgOld;
		PreparedStatement prepAmountOld;
		ResultSet newlieferant;
		LiefertEntry entry;

		int avgNew, avgOld, priceNew = 0 , amountOld, amountNew, countNew = 0;

		try{
			connection.setAutoCommit(false);
			savepoint = connection.setSavepoint();
			angebot = lieferant.getLiefertEntries();
			dbmd = connection.getMetaData();

			//if (dbmd.supportsTransactionIsolationLevel(Connection.TRANSACTION_SERIALIZABLE)){
				connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED); 	//richtiges Level?
			//}
			if(!angebot.hasNext()){
				//case: lieferant has no articles offered
				connection.rollback();
				return;
			}
			/**
			 * 1. check if lieferant is new or not
			 */
			PreparedStatement checkIfNew;
			checkIfNew = connection.prepareStatement("SELECT * FROM LIEFERANT " +
					"WHERE LIEFERANTEN_NR = ? ");
			checkIfNew.setInt(1, lieferant.getLieferanten_nr());
			newlieferant = checkIfNew.executeQuery();
			//newlieferant = checkIfNewLieferant(connection, lieferant);
			if( !newlieferant.next() /*!newlieferant.isBeforeFirst()*/ ){ //Diese Methode darf nur für ResultSet-Objekte aufgerufen werden, die verschiebbar sind (Typ TYPE_SCROLL_SENSITIVE oder TYPE_SCROLL_INSENSITIVE). ERRORCODE=-4476, SQLSTATE=null
				/**
				 * 2. case: new lieferant
				 */
				int avgAll;
				insertNewLieferant(connection, lieferant);
				while (angebot.hasNext()){
					/**
					 * 2.0 add each entry to LIEFERT
					 */
					entry = angebot.next();
					if(entry == null){								//potentieller Fehler
						connection.rollback();
						return;
					}

					/**
					 * 2.1 compute avg price for this article (entry) from all vendors
					 */
					PreparedStatement prepAvgAll;
					prepAvgAll= connection.prepareStatement( "SELECT avg(LIEFERPREIS) FROM LIEFERT " +
							"WHERE ARTIKEL = ?");
					prepAvgAll.setString(1, entry.getArtikel());
					ResultSet res;
					res = prepAvgAll.executeQuery();
					res.next();
					avgAll = res.getInt(1);

					if(entry.getPreis() > avgAll * 1.1){
						//Abbrechen, falls Artikel zu teuer
						connection.rollback(savepoint);
						return;
					}
					/**
					 * 2.1 insert new offer into table LIEFERT
					 */
					PreparedStatement prepInsertLiefert;
					prepInsertLiefert = connection.prepareStatement("INSERT INTO LIEFERT " +
							"VALUES (?,?,?,?)");
					prepInsertLiefert.setString(1, entry.getArtikel());
					prepInsertLiefert.setInt(2, lieferant.getLieferanten_nr() );
					prepInsertLiefert.setInt(3, entry.getAnzahl());
					prepInsertLiefert.setDouble(4, entry.getPreis());
					prepInsertLiefert.executeUpdate();
				}
			}else{
				/**
				 * 3. case: lieferant already known
				 */
				/**
				 * 3.1 get average price of LIEFERANTs Products, before update (->old)
				 */
				prepAvgOld = connection.prepareStatement("SELECT avg(LIEFERPREIS) FROM LIEFERT " +
						"WHERE LIEFERANT = ?");
				prepAvgOld.setInt(1, lieferant.getLieferanten_nr());
				ResultSet res;
				res = prepAvgOld.executeQuery();
				res.next();
				avgOld = res.getInt(1);

				prepAmountOld = connection.prepareStatement("SELECT ANZAHL_VERFUEGB FROM LIEFERT " +
						"WHERE LIEFERANT = ? AND ARTIKEL = ?");
				prepAmountOld.setInt(1, lieferant.getLieferanten_nr());

				while (angebot.hasNext()){
					entry = angebot.next();
					if (entry == null){
						connection.rollback();
						return;
					}
					/**
					 * 3.2 get old amount of current product
					 */
					prepAmountOld.setString(2, entry.getArtikel());
					ResultSet resOldAmount;
					resOldAmount = prepAmountOld.executeQuery();
					resOldAmount.next();
					amountOld = resOldAmount.getInt(1);

					amountNew = entry.getAnzahl();

					if(amountNew <= amountOld){		//Abbruch, wenn neue Anzahl nicht höher als alte ist
						connection.rollback(savepoint);
						return;
					}
					/**
					 * 3.3 update table LIEFERT with new amounts
					 */
					PreparedStatement prepUpdateLiefert;
					prepUpdateLiefert = connection.prepareStatement("UPDATE LIEFERT " +
							"SET ANZAHL_VERFUEGB = ?, LIEFERPREIS = ? " +
							"WHERE ARTIKEL = ? AND LIEFERANT = ?");
					prepUpdateLiefert.setInt(1, amountNew);
					prepUpdateLiefert.setDouble(2, entry.getPreis());
					prepUpdateLiefert.setString(3, entry.getArtikel());
					prepUpdateLiefert.setInt(4, lieferant.getLieferanten_nr() );
					prepUpdateLiefert.executeUpdate();

					priceNew += entry.getPreis();
					countNew++;
				}
				avgNew = priceNew / countNew;
				if(avgNew > avgOld * 1.1){			//Abbruch, wenn Preissteigerung zu hoch
					connection.rollback(savepoint);
					return;
				}
			}
			connection.commit();
		}catch (SQLException e){
			e.printStackTrace();
			try{
				connection.rollback();
			}catch (SQLException se){
				se.printStackTrace();
			}
		}
	}

	private void insertNewLieferant(Connection connection, Lieferant lieferant) throws SQLException {
		PreparedStatement prepUpdateLieferant;
		prepUpdateLieferant = connection.prepareStatement("INSERT INTO LIEFERANT " +
				"VALUES (?,?,?,?,?,?)");
		prepUpdateLieferant.setInt(1, lieferant.getLieferanten_nr());
		prepUpdateLieferant.setString(2, lieferant.getName());
		prepUpdateLieferant.setString(3, lieferant.getAdresse());
		prepUpdateLieferant.setInt(4, lieferant.getLand());
		prepUpdateLieferant.setString(5, lieferant.getTelefon());
		prepUpdateLieferant.setDouble(6, lieferant.getKontostand());
		prepUpdateLieferant.executeUpdate();
	}
}
