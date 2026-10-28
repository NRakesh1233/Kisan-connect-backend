package Kisan.connect.app.Services;


import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import Kisan.connect.app.Exception.ResourceNotFoundException;
import Kisan.connect.app.Repository.AddressRepository;
import Kisan.connect.app.Repository.CartRepository;
import Kisan.connect.app.Repository.OrderItemRepository;
import Kisan.connect.app.Repository.OrdersRepository;
import Kisan.connect.app.Repository.ProductsRepository;
import Kisan.connect.app.Repository.ReviewRepository;
import Kisan.connect.app.Repository.UserRepo;
import Kisan.connect.app.Requests.AddAddressRequest;
import Kisan.connect.app.Requests.AddToCartRequest;
import Kisan.connect.app.Requests.OrderRequest;
import Kisan.connect.app.Requests.ReviewRequest;
import Kisan.connect.app.Response.AddressResponse;
import Kisan.connect.app.Response.CartResponse;
import Kisan.connect.app.Response.OrderItemResponse;
import Kisan.connect.app.Response.OrderResponse;
import Kisan.connect.app.Response.ReviewResponse;
import Kisan.connect.app.entities.Address;
import Kisan.connect.app.entities.Cart;
import Kisan.connect.app.entities.OrderItems;
import Kisan.connect.app.entities.OrderStatus;
import Kisan.connect.app.entities.Orders;
import Kisan.connect.app.entities.Products;
import Kisan.connect.app.entities.Review;
import Kisan.connect.app.entities.Users;

@Service
public class CustomerService {

	@Autowired
	private  UserRepo userRepo;
	@Autowired
	private ProductsRepository productsRepository;
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private OrdersRepository orderRepository;
	@Autowired
	private OrderItemRepository  orderItemRepository;
	@Autowired
	private ReviewRepository reviewRepository;
	
	public String addTCart(AddToCartRequest request) {
		// TODO Auto-generated method stub
		
		Cart cart=AddToCartRequest.getCartEntity(request);
		
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();

		
		Users user=userRepo.findByEmail(authentication.getName())
		.orElseThrow(()->new ResourceNotFoundException("user not found"));
		cart.setUser(user);
		
		Products product = productsRepository.findById(request.getProductId())
				.orElseThrow(()->new ResourceNotFoundException("product not found"));
		cart.setUser(user);
		
		cart.setProduct(product);
		
		cartRepository.save(cart);
		
		return "your item successfully added into cart!!";
	}

	public String addAddress(AddAddressRequest request) {
		// TODO Auto-generated method stub
		Address address=AddAddressRequest.getAddressEntity(request);
	 Authentication authentication=SecurityContextHolder.getContext().getAuthentication();

		Optional<Users> user=userRepo.findByEmail(authentication.getName());
		 address.setCustomer(user.get());
		 addressRepository.save(address);
		return "successfully address added!!";
	}

	public String addOrder(OrderRequest request) {
		
	Double totalPrice=0.0;	
	
	
	List<OrderItems> items = new ArrayList<>();
		Orders order =new Orders();
		
		order.setPaymentMethod(request.getPaymentMethod());
		
		 Authentication authentication=SecurityContextHolder.getContext().getAuthentication();

		 Users user=userRepo.findByEmail(authentication.getName())
					.orElseThrow(()->new ResourceNotFoundException("user not found"));
			
			order.setCustomer(user);
			
		Address address=addressRepository.findById(request.getAddressId())
					.orElseThrow(()->new ResourceNotFoundException("Address  not found"));
			order.setDeliveryAddress(address);
			
			if(user.getId()!=address.getCustomer().getId()) {
				throw new AccessDeniedException("you can order for your address only");
			}
			
			
			Users farmer=userRepo.findById(request.getFarmerId())
					.orElseThrow(()->new ResourceNotFoundException("user not found"));
			order.setFarmer(farmer);
			
		order.setOrderStatus(OrderStatus.PENDING);
	
		order.setPaymentStatus("success");
		order.setOrderDate(LocalDateTime.now());
		
		
		
		for(int i=0;i<request.getOrderItems().size(); i++) {
			
			Products product = productsRepository.findById(request.getOrderItems().get(i).getProductId())
					.orElseThrow(()->new ResourceNotFoundException("product not found"));
			
			if(farmer.getId()!=product.getFarmer().getId()) {
				throw new AccessDeniedException("you have to order  from same farmer only");
			}
			
			
			
			Double price=product.getPrice();
			
			Double quantity =request.getOrderItems().get(i).getQuantity();
			
			Double itemTotalPrice=price*quantity;
			totalPrice=totalPrice+itemTotalPrice;
		
			OrderItems orderItem=new OrderItems(product,order,quantity,price,itemTotalPrice);
			items.add(i, orderItem);
			
			
			
		}
		
		
		order.setTotalAmount(totalPrice);
		
		
		
		
		
		orderRepository.save(order);
		orderItemRepository.saveAll(items);
		
		// TODO Auto-generated method stub
		return "ordered successfully!!!";
	}

