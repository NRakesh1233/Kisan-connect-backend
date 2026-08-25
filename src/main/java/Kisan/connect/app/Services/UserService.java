package Kisan.connect.app.Services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Kisan.connect.app.Config.CustomUserDetails;
import Kisan.connect.app.Config.JwtUtills;
import Kisan.connect.app.Exception.ResourceNotFoundException;
import Kisan.connect.app.Repository.AddressRepository;
import Kisan.connect.app.Repository.OtpRepository;
import Kisan.connect.app.Repository.UserRepo;
import Kisan.connect.app.Requests.AddProfileRequest;
import Kisan.connect.app.Requests.UserLoginRequest;
import Kisan.connect.app.Requests.UserRegisterRequest;
import Kisan.connect.app.Response.AuthenticationResponse;
import Kisan.connect.app.Response.UserResponse;
import Kisan.connect.app.entities.Active;
import Kisan.connect.app.entities.Address;
import Kisan.connect.app.entities.Gender;
import Kisan.connect.app.entities.OTP;
import Kisan.connect.app.entities.Role;
import Kisan.connect.app.entities.Users;
import Kisan.connect.app.entities.Verifyed;


@Service
public class UserService {

	
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private JwtUtills jwtUtills;
	@Autowired
	private OtpRepository otpRepository;
	
	public  String register(UserRegisterRequest request) {
		
		if(userRepo.findByEmail(request.getEmail()).isPresent()) {
			
			throw new AccessDeniedException("this email is alreardy have account");
			
		}
		
		 Users user = new Users();

	        user.setName(request.getName());
	        user.setEmail(request.getEmail());

	        user.setPassword(
	                passwordEncoder.encode(
	                        request.getPassword()));

	        user.setRole(request.getRole());
	        
	 
	        user.setMailVerified(Verifyed.YES);
	        user.setActive(Active.Yes);
	        user.setMobileVerified(Verifyed.YES);
	        user.setCreatedAt(LocalDate.now());
	        user.setUpdatedAt(LocalDate.now());
	        user.setBio(request.getBio());
	        user.setDateOfBirth(request.getDateOfBirth());
	        user.setGender(request.getGender());
	        user.setMobileNumber(request.getMobileNumber());
	        
//	        public Address(String fullName, String mobileNumber, String village, String district, String state, String pincode,
//	    			Users customer) {
	    	
	     Address ads=new Address(request.getName(),request.getMobileNumber(),request.getVillage(),request.getDistrict(),request.getState(),request.getPincode(),user,"HOME");
 
	        userRepo.save(user);
	        addressRepository.save(ads);

	        return "Registration Successful";

	}
	
	
	public AuthenticationResponse  login(UserLoginRequest request) {
		
		
	    
	    System.out.println("Email from request = " + request.getEmailId());
	    System.out.println("Password from request = " + request.getPassword());
	    try {

	        authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                        request.getEmailId(),
	                        request.getPassword()));

	    } catch (Exception e) {

	        e.printStackTrace();

	        throw e;
	    }
		
		
		Users us=userRepo.findByEmail(request.getEmailId())
				  .orElseThrow(() -> new ResourceNotFoundException("user not found"));
		UserDetails user=new CustomUserDetails(us);
		
		String token =jwtUtills.generateToken(user);
		

	    System.out.println("Step 3 token"+token);
		
		return new AuthenticationResponse(
				token,
				user.getUsername(),
				user.getAuthorities()
				.iterator()
				.next()
				.getAuthority(),
				"user logged successfully"
				);
		
	}


	public String addProfilePic(AddProfileRequest request) throws IOException {
		// TODO Auto-generated method stub
		
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
	Users user=userRepo.findByEmail(authentication.getName())
			  .orElseThrow(() -> new ResourceNotFoundException("user not found"));
		String name=UUID.randomUUID()+"_"+request.getProfilePic().getOriginalFilename();
		
		Path path=Paths.get("uploads/profiles",name);
		
		Files.copy(request.getProfilePic().getInputStream(), path);
		
		user.setProfilePic(name);
		userRepo.save(user);
		return "profile image added successfully!!";
	}


	public ResponseEntity<String> generateOtp(String mobile) {
		
		SecureRandom random =new SecureRandom();
		int otp=100000 +random.nextInt(900000);
		
		System.out.println("your otp is "+otp);
		
		//	public OTP(String mobileNumber, String otp, LocalDateTime expiryTime, boolean verified) {

		LocalDateTime exp=LocalDateTime.now().plusMinutes(2);
		OTP o=new OTP(mobile,otp,exp,false);
		
		otpRepository.save(o);
		return ResponseEntity.ok("otp sent succefully");
	}


	public ResponseEntity<String> verifyOtp(int otp, String mobile) {
		// TODO Auto-generated method stub
		
		OTP table=otpRepository.findByMobileNumber(mobile);
		if(LocalDateTime.now().isAfter(table.getExpiryTime())) {
			otpRepository.deleteById(table.getId());
			return ResponseEntity.ok("your otp is expired please send request for new otp");
			
		}
		if(otp==table.getOtp()) {
			otpRepository.deleteById(table.getId());
			return ResponseEntity.ok("your otp  is sucessfully verified!!");
			
			

		}
					return ResponseEntity.ok("your entered  otp is incorrect please enter valid otp!!");

	}

	
//	UserResponse(Integer id, String name, String email, Gender gender, LocalDate dateOfBirth,
//			String mobileNumber, String profilePic, String bio, LocalDate createdAt, LocalDate updatedAt, Active active,
//			Role role) {

	public ResponseEntity<UserResponse> getUserDetalis() {
		// TODO Auto-generated method stub
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		Users user=userRepo.findByEmail(authentication.getName())
				  .orElseThrow(() -> new ResourceNotFoundException("user not found"));
		
		UserResponse res= new UserResponse(user.getId(), user.getName() ,user.getEmail(), user.getGender(), user.getDateOfBirth(),user.getMobileNumber(),user.getProfilePic(),user.getBio(),user.getCreatedAt(),user.getUpdatedAt(),user.getActive(),user.getRole());
		
		
		return ResponseEntity.ok(res);
	}
	
	
	
}
