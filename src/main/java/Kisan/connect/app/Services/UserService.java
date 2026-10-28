package Kisan.connect.app.Services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
import Kisan.connect.app.Config.CustomUserDetailsService;
import Kisan.connect.app.Config.JwtUtills;
import Kisan.connect.app.Exception.ResourceNotFoundException;
import Kisan.connect.app.Repository.AddressRepository;
import Kisan.connect.app.Repository.OtpRepository;
import Kisan.connect.app.Repository.ReviewRepository;
import Kisan.connect.app.Repository.UserRepo;
import Kisan.connect.app.Requests.AddProfileRequest;
import Kisan.connect.app.Requests.UserLoginRequest;
import Kisan.connect.app.Requests.UserRegisterRequest;
import Kisan.connect.app.Response.AuthenticationResponse;
import Kisan.connect.app.Response.UserResponse;
import Kisan.connect.app.entities.Active;
import Kisan.connect.app.entities.Address;
import Kisan.connect.app.entities.OTP;
import Kisan.connect.app.entities.RefreshToken;
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
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private RefreshTokenService refreshTokenService;
	
	public String register(UserRegisterRequest request) {
		if(userRepo.findByEmail(request.getEmail()).isPresent()) {
			throw new AccessDeniedException("this email is already have account");
		}
		
		Users user = new Users();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
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
	
		Address ads = new Address(request.getName(), request.getMobileNumber(), request.getVillage(), request.getDistrict(), request.getState(), request.getPincode(), user, "HOME");

		userRepo.save(user);
		addressRepository.save(ads);

		return "Registration Successful";
	}
	
	public AuthenticationResponse login(UserLoginRequest request) {
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
		
		CustomUserDetailsService cus = new CustomUserDetailsService(userRepo);
		UserDetails user = cus.loadUserByUsername(request.getEmailId());
		
		// Generate Access & Refresh Tokens
		String accessToken = jwtUtills.generateToken(user);
		RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUsername());

		System.out.println("Step 3 token: " + accessToken);
		
		return new AuthenticationResponse(
				accessToken,
				refreshToken.getToken(),
				user.getUsername(),
				user.getAuthorities()
					.iterator()
					.next()
					.getAuthority(),
				"user logged successfully"
		);
	}

	public AuthenticationResponse generateNewAccessToken(String requestRefreshToken) {
		return refreshTokenService.findByToken(requestRefreshToken)
				.map(refreshTokenService::verifyExpiration)
				.map(RefreshToken::getUser)
				.map(users -> {
					UserDetails userDetails = new CustomUserDetails(users);
					String accessToken = jwtUtills.generateToken(userDetails);
					System.out.println("new access token"+accessToken);
					return new AuthenticationResponse(
							accessToken,
							requestRefreshToken,
							users.getEmail(),
							users.getRole().name(),
							"Access token refreshed successfully"
					);
				})
				.orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
	}

	public String addProfilePic(AddProfileRequest request) throws IOException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Users user = userRepo.findByEmail(authentication.getName())
				.orElseThrow(() -> new ResourceNotFoundException("user not found"));
		String name = UUID.randomUUID() + "_" + request.getProfilePic().getOriginalFilename();
		
		Path path = Paths.get("uploads/profiles", name);
		Files.copy(request.getProfilePic().getInputStream(), path);
		
		user.setProfilePic(name);
		userRepo.save(user);
		return "profile image added successfully!!";
	}

	public ResponseEntity<String> generateOtp(String mobile) {
		SecureRandom random = new SecureRandom();
		int otp = 100000 + random.nextInt(900000);
		
		System.out.println("your otp is " + otp);

		LocalDateTime exp = LocalDateTime.now().plusMinutes(2);
		OTP o = new OTP(mobile, otp, exp, false);
		
		otpRepository.save(o);
		return ResponseEntity.ok("otp sent successfully");
	}

	public ResponseEntity<String> verifyOtp(int otp, String mobile) {
		OTP table = otpRepository.findByMobileNumber(mobile);
		if (table == null) {
			return ResponseEntity.ok("OTP request not found!");
		}
		if (LocalDateTime.now().isAfter(table.getExpiryTime())) {
			otpRepository.deleteById(table.getId());
			return ResponseEntity.ok("your otp is expired please send request for new otp");
		}
		if (otp == table.getOtp()) {
			otpRepository.deleteById(table.getId());
			return ResponseEntity.ok("your otp is successfully verified!!");
		}
		return ResponseEntity.ok("your entered otp is incorrect please enter valid otp!!");
	}

	public ResponseEntity<UserResponse> getUserDetalis() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Users user = userRepo.findByEmail(authentication.getName())
				.orElseThrow(() -> new ResourceNotFoundException("user not found"));
		
		UserResponse res = new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getGender(), user.getDateOfBirth(), user.getMobileNumber(), user.getProfilePic(), user.getBio(), user.getCreatedAt(), user.getUpdatedAt(), user.getActive(), user.getRole());
		
		return ResponseEntity.ok(res);
	}
	
	public ResponseEntity<UserResponse> getUserDetalis(Integer id) {
		Users user = userRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("user not found"));
		
		UserResponse res = new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getGender(), user.getDateOfBirth(), user.getMobileNumber(), user.getProfilePic(), user.getBio(), user.getCreatedAt(), user.getUpdatedAt(), user.getActive(), user.getRole());
		
		return ResponseEntity.ok(res);
	}

	public ResponseEntity<List<UserResponse>> getAllFarmerDetalis() {
		List<Users> users = userRepo.findAll();
		List<UserResponse> resp = new ArrayList<>();
		
		for (int i = 0; i < users.size(); i++) {
			Users user = users.get(i);
			UserResponse res = new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getGender(), user.getDateOfBirth(),
					user.getMobileNumber(), user.getProfilePic(), user.getBio(), user.getCreatedAt(), user.getUpdatedAt(), user.getActive(), user.getRole());
			res.setRating(reviewRepository.findAverageRatingByFarmer(user));
			resp.add(res);
		}

		return ResponseEntity.ok(resp);
	}
}