	public ResponseEntity<List<CartResponse>> getCartProducts() {
		// TODO Auto-generated method stub
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		List<Cart> cart=cartRepository.findByUser(userRepo.findByEmail(authentication.getName()).orElseThrow(()->new ResourceNotFoundException("user not found")));
		List<CartResponse> response=new ArrayList<>();
		for (int i = 0; i < cart.size(); i++) {
		
			Cart c=cart.get(i);
			Products p =c.getProduct();
			Users user=c.getUser();
//			
//			CartResponse(Integer cartId, Integer productId, String productName, Double productPrice, Double quantity,
//					Double totalPrice, String productImage, String category, String unit, Double availableStock,
//					Integer farmerId, String farmerName, String farmerProfilePic) {
//			
			
			CartResponse res=new CartResponse(c.getCartId(),
					        p.getProductId(),
					      p.getProductName(),
					       c.getCartPrice(),
					       c.getQuantity(),
					       c.getTotalPrice(),
					       p.getImage(),
					       p.getCategory(),
					       p.getUnit(),
					       p.getStock(),
					     p.getFarmer().getId(),
					      p.getFarmer().getName(),
					     p.getFarmer().getProfilePic()
					);
			
			
			response.add(i, res);
			
			
		}
		
		
		
		
		
		
		return  ResponseEntity.ok(response);
	}

	public ResponseEntity<String> updateCart(int id, AddToCartRequest request) {
		// TODO Auto-generated method stub	
		Cart cart=cartRepository.findById(id).get();
		cart.setQuantity(request.getQuantity());
		cart.setTotalPrice(request.getQuantity()*cart.getCartPrice());
		cartRepository.save(cart);
		
		return ResponseEntity.ok("cart updatted succefully");
	}

	public ResponseEntity<String> deleteCart(int id) {
		// TODO Auto-generated method stub
		
		cartRepository.deleteById(id);
		return ResponseEntity.ok("cart item successfully deleted!!");
	}
	

	public ResponseEntity<String> deleteCart() {
		
		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	        Users customer = userRepo.findByEmail(authentication.getName())
	                .orElseThrow(() -> new RuntimeException("Customer not found"));
	        cartRepository.deleteByUser(customer);
		// TODO Auto-generated method stub
		return ResponseEntity.ok(" allcart items successfully deleted!!");
	}

	public ResponseEntity<List<AddressResponse>> getAllAddresses() {
		// TODO Auto-generated method stub
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
List<Address>	address=addressRepository.findByCustomer(userRepo.findByEmail(authentication.getName()).get());

List<AddressResponse> response=new ArrayList<>();
//public AddressResponse(int addressId, String fullName, String mobileNumber, String houseNo, String street,
//		String village, String mandal, String district, String state, String pincode, String landmark,
//		String addressType, String fullAddress) {
//	

for (int i = 0; i < address.size(); i++) {
	Address a=address.get(i);
	
	String fullAddress ="HNo."+
	        a.getHouseNo() + ", " +
	        a.getStreet() + ", " +
	        a.getVillage() + "(Village) " +
	        a.getMandal() + "(mandal) " +
	        a.getDistrict() + "(district) " +
	        a.getState() + " - " +
	        a.getPincode();


	AddressResponse adr=new AddressResponse(
			
			a.getAddressId(),
			a.getFullName(),
			a.getMobileNumber(),
			a.getHouseNo(),
			a.getStreet(),
			a.getVillage(),
			a.getMandal(),
			a.getDistrict(),
			a.getState(),
			a.getPincode(),
			a.getLandmark(),
			a.getAddressType(),
			fullAddress
			
			);
	response.add(i, adr);
	
}
		return ResponseEntity.ok(response);
	}

