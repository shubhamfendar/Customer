package com.customer.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.customer.dao.DBConfig;
import com.customer.model.Customer;
import com.mysql.cj.protocol.Resultset;

@Service
public class CustomerServiceImpl implements CustomerService {
	List<Customer> list;
	Connection conn = DBConfig.create();

	@Override
	public List<Customer> getAllCustomer() {
		List<Customer> list = new ArrayList<>();
		try {
			String query = "select * from customer";
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				Customer c = new Customer();
				c.setCustId(rs.getInt(1));
				c.setName(rs.getString(2));
				c.setCity(rs.getString(3));
				c.setAge(rs.getInt(4));
				c.setCategory(rs.getString(5));
				list.add(c);
			}
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Customer getSingleCustomer(int customerId) {

		Customer c = new Customer();
		try {
			String query = "select * from customer where custId=?";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setInt(1, customerId);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				c.setCustId(rs.getInt(1));
				c.setName(rs.getString(2));
				c.setCity(rs.getString(3));
				c.setAge(rs.getInt(4));
				c.setCategory(rs.getString(5));
			}
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}

	@Override
	public Customer addCustomer(Customer customer) {
		try {
			String query = "insert into customer (custId,name,city,age,category) values (?,?,?,?,?)";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setInt(1, customer.getCustId());
			pst.setString(2, customer.getName());
			pst.setString(3, customer.getCity());
			pst.setInt(4, customer.getAge());
			pst.setString(5, customer.getCategory());
			pst.execute();

			pst.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
//		list.add(customer);
		
		finally {

			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return customer;
	}

	@Override
	public void updateCustomer(Customer customer, int customerId) {

		try {
			String query = "update customer set name=?,city=?,age=?,category=? where custId=?";
			PreparedStatement pst = conn.prepareStatement(query);

			pst.setString(1, customer.getName());
			pst.setString(2, customer.getCity());
			pst.setInt(3, customer.getAge());
			pst.setString(4, customer.getCategory());
			pst.setInt(5, customerId);
			pst.execute();

			pst.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {

			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

	}

	@Override
	public void deleteCustomer(int custID) {
		
		try {
			String query = "delete from customer where custId=?";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setInt(1, custID);
			pst.execute();

			pst.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		finally {

			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

	}

}
