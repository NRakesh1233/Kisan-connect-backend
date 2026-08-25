package Kisan.connect.app.Config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import Kisan.connect.app.Repository.UserRepo;
import Kisan.connect.app.entities.Users;

@Service
public class CustomUserDetailsService  implements UserDetailsService{
	
	private final UserRepo userRepo;
	

	public CustomUserDetailsService(UserRepo repository){

	    this.userRepo = repository;

	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		  System.out.println("Email received = " + email);

	
	Optional<Users> us=userRepo.findByEmail(email);
	
	  System.out.println("User found = " + us.isPresent());
	
			Users user=us.get();
			
			 if (us.isEmpty()) {
			        throw new UsernameNotFoundException("User not found");
			    }
	if(user!=null) {
		return new CustomUserDetails(user);
	}
	else {
		throw new UsernameNotFoundException("user not fuund");
	}
	
		
		
	}

}
