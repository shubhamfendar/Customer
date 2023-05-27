package com.customer.config;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class SessionMgm {

	private Map<String, Timestamp> session = new HashMap<>();

	private static final long sessionTimeout = 1 * 60 * 1000;

	long currentTime = System.currentTimeMillis();

	public void addSession(String sessionId, Timestamp timestamp) {
		session.put(sessionId, timestamp);
		System.out.println("Session: "+ timestamp);
		
	}

	public Timestamp getSession(String sessionId) {
		Timestamp timestamp = session.get(sessionId);

		if (timestamp == null) {
			throw new IllegalStateException("Session not found");
		}
		System.out.println("Limit time:  "+sessionTimeout);
		Timestamp timestampNow = new Timestamp(System.currentTimeMillis());
		System.out.println("Time remaining: " + (timestampNow.getTime() - timestamp.getTime()));

		if (timestampNow.getTime() - timestamp.getTime() > sessionTimeout) {
			throw new IllegalStateException("Session expired");
		}
		return timestamp;

	}

}
