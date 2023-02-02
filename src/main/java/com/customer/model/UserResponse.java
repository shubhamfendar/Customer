package com.customer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

	public UserResponse(String token2, String string) {
		// TODO Auto-generated constructor stub
	}
	private String token;
	private String message;
}
