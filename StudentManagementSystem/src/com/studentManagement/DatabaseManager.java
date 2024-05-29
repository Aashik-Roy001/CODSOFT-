package com.studentManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/studentManagement";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "Example@2022#";

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
	}

	public static void close(Connection connection, Statement statement, ResultSet resultSet) {
		try {
			if (resultSet != null)
				resultSet.close();
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
