package com.customer.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.customer.config.SessionMgm;
import com.customer.dao.DBConfig;
import com.customer.model.Customer;
import com.customer.model.ResponseData;
import com.customer.model.SignUpData;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private SessionMgm mgm;
	
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
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public SignUpData signUp(SignUpData data) {
		try {
			String query = "insert into signup (userid,pass) values (?,?)";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, data.getUserId());
			pst.setString(2, data.getPass());

			pst.execute();
			System.out.println("query running");
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			try {
//				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return data;
	}

	@Override
	public ResponseData login(SignUpData log) {
		ResponseData dbLog = new ResponseData();
		try {
			String query = "select * from signup where userid='" + log.getUserId() + "' and pass='" + log.getPass()
					+ "'";
			System.out.println("Query--> " + query);
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			dbLog.setHttpStatus(HttpStatus.BAD_REQUEST);
			while (rs.next()) {
				dbLog.setHttpStatus(HttpStatus.OK);
				String uniqueID = UUID.randomUUID().toString();

				String query_uid = "update signup set uid='" + uniqueID + "' where userid='" + log.getUserId() + "'";
				System.out.println("UID Query--> " + query_uid);
				pst = conn.prepareStatement(query_uid);
				pst.executeUpdate();
				dbLog.setUniqueIdentifier(uniqueID);
				
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				mgm.addSession(uniqueID, timestamp);
				
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbLog;
	}

	public boolean validateUID(String uniqueId) throws SQLException {

		String query = "select * from signup where uid='" + uniqueId + "'";
		PreparedStatement pst = conn.prepareStatement(query);
		System.out.println("Query--> " + query);
		ResultSet result = pst.executeQuery();
		System.out.println(result.toString());
		while (result.next()) {
			return true;
		}
		return false;
	}

}
