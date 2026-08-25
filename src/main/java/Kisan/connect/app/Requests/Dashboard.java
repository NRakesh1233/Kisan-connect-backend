package Kisan.connect.app.Requests;

import java.util.List;

import Kisan.connect.app.Response.OrderResponse;
import Kisan.connect.app.Response.ProductsResponse;

public class Dashboard {
	
	
	private List<ProductsResponse> products;
	private  List<OrderResponse> orders;
	private Double revenue;
	private Double rating;
	private String profilePic;
	private String farmerName;
	public String getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}
	public String getFarmerName() {
		return farmerName;
	}
	public void setFarmerName(String farmerName) {
		this.farmerName = farmerName;
	}
	public List<ProductsResponse> getProducts() {
		return products;
	}
	public void setProducts(List<ProductsResponse> products) {
		this.products = products;
	}
	public List<OrderResponse> getOrders() {
		return orders;
	}
	public void setOrders(List<OrderResponse> orders) {
		this.orders = orders;
	}
	public Double getRevenue() {
		return revenue;
	}
	public void setRevenue(Double double1) {
		this.revenue = double1;
	}
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
	
	

}
