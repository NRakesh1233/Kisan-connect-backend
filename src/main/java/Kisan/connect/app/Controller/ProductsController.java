package Kisan.connect.app.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Kisan.connect.app.Response.ProductsResponse;
import Kisan.connect.app.Services.ProductsService;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/public")
public class ProductsController {
	
	@Autowired
	private ProductsService productsService;
	
	@GetMapping("/products")
	public ResponseEntity<List<ProductsResponse>> getAllProducts(){
		
		return  productsService.getProducts();
		
	}
	@GetMapping("/products/{id}")
	public ResponseEntity<ProductsResponse> getProductById(@PathVariable int id){
		
		return  productsService.getProductById(id);
	
	}
	@GetMapping("/products/category/{category}")
	public ResponseEntity<List<ProductsResponse>> getProductsByCategory(@PathVariable String category){
		
		return  productsService.getProductByCategory(category);
		
	}
	@GetMapping("/products/top4")
public ResponseEntity<List<ProductsResponse>> getTopProducts(){
		
		return  productsService.getTopProducts();
		
	}
	@GetMapping("products/farmer/{id}")
	
public ResponseEntity<List<ProductsResponse>> getProductByFarmer(@PathVariable int id){
		
		return  productsService.getProductByFarmer(id);
	
	}
	
	
	
//	@PutMapping("products/update/{pid}")
//	public ResponseEntity<String> updateProduct(@PathVariable Integer pid){
//		
//		productsService.updateProduct
//		
//		return null;
//	}
	
	
	
	
	
	
	
	
	
	
}

