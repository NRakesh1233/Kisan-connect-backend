package Kisan.connect.app.Services;
import java.util.ArrayList;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import Kisan.connect.app.Exception.ResourceNotFoundException;
import Kisan.connect.app.Repository.AddressRepository;
import Kisan.connect.app.Repository.OrderItemRepository;
import Kisan.connect.app.Repository.OrdersRepository;
import Kisan.connect.app.Repository.ProductsRepository;
import Kisan.connect.app.Repository.ReviewRepository;
import Kisan.connect.app.Repository.UserRepo;
import Kisan.connect.app.Requests.Dashboard;
import Kisan.connect.app.Response.EarningResponse;
import Kisan.connect.app.Response.OrderItemResponse;
import Kisan.connect.app.Response.OrderResponse;
import Kisan.connect.app.Response.ProductsResponse;
import Kisan.connect.app.Response.ReviewResponse;
import Kisan.connect.app.entities.Address;
import Kisan.connect.app.entities.OrderItems;
import Kisan.connect.app.entities.OrderStatus;
import Kisan.connect.app.entities.Orders;
import Kisan.connect.app.entities.Products;
import Kisan.connect.app.entities.Review;
import Kisan.connect.app.entities.Users;

@Service
public class FarmerService {

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private OrdersRepository orderRepository;
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private ProductsService productService;
	@Autowired
private ReviewRepository reviewRepository;
	@Autowired
	private ProductsRepository productsRepository;
	
	
	public List<OrderResponse> getOrders() {
		// TODO Auto-generated method stub
		

		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	        Users farmer = userRepo.findByEmail(authentication.getName())
	                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
	        
	        List<Orders>orders=orderRepository.findByFarmer(farmer);
	        List<OrderResponse> response=new ArrayList<>();
	        
	        
	      // 
  for (int i = 0; i < orders.size(); i++) {
	        	
	        	Orders o=orders.get(i);
	        	 if(farmer.getId()!=o.getFarmer().getId()) {
	        		 throw new AccessDeniedException("farmer can get only his orders!!");
	        	 }
	        	
//if(
//	            public OrderResponse(Integer orderId, Double totalAmount, String orderStatus, String paymentMethod,
//	        			String paymentStatus, LocalDateTime orderTime, Integer customerId, String customerName, Integer farmerId,
//	        			String farmerName, AddressResponse address, List<OrderItemResponse> orderItems) {
//       	
	        	Address a=addressRepository.findById(o.getDeliveryAddress().getAddressId()).get();
	    		
	    		String fullAddress ="HNo."+
	    		        a.getHouseNo() + ", " +
	    		        a.getStreet() + ", " +
	    		        a.getVillage() + "(Village) " +
	    		        a.getMandal() + "(mandal) " +
	    		        a.getDistrict() + "(district) " +
	    		        a.getState() + " - " +
	    		        a.getPincode();
List<OrderItems> items=orderItemRepository.findByOrder(o);

List<OrderItemResponse> orderItemRespone=new ArrayList<>();
for (int j = 0; j < items.size(); j++) {
	
	OrderItems or=items.get(j);
	
	if(farmer.getId()!=or.getProduct().getFarmer().getId()) {
		 throw new AccessDeniedException("farmer can get only his orders!!");
	 }
//Order
//	 public OrderItemResponse(int orderItemId, Integer productId, String productName, String image, Integer orderId,
//			Double quantity, Double price, Double totalPrice) {
//	
	
	OrderItemResponse oir=new OrderItemResponse(
			or.getOrderItemId(),
			or.getProduct().getProductId(),
			or.getProduct().getProductName(),
			or.getProduct().getImage(),
			or.getOrder().getOrderId(),
			or.getQuantity(),
			or.getPrice(),
			or.getTotalPrice()
		
			);
	
	orderItemRespone.add(j, oir);
	
	
}



	        	
	        	
	        	OrderResponse orderResponse=new OrderResponse(
	        			o.getOrderId(),
	        			o.getTotalAmount(),
	        			o.getOrderStatus(),
	        			o.getPaymentMethod(),
	        			o.getPaymentStatus(),
	        			o.getOrderDate(),
	        			o.getCustomer().getId(),
	        			
	        			o.getCustomer().getName(),
	        			o.getFarmer().getId(),
	        			o.getFarmer().getName(),
	        			fullAddress,
	        			
	        			orderItemRespone
	        			
	        			);
	        	
				response.add(i, orderResponse);
	        	
			}

		
		
		
		return response;
	}

