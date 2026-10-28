package Kisan.connect.app.Controller;

import java.io.IOException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Kisan.connect.app.Requests.AddAddressRequest;
import Kisan.connect.app.Requests.AddProfileRequest;
import Kisan.connect.app.Requests.AddToCartRequest;
import Kisan.connect.app.Requests.OrderRequest;
import Kisan.connect.app.Requests.ReviewRequest;
import Kisan.connect.app.Response.AddressResponse;
import Kisan.connect.app.Response.CartResponse;
import Kisan.connect.app.Response.OrderResponse;
import Kisan.connect.app.Response.ReviewResponse;
import Kisan.connect.app.Response.UserResponse;
import Kisan.connect.app.Services.CustomerService;
import Kisan.connect.app.Services.FarmerService;
import Kisan.connect.app.Services.UserService;
import Kisan.connect.app.entities.OrderStatus;

@CrossOrigin(origins = "http://localhost:5173")

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;

	@Autowired
	private UserService userService;
	@Autowired
	private FarmerService farmerService;
	
	@PostMapping("/profile/update")
	public ResponseEntity<String>addProfile(@ModelAttribute AddProfileRequest request) throws IOException{
		System.out.println("controller page...profile pic");
		String response=userService.addProfilePic(request);
		
		return ResponseEntity.ok(response);
		
	}
	@GetMapping("/profile")
	public ResponseEntity<UserResponse> getUserDetails(){
		
		return userService.getUserDetalis();
		
	}

	
	
	
	@PostMapping("/cart/add")
	public ResponseEntity<String> addTOCart(@RequestBody AddToCartRequest request){
		
		
		
		String response= customerService.addTCart(request);
		
		return ResponseEntity.ok(response);
		
	}
	
	@GetMapping("/cart/products")
	public ResponseEntity<List<CartResponse>> getCartProducts(){
		
		return customerService.getCartProducts();
		
	}
	
	@PostMapping("/address/add")
	public ResponseEntity<String> addAddress(@RequestBody AddAddressRequest request){
		
		String response= customerService.addAddress(request);
		
		return ResponseEntity.ok(response);
		
	}
	
	@PostMapping("/order/add")
	public ResponseEntity<String> addOrder(@RequestBody OrderRequest request){
String response= customerService.addOrder(request);
		
		return ResponseEntity.ok(response);
	}
	
	
	@PutMapping("/cart/update/quantity/{id}")
	public ResponseEntity<String> updateCart(@PathVariable int id,@RequestBody AddToCartRequest request){
		
		
		
		
		
		return customerService.updateCart(id,request);
		
	}
	
	@DeleteMapping("/cart/delete/{id}")
	public ResponseEntity<String> deleteCart(@PathVariable int id){
		return customerService.deleteCart(id);
		
	}
	@DeleteMapping("/cart/delete/all")
	public ResponseEntity<String> deleteAllCart(){
		return customerService.deleteCart();
		
	}
	
	@GetMapping("/address/all")
	public ResponseEntity<List<AddressResponse>> getAllAddresses(){
		return customerService.getAllAddresses();
		
	}
	@GetMapping("/address/{id}")
	public ResponseEntity<AddressResponse> getAddress(@PathVariable int id){
		return customerService.getAddress(id);
		
	}
	@PutMapping("address/update/{id}")
	public ResponseEntity<String> updateAddress(@PathVariable int id,@RequestBody AddressResponse request){
		return customerService.updateAddress(id,request);
		
	}
	
	@DeleteMapping("/address/delete/{id}")
	public ResponseEntity<String> updateAddress(@PathVariable int id){
		return customerService.deleteAddress(id);
		
	}
	@GetMapping("/orders/all")
	public ResponseEntity<List<OrderResponse>> getOrders(){
		
		
		return customerService.getOrders();
		
	}
	
	@GetMapping("/orders/{id}")
	public ResponseEntity<OrderResponse> getOrderById(@PathVariable Integer id){
		
		
		return customerService.getOrderById(id);
		
	}

	@PutMapping("/order/status")
	public ResponseEntity<String> orderStatus(@RequestParam int id,@RequestParam OrderStatus status){
		if(!status.equals("CANCELLED")) {
			return farmerService.orderStatusCustomer(id,status);
		}
		return ResponseEntity.ok("customer can't change other than cancelled status!!");
		
	}
	
	
	//review..............
	@PostMapping("/order/product/review/add")
	public ResponseEntity<String> addReview(@RequestBody ReviewRequest request){
		
	
		
		
		
		return customerService.addReview(request);
		
	}
	@PutMapping("/order/product/review/update")
	public ResponseEntity<String> updateReview(@RequestBody ReviewRequest request){
		
	
		
		
		
		return customerService.updateReview(request);
		
	}
	
	@GetMapping("/reviews/orderItem/{id}")
	public ResponseEntity<ReviewResponse> fetchReviewsByOrderItemId(@PathVariable Integer id){
		
		
		
		return customerService.getReviewByCustomerAndItem(id);
		
	}
	@GetMapping("/reviews/all")
	public ResponseEntity<List<ReviewResponse>> fetchReviewsByCustomer(){
		
		
		
		return customerService.getReviewByCustomer();
		
	}
	
}
