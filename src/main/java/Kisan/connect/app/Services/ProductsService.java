package Kisan.connect.app.Services;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.springbootProject.jpaOperations.UsersRepository;

import Kisan.connect.app.Exception.ResourceNotFoundException;
import Kisan.connect.app.Repository.ProductsRepository;
import Kisan.connect.app.Repository.ReviewRepository;
import Kisan.connect.app.Repository.UserRepo;
import Kisan.connect.app.Requests.AddProductRequest;
import Kisan.connect.app.Response.ProductsResponse;
import Kisan.connect.app.entities.Products;
import Kisan.connect.app.entities.Review;
import Kisan.connect.app.entities.Users;

@Service
public class ProductsService {
	
	
	
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ProductsRepository productsRepository;
	@Autowired
	private ReviewRepository reviewRepository;
	
	private final String productImagePath="uploads/products" ;

	public String addProduct(AddProductRequest request) throws IOException {
		
		
		System.out.println("step1 servic p");
		
		
		// TODO Auto-generated method stub
		
		System.out.println("Product Name : " + request.getProductName());
		System.out.println("Category : " + request.getCategory());
		System.out.println("Price : " + request.getPrice());
		
		Products products=AddProductRequest.getProductEntity(request);
		
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		System.out.println("...........................step1 servic p"+authentication.getName());                                                

	Users user= userRepo.findByEmail(authentication.getName())
				  .orElseThrow(() -> new ResourceNotFoundException("user not found"));
		
		
                                                                                                                                                                                                  
products.setFarmer(user);


MultipartFile image=request.getImage();
String fileName= UUID.randomUUID()+"_"+image.getOriginalFilename();


Path path=Paths.get(productImagePath,fileName);

		Files.copy(image.getInputStream(), path);
		
		products.setImage(fileName);
		
		products.setStatus("active");
		
		productsRepository.save(products);

		return " product add sussfully";
	}
	
	
	
	
	public List<ProductsResponse> loadProducts(List<Products> products){
		List<ProductsResponse> allProducts=new ArrayList<>();
	for(int i=0;i<products.size();i++) {
			
			Products p=products.get(i);
			Double rating=reviewRepository.getAverageRatingByProductId(p.getProductId());
			Integer count =reviewRepository.getReviewCount(p.getProductId());
			if(rating==null) {
				rating=0.0;	
			}
			ProductsResponse productResponse=new ProductsResponse(p.getProductId(),
					                                 p.getProductName(),
					                                 p.getPrice(),
					                                 p.getStock(),
					                                 p.getUnit(),
					                                 p.getCategory(),
					                                 p.getDescription(),
					                                 p.getImage(),
					                                 p.getHarvestDate(),
					                                 p.getExpiryDate(),
					                                 p.getFarmer().getId(),
					                                 p.getFarmer().getName(),
					                                 p.getFarmer().getProfilePic(), rating,p.getImage(),p.getTotalStock());
			productResponse.setReviewCount(count);
			
			allProducts.add(i, productResponse);
			
		}
		return allProducts;	
	}
	

	public ResponseEntity<List<ProductsResponse>> getProducts() {
		// TODO Auto-generated method stub
		List<Products> products=productsRepository.findAll();
		
	
		return ResponseEntity.ok(loadProducts(products));
	}
	
	
	
	

	public ResponseEntity<ProductsResponse> getProductById(int id) {
		// TODO Auto-generated method stub
		
		Products p=productsRepository.findById(id).get();
		
		Double rating=reviewRepository.getAverageRatingByProductId(p.getProductId());
		Integer count =reviewRepository.getReviewCount(p.getProductId());
		if(rating==null) {
			rating=0.0;
			}
		ProductsResponse productResponse=new ProductsResponse(p.getProductId(),
                p.getProductName(),
                p.getPrice(),
                p.getStock(),
                p.getUnit(),
                p.getCategory(),
                p.getDescription(),
                p.getImage(),
                p.getHarvestDate(),
                p.getExpiryDate(),
                p.getFarmer().getId(),
                p.getFarmer().getName(),
                p.getFarmer().getProfilePic(),
                rating,p.getImage(),p.getTotalStock());
		productResponse.setReviewCount(count);
		return ResponseEntity.ok(productResponse);
	}