	public ResponseEntity<String> orderStatusByFarmer(int id, OrderStatus status) {
		// TODO Auto-generated method stub
		

		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	        Users farmer = userRepo.findByEmail(authentication.getName())
	                .orElseThrow(() -> new ResourceNotFoundException("farmer not found"));
	        
		Orders order=orderRepository.findById(id).get();
		order.setOrderStatus(status);
	
		if(farmer.getId()!=order.getFarmer().getId()) {
			throw new AccessDeniedException("your can change your order status only!!");
		}
		
		if(status.equals(OrderStatus.DELIVERED)) {
			List<OrderItems> items=orderItemRepository.findByOrder(order);
			for (int i = 0; i < items.size(); i++) {
				OrderItems item=items.get(i);
				Products p= item.getProduct();
				p.setStock(p.getStock()- item.getQuantity());
				item.setProduct(p);
				
			}
			
	
			
			
			
		
		}
			
		
		
		String response="your order status is "+order.getOrderStatus();
		orderRepository.save(order);
		return ResponseEntity.ok(response);
	}
	
	public ResponseEntity<String> orderStatusCustomer(int id, OrderStatus status) {
		// TODO Auto-generated method stub
		

		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	        Users farmer = userRepo.findByEmail(authentication.getName())
	                .orElseThrow(() -> new ResourceNotFoundException("farmer not found"));
	        
		Orders order=orderRepository.findById(id).get();
		order.setOrderStatus(status);
		
		if(farmer.getId()!=order.getCustomer().getId()) {
			throw new AccessDeniedException("your can change your order status only!!");
		}
		
		
		String response="your order status is "+order.getOrderStatus();
		orderRepository.save(order);
		return ResponseEntity.ok(response);
	}

	public ResponseEntity<Dashboard> getDashBoard() {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		  Users farmer = userRepo.findByEmail(authentication.getName())
	                .orElseThrow(() -> new ResourceNotFoundException("farmer not found"));
	        
		Dashboard das=new Dashboard();
		das.setProducts( productService.getFarmerProducts());
		das.setOrders(this.getOrders());
		
		das.setRevenue(orderRepository.findRevenue(farmer));
		Double sum=0.0;
		long count=0;
		for (int i = 0; i < das.getProducts().size(); i++) {
			ProductsResponse p= das.getProducts().get(i);
			Double review=reviewRepository.getAverageRatingByProductId(p.getProductId());
	
			if(review!=null) {
				sum=sum+review;
				count++;
			}
				
		}
		Double rating=sum/count;
		das.setRating(rating);
		
	
		das.setProfilePic(farmer.getProfilePic());
		das.setFarmerName(farmer.getName());
		
		
		
		
		
		// TODO Auto-generated method stub
		return ResponseEntity.ok(das);
	}

