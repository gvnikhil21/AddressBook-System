package com.bridgelabs.addressbooksystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
	private static Connection con = null;

	// connects to the payroll_service database
	public static Connection getConnection() throws AddressBookException {
		String url = "jdbc:mysql://localhost:3306/addressbook_service";
		String user = "root";
		String password = "root";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
		} catch (SQLException | ClassNotFoundException e) {
			throw new AddressBookException(e.getMessage());
		}
		return con;
	}
}
