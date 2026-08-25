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

import Kisan.connect.app.Requests.AddProductRequest;
import Kisan.connect.app.Requests.AddProfileRequest;
import Kisan.connect.app.Requests.Dashboard;
import Kisan.connect.app.Response.OrderResponse;
import Kisan.connect.app.Response.ProductsResponse;
import Kisan.connect.app.Response.ReviewResponse;
import Kisan.connect.app.Response.UserResponse;
import Kisan.connect.app.Services.FarmerService;
import Kisan.connect.app.Services.ProductsService;
import Kisan.connect.app.Services.UserService;
import Kisan.connect.app.entities.OrderStatus;
@CrossOrigin(origins = "http://localhost:5173")

@RestController
@RequestMapping("/farmer")
public class FarmerController {
	
	@Autowired
	private ProductsService productsService;
	@Autowired
	private FarmerService farmerService;

	@Autowired
	private UserService userService;
	
	
	
	
	@PostMapping("/profile/update")
	public ResponseEntity<String>addProfile(@ModelAttribute AddProfileRequest request) throws IOException{
		System.out.println("controller page...profile pic");
		String response=userService.addProfilePic(request);
		
		return ResponseEntity.ok(response);
		
	}

	
	@PostMapping("/products/add")
	
	public ResponseEntity<String> addProduct(@ModelAttribute AddProductRequest request ) throws IOException{
		
		// String response="successfully-product added";
		 String response=productsService.addProduct(request);
		System.out.println("product controller");
		
		return ResponseEntity.ok(response);
	}




	@GetMapping("/products")
	public ResponseEntity<List<ProductsResponse>> getProducts(){
		
		return  ResponseEntity.ok(productsService.getFarmerProducts());
		
	}
	
	@PutMapping("/products/update/{id}")
	public ResponseEntity<String> updateProduct(@PathVariable int id,@ModelAttribute AddProductRequest request) throws IOException{
		
		
		return productsService.updateProduct(id,request);
		
	}
	
	@DeleteMapping("/products/delete/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable int id){
		
		
		return productsService.deleteProduct(id);
		
	}
	@GetMapping("/Orders/all")
	public  ResponseEntity<List<OrderResponse>> getOrders(){
		return ResponseEntity.ok( farmerService.getOrders());
		
	}
	@GetMapping("/Orders/bystatus/{status}")
	public  ResponseEntity<List<OrderResponse>> getOrdersByStatus( @PathVariable OrderStatus status){
		return ResponseEntity.ok( farmerService.getOrdersByStatus(status));
		
	}
	
	
	@PutMapping("/order/status")
	public ResponseEntity<String> orderStatus(@RequestParam int id,@RequestParam OrderStatus status){
		
		
		return farmerService.orderStatusByFarmer(id,status);
	}
	
	
	@GetMapping("/dashboard")
	public  ResponseEntity<Dashboard>  getDashboard(){
		
		return farmerService.getDashBoard();
	}
	
	@GetMapping("/products/reviews")
	public ResponseEntity<List<ReviewResponse>> getReviewsOfFarmer(){
		
		
		
		return ResponseEntity.ok(farmerService.getReviewOfFarmer());
		
	}
	
	@GetMapping("/details")
	public ResponseEntity<UserResponse> getUserDetalis(){
		
		return userService.getUserDetalis();
	}
	
	
}