	public List<OrderResponse> getOrdersByStatus(OrderStatus status) {
		
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	        Users farmer = userRepo.findByEmail(authentication.getName())
	                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
	        
	        List<Orders>orders=orderRepository.findByFarmerAndOrderStatus(farmer,status);
	        List<OrderResponse> response=new ArrayList<>();
	        
	        
	      // 
for (int i = 0; i < orders.size(); i++) {
	        	
	        	Orders o=orders.get(i);
	        	 if(farmer.getId()!=o.getFarmer().getId()) {
	        		 throw new AccessDeniedException("farmer can get only his orders!!");
	        	 }
	        	
//if(
//	            public OrderResponse(Integer orderId, Double totalAmount, String orderStatus, String paymentMethod,
//	        			String paymentStatus, LocalDateTime orderTime, Integer customerId, String customerName, Integer farmerId,
//	        			String farmerName, AddressResponse address, List<OrderItemResponse> orderItems) {
//    	
	        	Address a=addressRepository.findById(o.getDeliveryAddress().getAddressId()).get();
	    		
	    		String fullAddress ="HNo."+
	    		        a.getHouseNo() + ", " +
	    		        a.getStreet() + ", " +
	    		        a.getVillage() + "(Village) " +
	    		        a.getMandal() + "(mandal) " +
	    		        a.getDistrict() + "(district) " +
	    		        a.getState() + " - " +
	    		        a.getPincode();
List<OrderItems> items=orderItemRepository.findByOrder(o);

List<OrderItemResponse> orderItemRespone=new ArrayList<>();
for (int j = 0; j < items.size(); j++) {
	
	OrderItems or=items.get(j);
	
	if(farmer.getId()!=or.getProduct().getFarmer().getId()) {
		 throw new AccessDeniedException("farmer can get only his orders!!");
	 }
//Order
//	 public OrderItemResponse(int orderItemId, Integer productId, String productName, String image, Integer orderId,
//			Double quantity, Double price, Double totalPrice) {
//	
	
	OrderItemResponse oir=new OrderItemResponse(
			or.getOrderItemId(),
			or.getProduct().getProductId(),
			or.getProduct().getProductName(),
			or.getProduct().getImage(),
			or.getOrder().getOrderId(),
			or.getQuantity(),
			or.getPrice(),
			or.getTotalPrice()
		
			);
	
	orderItemRespone.add(j, oir);
	
	
}
   	
	        	OrderResponse orderResponse=new OrderResponse(
	        			o.getOrderId(),
	        			o.getTotalAmount(),
	        			o.getOrderStatus(),
	        			o.getPaymentMethod(),
	        			o.getPaymentStatus(),
	        			o.getOrderDate(),
	        			o.getCustomer().getId(),
	        			
	        			o.getCustomer().getName(),
	        			o.getFarmer().getId(),
	        			o.getFarmer().getName(),
	        			fullAddress,
	        			
	        			orderItemRespone
	        			
	        			);
	        	
				response.add(i, orderResponse);
	        	
			}

		
		
		
		return response;
		
		
		
		
	}

	public List<ReviewResponse> getReviewOfFarmer() {
		// TODO Auto-generated method stub
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	        Users farmer = userRepo.findByEmail(authentication.getName())
	                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
	        
	       List<ReviewResponse> response= new ArrayList<>();
		
		List<Review> reviews= reviewRepository.findByFarmer(farmer);
		
		for (int i = 0; i < reviews.size(); i++) {
			
			Review rew =reviews.get(i);
			ReviewResponse res= new ReviewResponse();
			
			res.setId(rew.getReviewId());
			res.setCustomer(rew.getCustomer().getName());
			res.setComment(rew.getReview());
			res.setRating(rew.getRating());
			res.setDate(rew.getReviewDate());
			    
			
			response.add(res);
			
		}
		
		return response;
	}
	
	public List<ReviewResponse> getReviewOfProduct(int pnum) {
		// TODO Auto-generated method stub
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	        Users farmer = userRepo.findByEmail(authentication.getName())
	                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
	        
	       List<ReviewResponse> response= new ArrayList<>();
		Products product =productsRepository.findById(pnum)
				.orElseThrow(()->new ResourceNotFoundException("user not found"));
		List<Review> reviews= reviewRepository.findByProduct(product);
		
		for (int i = 0; i < reviews.size(); i++) {
			
			Review rew =reviews.get(i);
			ReviewResponse res= new ReviewResponse();
			
			res.setId(rew.getReviewId());
			res.setCustomerDetails(rew.getCustomer());
			res.setComment(rew.getReview());
			res.setRating(rew.getRating());
			res.setDate(rew.getReviewDate());
			    
			
			response.add(res);
			
		}
		
		return response;
	}


	public EarningResponse getEarning(int pnum) {
		// TODO Auto-generated method stub
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		 Users farmer = userRepo.findByEmail(authentication.getName())
	                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
		 
		 ResponseEntity<ProductsResponse> product = productService.getProductById(pnum);
		 EarningResponse  response=new  EarningResponse();
		 response.setProduct(product.getBody());
		 
		 response.setReviews(getReviewOfProduct(pnum));
		 
		 List<Orders> ods=orderItemRepository.findByProductId(pnum);
		 response.setOrdersCount(ods.size());
		response.setRating(reviewRepository.findByProductRating(pnum));
		
		return response;
	}
	
	


	
	
	
	
}
