package de.tuberlin.dima.dbpra.exercises;

import de.tuberlin.dima.dbpra.interfaces.SQL4ExercisesInterface;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SQL4Exercises implements SQL4ExercisesInterface {

	/**
	 * (1.0P)
	 * Schreiben Sie zwei UDFs die (2D) kartesische Koordinaten
	 * in Polarkoordinaten umrechnen.
	 * (siehe Folien)
	 */
	@Override
	public void ex01Kart2Pol(Connection con) throws SQLException {

		Statement stmt = con.createStatement();

		try {
			stmt.execute(getQueryString(11));

			stmt.execute(getQueryString(12));

			System.out.println("1: functions created!");

		} catch (SQLException e) {
			System.err.println("1: Error creating functions: ");
			System.err.println(e.getMessage());
			throw e;

		}
	}

	/**
	 * Messungen (1.5P)
	 * (siehe Folien)
	 */
	@Override
	public void ex02Messungen(Connection con) throws SQLException {

		Statement stmt = con.createStatement();

		try {
			stmt.execute(getQueryString(2));

			System.out.println("2: view created!");

		} catch (SQLException e) {
			System.err.println("2: Error creating view: ");
			System.err.println(e.getMessage());
			throw e;

		}
	}

	/**
	 * MessungStatistik (1.5P)
	 * (siehe Folien)
	 */
	@Override
	public void ex03MessungenStatistik(Connection con) throws SQLException {

		Statement stmt = con.createStatement();

		try {
			stmt.execute(getQueryString(3));

			System.out.println("3: view created!");

		} catch (SQLException e) {
			System.err.println("3: Error creating view: ");
			System.err.println(e.getMessage());
			throw e;

		}
	}

	/**
	 * (2.0P)
	 * (siehe Folien)
	 */
	@Override
	public void ex04TriggerMessungen(Connection con) throws SQLException {

		Statement stmt = con.createStatement();

		try {
			stmt.execute(getQueryString(4));

			System.out.println("4: trigger created!");

		} catch (SQLException e) {
			System.err.println("4: Error creating trigger: ");
			System.err.println(e.getMessage());
			throw e;

		}
	}


	/**
	 * areaUnderCurveSP (3P)
	 * (siehe Folien)
	 */
	@Override
	public void areaUnderCurveSP(Connection con) throws SQLException {

		Statement stmt = con.createStatement();

		try {
			stmt.execute(getQueryString(5));

			System.out.println("5: procedure created!");

		} catch (SQLException e) {
			System.err.println("5: Error creating procedure: ");
			System.err.println(e.getMessage());
			throw e;

		}

	}

	private String getQueryString(int i) {
		StringBuilder query = new StringBuilder();

		try {
			String path = String.format("de/tuberlin/dima/dbpra/exercises/SQL4/SQL4Query%02d.sql", i);
			InputStream is = SQL4Exercises.class.getClassLoader().getResourceAsStream(path);
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
/*
CREATE PROCEDURE ComputeAreaUnderCurve AS
    DECLARE lastX DOUBLE;
    DECLARE lastY DOUBLE;
    DECLARE newCalc DOUBLE;
    DECLARE oldCalc DOUBLE;
BEGIN
    FOR seriesIds AS
        SELECT DISTINCT SERIES FROM MESSUNGEN
    DO
        FOR orderedMessungen AS
            SELECT * FROM MESSUNGEN, seriesIds
            WHERE seriesIds.SERIES = Messungen.SERIES
            ORDER BY x ASC
        DO
            newCalc = oldCalc + berechnungausaufgabe;
            oldCalc = newCalc;
            lastX = orderedMessungen.x;
            lastY = orderedMessungen.y;
        END FOR;
        IF EXISTS(SELECT * FROM area_under_curve a
                        WHERE a.series = seriesIds.SERIES) THEN
            UPDATE area_under_curve ON area_under_curve.series = seriesIds.series
                SET area_under_curve.area = newCalc;
        ELSE INSERT INTO area_under_curve
            VALUES seriesIds.SERIES, newCalc;
        END IF;
    END FOR;
END;

 */