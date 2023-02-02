package com.customer.dao;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.context.annotation.Configuration;

@Configuration
public class DBConfig {

	static Connection conn;

	public static Connection create() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/customermgm";
			String user = "root";
			String password = "root";
			conn = DriverManager.getConnection(url, user, password);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

}
