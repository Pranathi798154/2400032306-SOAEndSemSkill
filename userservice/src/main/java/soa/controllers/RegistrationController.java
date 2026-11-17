package soa.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import soa.models.Users;
import soa.services.UsersService;

@RestController
@RequestMapping("/")
public class RegistrationController {

	UsersService US;
	
	public RegistrationController(UsersService US)
	{
		this.US = US;
	}
	
	@PostMapping("register")
	public Object register(@RequestBody Users U)
	{
		return US.validateUser(U.getUsername(), U.getPassword());
	}
}
