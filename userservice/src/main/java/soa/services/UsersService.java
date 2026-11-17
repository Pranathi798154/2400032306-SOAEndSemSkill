package soa.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import soa.models.Users;
import soa.repository.UsersRepository;

@Service
public class UsersService {

	UsersRepository UR;
	JWTService JWT;
	
	public UsersService(UsersRepository UR, JWTService JWT)
	{
		this.UR = UR;
		this.JWT = JWT;
	}
	
	//Registration
	public Object registerUser(Users U) throws Exception
	{
		Map<String, Object> response = new HashMap<>();
		
		try
		{
			UR.save(U); //Insert into DB
			response.put("status", "success");
			response.put("message", "User registered successfully");
			response.put("data", U);
		}catch(Exception e)
		{
			response.put("status", "error");
			response.put("message", e.getMessage());
		}
		return response;
	}
	
	//Validating User
	public Object validateUser(String username, String password)
	{
		Map<String, Object> response = new HashMap<>();
		try
		{
			Users U = UR.validateCredentials(username, password);
			if(U == null)
				throw new Exception("Invalid username and password");
			
			Object token = JWT.generateToken(U.getUser_id(), U.getUsername());
			
			Map<String, Object> data = new HashMap<>();
			data.put("token", token);
			data.put("user_id", U.getUser_id());
			data.put("username", U.getUsername());
			
			response.put("status", "success");
			response.put("message", "Login successful");
			response.put("data", data);
		}catch (Exception e) 
		{
			response.put("status", "error");
			response.put("message", e.getMessage());
		}
		return response;
	}
	
	//Get All Users
	public Object getAllUsers(String token)
	{
		Map<String, Object> response = new HashMap<>();
		try
		{
			JWT.validateToken(token);
			
			List<Users> users = UR.findAll(); //READ ALL RECORDS FROM DB
			
			response.put("status", "success");
			response.put("data", users);
		}catch(Exception e)
		{
			response.put("status", "error");
			response.put("message", e.getMessage());
		}
		return response;
	}
	
}
