package soa.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import soa.models.Users;
import soa.services.UsersService;

@RestController
@RequestMapping("/")
public class AuthenticationController {

	UsersService US;
	
	public AuthenticationController(UsersService US)
	{
		this.US = US;
	}
	
	@PostMapping("login")
	public Object login(@RequestBody Users U)
	{
		return US.validateUser(U.getUsername(), U.getPassword());
	}
	
	@GetMapping("getallusers")
	public Object getAllUsers(@RequestHeader String token)
	{
		return US.getAllUsers(token);
	}
}
