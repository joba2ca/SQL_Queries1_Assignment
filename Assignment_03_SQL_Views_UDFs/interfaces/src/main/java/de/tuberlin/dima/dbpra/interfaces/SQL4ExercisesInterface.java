package de.tuberlin.dima.dbpra.interfaces;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Moritz on 15.05.2015.
 */
public interface SQL4ExercisesInterface {
	/**
	 * (1.0P)
	 * Schreiben Sie zwei UDFs die (2D) kartesische Koordinaten
	 * in Polarkoordinaten umrechnen.
	 * (siehe Folien)
	 */
	void ex01Kart2Pol(Connection con) throws SQLException;

	/**
	 * Messungen (1.5P)
	 * (siehe Folien)
	 */
	void ex02Messungen(Connection con) throws SQLException;

	/**
	 * MessungStatistik (1.5P)
	 * <p/>
	 * (siehe Folien)
	 */
	void ex03MessungenStatistik(Connection con) throws SQLException;

	/**
	 * (2.0P)
	 * (siehe Folien)
	 */
	void ex04TriggerMessungen(Connection con) throws SQLException;

	/**
	 * areaUnderCurveSP (3P)
	 * <p/>
	 * (siehe Folien)
	 */
	void areaUnderCurveSP(Connection con) throws SQLException;
}
