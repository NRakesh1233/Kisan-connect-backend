package Kisan.connect.app.Config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	
	private UserDetails userDetails;
	
	private final JwtUtills jwtUtills;
	private final UserDetailsService userDetailsService;
	public JwtAuthenticationFilter(JwtUtills jwtutills, UserDetailsService userDetailsService) {
		
		this.jwtUtills = jwtutills;
		this.userDetailsService = userDetailsService;
	}
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("JWT Filter Called");		
		String header=request.getHeader("Authorization");
		if(header==null||header.isEmpty()|| !header.startsWith("Bearer ")) {
			
			
			filterChain.doFilter(request, response);
			return;
			
		}
		
		String token=header.substring(7);
		String email=jwtUtills.extractEmail(token);		
		
		if(email!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			
			
			UserDetails userDetails=userDetailsService.loadUserByUsername(email);
			
			if(jwtUtills.validateToken(token, userDetails)) {
				
				
				UsernamePasswordAuthenticationToken authentication= new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				System.out.println(userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				
			}

			
		}
		
		filterChain.doFilter(request, response);
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
}
