package Kisan.connect.app.Config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtUtills {
	
	
	//  secrete:  this_is_kisan_connect_project_created_by_rakesh

	private  final String secret="dGhpc19pc19raXNhbl9jb25uZWN0X3Byb2plY3RfY3JlYXRlZF9ieV9yYWtlc2g=";

	private Object String;
	
	  private static final long JWT_EXPIRATION = 1000 * 60 * 30;
	  
	  public String generateToken(UserDetails userDetails) {
		  
		  
		  HashMap<String,Object> clims=new HashMap<>();
		  clims.put("role",
				  
				  userDetails.getAuthorities()
				  .iterator()
				  .next()
				  .getAuthority()
				  
				  );
		  return createToken(clims,userDetails.getUsername());
		  
	  }

	public  String createToken(HashMap<String, Object> clims, String username) {
		// TODO Auto-generated method stub
		String Token=Jwts.builder()
				.claims(clims)
				.subject(username)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() +JWT_EXPIRATION))
				.signWith(getSignkey())
				.compact();
				
				
				 
				
		return Token;
	}
	public <T>T extractClims(String Token,Function<Claims,T> resolver){
		
		
		Claims clims=extractAllClims(Token);
		return resolver.apply(clims);
	}
	private Claims extractAllClims(String token) {
		// TODO Auto-generated method stub
		
		return Jwts.parser()
				.verifyWith(getSignkey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
				
	}
	public String extractEmail(String token){
		
		String email=extractClims(token,Claims::getSubject);
		
		
		
		return email;
		
	}
	public Date extractExpiration(String token) {
		
		return extractClims(token,Claims::getExpiration);
	}
	public String extractRole(String token) {
		
		return extractAllClims(token).get("role",String.class);
	}
	
	public boolean isTokenExp(String token) {
		
		
		return !extractExpiration(token).after(new Date());
	}
	public boolean validateToken(String token,UserDetails userDetails) {
		
		String email=extractEmail(token);
		
		return !isTokenExp(token)&& userDetails.getUsername().equals(email);
		
		
	}

	public SecretKey getSignkey() {
		
		byte[] keybytes=Decoders.BASE64.decode(secret);
		
		return Keys.hmacShaKeyFor(keybytes);
	}
	
	
}