	public ResponseEntity<List<ProductsResponse>> getProductByCategory(String category) {
		// TODO Auto-generated method stub
		
		List<ProductsResponse> allProducts=new ArrayList<>();
		List<Products> products=productsRepository.findByCategory(category);
		
	
		for(int i=0;i<products.size();i++) {
			
			Products p=products.get(i);
			Double rating=reviewRepository.getAverageRatingByProductId(p.getProductId());
			Integer count =reviewRepository.getReviewCount(p.getProductId());
			if(rating==null) {
				rating=0.0;
			}
			ProductsResponse productResponse=new ProductsResponse(p.getProductId(),
					                                 p.getProductName(),
					                                 p.getPrice(),
					                                 p.getStock(),
					                                 p.getUnit(),
					                                 p.getCategory(),
					                                 p.getDescription(),
					                                 p.getImage(),
					                                 p.getHarvestDate(),
					                                 p.getExpiryDate(),
					                                 p.getFarmer().getId(),
					                                 p.getFarmer().getName(),
					                                 p.getFarmer().getProfilePic()
					                                 ,rating,p.getImage(),p.getTotalStock());
			productResponse.setReviewCount(count);
			
			allProducts.add(i, productResponse);
			
		}
		
		
		return ResponseEntity.ok(allProducts);
	}

	public List<ProductsResponse> getFarmerProducts() {
		// TODO Auto-generated method stub
		
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		List<Products> products=productsRepository.findByFarmer(userRepo.findByEmail(authentication.getName()) .orElseThrow(() -> new ResourceNotFoundException("Customer not found")));
		
		
		
		return loadProducts(products);
	}

	public ResponseEntity<String> updateProduct(int id, AddProductRequest request) throws IOException {
		// TODO Auto-generated method stub
		
		Products product=productsRepository.findById(id).get();
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();

		Users user =userRepo.findByEmail(authentication.getName()) 
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
		
		if((product.getFarmer().getId()!=user.getId())) {
			 throw new AccessDeniedException("You can update only your products");
		}
		
		product.setProductName(request.getProductName());
		product.setPrice(request.getPrice());
		product.setStock(request.getStock());
		product.setUnit(request.getUnit());
		product.setCategory(request.getCategory().toLowerCase());
		product.setDescription(request.getDescription());
		
		if (request.getImage() != null &&
			    !request.getImage().isEmpty()) {

			    MultipartFile image = request.getImage();

			    String fileName =
			            UUID.randomUUID() + "_" + image.getOriginalFilename();

			    Path path = Paths.get(productImagePath, fileName);

			    Files.copy(image.getInputStream(), path);

			    product.setImage(fileName);
			}
		productsRepository.save(product);
		
		return ResponseEntity.ok("product updated successfully!! ");
	}

	public ResponseEntity<String> deleteProduct(int id) {
		// TODO Auto-generated method stub

		Products product=productsRepository.findById(id).get();
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();

		Users user =userRepo.findByEmail(authentication.getName()) 
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
		
		if((product.getFarmer().getId()!=user.getId())) {
			 throw new AccessDeniedException("You can delete only your products");
		}
		
		productsRepository.deleteById(id);
		
		return ResponseEntity.ok("your product deleted successfully!!") ;
	}

	public ResponseEntity<List<ProductsResponse>> getTopProducts() {
		
		
		// TODO Auto-generated method stub
		List<Products> products=reviewRepository.getHighRatingProducts(PageRequest.of(0, 4));

				return  ResponseEntity.ok(loadProducts(products));
	}

	public ResponseEntity<List<ProductsResponse>> getProductByFarmer(int id) {
		// TODO Auto-generated method stub
	Users user= userRepo.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
		System.out.println("user"+user);
	List<Products> products=productsRepository.findByFarmer(user);
	System.out.println("products"+products);
		System.out.println(" farmer id for products"+ id);
		List<ProductsResponse> res=loadProducts(products);
		System.out.println(res);
	return  ResponseEntity.ok(res);
	}
	
	

}
