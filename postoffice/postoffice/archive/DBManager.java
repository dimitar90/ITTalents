package postoffice.archive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
	private static final String URL = "jdbc:mysql://localhost:3306/9gagdatabase?useSSL=false";

	private static final String USER = "root";

	private static final String PASSWORD = "1234";

	private static Connection connetion;

	public DBManager() {
		// load driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Sorry, Driver not loaded or does not exist! Aborting.");
		}
		System.out.println("Driver loaded");
		// Create connection
		try {
			connection = DriverManager.getConnection("jdbc:mysql://" + DB_IP + ":" + DB_PORT + "/" + DB_NAME + "?" +DB_USE_SSL ,DB_USER,
					DB_PASSWORD);
 
		} catch (SQLException e) {
			System.out.println("Sorry, connection failed. Maybe wrong credetials?");
			System.out.println(e.getMessage());
		}
	}

	public Connection getConnection() {
		return this.connection;
	}

}