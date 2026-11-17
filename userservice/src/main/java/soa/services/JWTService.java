package soa.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
	final String SECRETE_KEY = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM0123456789"; //by final keyword no need of creating constructor and it is a constant no need to change
    final SecretKey key = Keys.hmacShaKeyFor(SECRETE_KEY.getBytes()); //function helps us to convert key into bytes store in form of 

    //Generate Token
    public Object generateToken(Long user_id, String username) {
    	//whatever data we want to embedded into key (only keep necessary data) don't dump more data because it takes so much time so, optimize
    	Map<String, Object> payload = new HashMap<>();
    	payload.put("user_id", user_id);
    	payload.put("username",username);
    	
    	return Jwts.builder()
                   .claims(payload)   
    			   .issuedAt(new Date()) 
    			   .expiration(new Date(new Date().getTime()+86400000)) //we get current date and time (24 hours in ms)
                   .signWith(key) 
    	           .compact(); //convert into 64-bits
    }
    
  //Validate Token
    public Object validateToken(String token) throws Exception
    {
	    Claims claims = Jwts.parser()
						    .verifyWith(key)
						    .build()
						    .parseSignedClaims(token)
						    .getPayload();
	    if(claims == null || claims.getExpiration().before(new Date()))
	          throw new Exception("Invalid Token");
	    Map<String, Object> payload = new HashMap<>();
	    payload.put("user_id", claims.get("user_id"));
	    payload.put("username", claims.get("username"));
	    return payload;
    }
	 
    

}

