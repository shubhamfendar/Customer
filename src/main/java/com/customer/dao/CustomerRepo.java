package com.customer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.customer.model.Customer;
import com.mysql.jdbc.Driver;

@Repository
public class CustomerRepo {

	Connection conn = DBConfig.create();

	public List<Customer> getAllCustomer() {
		List<Customer> customer = new ArrayList<>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from customer");
			while (rs.next()) {
				Customer c = new Customer();
				c.setCustId(rs.getInt(1));
				c.setName(rs.getString(2));
				c.setCity(rs.getString(3));
				c.setAge(rs.getInt(4));
				c.setCategory(rs.getString(5));
				customer.add(c);
			}
			rs.close();

		} catch (Exception e) {

			e.printStackTrace();
		}
		return customer;

	}

	public boolean addCustomer(Customer cust) throws SQLException {
		String sql = "insert into customer (custId,name,city,age,category)values(?,?,?,?,?)";
		boolean rowInserted = false;
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		Customer c = new Customer();
		c.setCustId(rs.getInt(1));
		c.setName(rs.getString(2));
		c.setCity(rs.getString(3));
		c.setAge(rs.getInt(4));
		c.setCategory(rs.getString(5));
		rowInserted = stmt.executeUpdate() > 0;
		return rowInserted;

	}
}
