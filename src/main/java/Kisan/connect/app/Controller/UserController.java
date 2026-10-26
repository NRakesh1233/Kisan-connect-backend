package Kisan.connect.app.Controller;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Kisan.connect.app.Requests.AddProfileRequest;
import Kisan.connect.app.Requests.TokenRefreshRequest;
import Kisan.connect.app.Requests.UserLoginRequest;
import Kisan.connect.app.Requests.UserRegisterRequest;
import Kisan.connect.app.Response.AuthenticationResponse;
import Kisan.connect.app.Response.UserResponse;
import Kisan.connect.app.Services.UserService;
@CrossOrigin(origins = "http://localhost:5173")

@RestController
@RequestMapping("public/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping(path="/register")
	public String registerUser(@RequestBody UserRegisterRequest user){
		
		String response=userService.register(user);
		
		return response ;
	}
	
	
	@PostMapping(path="/login")
	public ResponseEntity<AuthenticationResponse>login(@RequestBody UserLoginRequest user){
		
		
		AuthenticationResponse response= userService.login(user);
		
		
		
		
		return ResponseEntity.ok(response);
		
	}
	@PostMapping("/public/auth/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest request) {
	    AuthenticationResponse response = userService.generateNewAccessToken(request.getRefreshToken());
	    return ResponseEntity.ok(response);
	}
	
	@PostMapping("mobile/generate")
	public ResponseEntity<String> generateOTP(@RequestParam String mobile){
		
		
		
		return userService.generateOtp(mobile);
		
	}
	@PostMapping("mobile/verify")
	public ResponseEntity<String> verifyOTP(@RequestParam int otp,@RequestParam String mobile){
		
		
		
		return userService.verifyOtp(otp,mobile);
		
	}
	
	
	

	@GetMapping("farmers/{id}")
	
	public ResponseEntity<UserResponse> getFarmerById(@PathVariable Integer id){
		
		
		return userService.getUserDetalis(id);
		
	}
	
	@GetMapping("farmers/all")
public ResponseEntity<List<UserResponse>> getAllFarmers(){
		
		
		return userService.getAllFarmerDetalis();
		
	}
	
	
	
	


	
	
}