	public ResponseEntity<AddressResponse> getAddress(int id) {
		// TODO Auto-generated method stub
		Address a=addressRepository.findById(id).get();
		
		String fullAddress ="HNo."+
		        a.getHouseNo() + ", " +
		        a.getStreet() + ", " +
		        a.getVillage() + "(Village) " +
		        a.getMandal() + "(mandal) " +
		        a.getDistrict() + "(district) " +
		        a.getState() + " - " +
		        a.getPincode();


		AddressResponse adr=new AddressResponse(
				
				a.getAddressId(),
				a.getFullName(),
				a.getMobileNumber(),
				a.getHouseNo(),
				a.getStreet(),
				a.getVillage(),
				a.getMandal(),
				a.getDistrict(),
				a.getState(),
				a.getPincode(),
				a.getLandmark(),
				a.getAddressType(),
				fullAddress
				
				);
		return ResponseEntity.ok(adr);
	}

	public ResponseEntity<String> updateAddress(int id, AddressResponse request) {
		// TODO Auto-generated method stub
		
		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	        Users customer = userRepo.findByEmail(authentication.getName())
	                .orElseThrow(() -> new RuntimeException("Customer not found"));

	        Address address = addressRepository.findById(request.getAddressId())
	                .orElseThrow(() -> new RuntimeException("Address not found"));

	        // Check whether this address belongs to the logged-in customer
	        if (!address.getCustomer().getId().equals(customer.getId())) {
	            throw new RuntimeException("You are not authorized to update this address.");
	        }

	        address.setFullName(request.getFullName());
	        address.setMobileNumber(request.getMobileNumber());
	        address.setHouseNo(request.getHouseNo());
	        address.setStreet(request.getStreet());
	        address.setVillage(request.getVillage());
	        address.setMandal(request.getMandal());
	        address.setDistrict(request.getDistrict());
	        address.setState(request.getState());
	        address.setPincode(request.getPincode());
	        address.setLandmark(request.getLandmark());
	        address.setAddressType(request.getAddressType());

	        addressRepository.save(address);

		return  ResponseEntity.ok("address updates successfully!!");
	}

	public ResponseEntity<String> deleteAddress(int id) {
		// TODO Auto-generated method stub
		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	        Users customer = userRepo.findByEmail(authentication.getName())
	                .orElseThrow(() -> new RuntimeException("Customer not found"));

	        Address address = addressRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Address not found"));

	        // Check whether this address belongs to the logged-in customer
	        if (!address.getCustomer().getId().equals(customer.getId())) {
	            throw new RuntimeException("You are not authorized to delete this address.");
	        }
	        
	        addressRepository.deleteById(id);
	        

		return ResponseEntity.ok("your address deleted succefully!!");
	}

