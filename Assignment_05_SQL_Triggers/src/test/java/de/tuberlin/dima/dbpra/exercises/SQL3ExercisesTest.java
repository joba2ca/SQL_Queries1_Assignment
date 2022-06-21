package de.tuberlin.dima.dbpra.exercises;

import de.tuberlin.dima.dbpra.config.ConnectionConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

public class SQL3ExercisesTest extends SQL3Exercises {
	private Connection con;
	private Statement statement;

	@Before
	public void setUp() throws Exception {
		// connect to database
		con = DriverManager.getConnection("jdbc:db2://gnu.dima.tu-berlin.de:50000/" + ConnectionConfig.DB2_DB, ConnectionConfig.DB2_USER, ConnectionConfig.DB2_PW);

		// create statement
		statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		//throw new Exception ("database not found exception");
	}

	@Test
	public void test() throws SQLException {
		(new SQL3Exercises()).initKundenKontaktTable(con);

	}

	@After
	public void cleanUp() throws Exception {
		statement.getConnection().close();
	}
}