	public ResponseEntity<List<OrderResponse>> getOrders() {
		// TODO Auto-generated method stub
		
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	        Users customer = userRepo.findByEmail(authentication.getName())
	                .orElseThrow(() -> new RuntimeException("Customer not found"));
	        
	        List<Orders>orders=orderRepository.findByCustomer(customer);
	        List<OrderResponse> response=new ArrayList<>();
	        
	        for (int i = 0; i < orders.size(); i++) {
	        	
	        	Orders o=orders.get(i);
	        	
//
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
	        			customer.getId(),
	        			customer.getName(),
	        			o.getFarmer().getId(),
	        			o.getFarmer().getName(),
	        			fullAddress,
	        			
	        			orderItemRespone
	        			
	        			);
	        	
				response.add(i, orderResponse);
	        	
			}

		
		
		
		return ResponseEntity.ok(response);
	}
	public ResponseEntity<String> addReview(ReviewRequest request) {
		// TODO Auto-generated method stub
		Review review=new Review();
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	        Users customer = userRepo.findByEmail(authentication.getName())
	                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
	        review.setCustomer(customer);
	        OrderItems item=orderItemRepository.findById(request.getOrderItemId())
	        		 .orElseThrow(() -> new ResourceNotFoundException("order item not found"));

	        review.setProduct(item.getProduct());
	        review.setOrderItem(item);
	        review.setRating(request.getRating());
	        review.setReviewDate(LocalDateTime.now());
	        review.setReview(request.getReview());
	        
		reviewRepository.save(review);
		return ResponseEntity.ok(" thank your for review!!");
		
	}
	public ResponseEntity<String> updateReview(ReviewRequest request) {
		// TODO Auto-generated method stub
	
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	        Users customer = userRepo.findByEmail(authentication.getName())
	                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
	    	
	      
	        OrderItems item=orderItemRepository.findById(request.getOrderItemId())
	        		 .orElseThrow(() -> new ResourceNotFoundException("order item not found"));
	        Review review=reviewRepository.findByCustomerAndOrderItem(customer,item)
	        		  .orElseThrow(() -> new ResourceNotFoundException("review not found"));
	        review.setCustomer(customer);
	        review.setProduct(item.getProduct());
	        review.setOrderItem(item);
	        review.setRating(request.getRating());
	        review.setReviewDate(LocalDateTime.now());
	        review.setReview(request.getReview());
	        
		reviewRepository.save(review);
		return ResponseEntity.ok(" your review is updated!!");
		
	}
	
	
	
	
	
	
	

	public ResponseEntity<OrderResponse> getOrderById(Integer id) {
		
		Orders o = orderRepository.findById(id).get();
		// TODO Auto-generated method stub
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		 Users customer = userRepo.findByEmail(authentication.getName())
	                .orElseThrow(() -> new RuntimeException("Customer not found"));
	        
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
		//Order
//			 public OrderItemResponse(int orderItemId, Integer productId, String productName, String image, Integer orderId,
//					Double quantity, Double price, Double totalPrice) {
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
			        			customer.getId(),
			        			customer.getName(),
			        			o.getFarmer().getId(),
			        			o.getFarmer().getName(),
			        			fullAddress,
			        			
			        			orderItemRespone
			        			
			        			);
			        	
		
		
		return  ResponseEntity.ok(orderResponse);
	}
	// Example logic in Spring Boot
	public ResponseEntity<ReviewResponse> getReviewByCustomerAndItem( Integer orderItemId) {
	    // Verify that this order item actually belongs to this customer, then fetch the review
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	        Users customer = userRepo.findByEmail(authentication.getName())
	                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
	        OrderItems item= orderItemRepository.findById(orderItemId).get();
	        
	    Review review = reviewRepository.findByCustomerAndOrderItem(customer,item)
	        .orElse(null);
	        
	    if (review == null) {
	        return ResponseEntity.ok(null); // No review posted yet
	    }
	    
	    // Map to ReviewResponse and return
	    return ResponseEntity.ok(convertToResponse(review));
	}

	private ReviewResponse convertToResponse(Review rew) {
		ReviewResponse res= new ReviewResponse();
			
			res.setId(rew.getReviewId());
			res.setCustomerDetails(rew.getCustomer());
			res.setComment(rew.getReview());
			res.setRating(rew.getRating());
			res.setDate(rew.getReviewDate());
			res.setProduct(rew.getProduct());
	return res;
		// TODO Auto-generated method stub
		
	}

	public ResponseEntity<List<ReviewResponse>> getReviewByCustomer() {
		
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	        Users customer = userRepo.findByEmail(authentication.getName())
	                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
	        
	       List<Review> reviews=reviewRepository.findByCustomer(customer);
	       List<ReviewResponse> response= new ArrayList<>();
	       
	       for(int i=0;i<reviews.size();i++) {
	    	   
	    	   response.add(convertToResponse(reviews.get(i)));
	       }
	       
	        
		// TODO Auto-generated method stub
		return  ResponseEntity.ok(response);
	}


}
