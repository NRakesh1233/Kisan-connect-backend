package Kisan.connect.app.Response;

import java.util.List;

public class EarningResponse {
	
	
	
private ProductsResponse product;
private int ordersCount;
private List<ReviewResponse> reviews;

private double rating;

public double getRating() {
	return rating;
}
public void setRating(double rating) {
	this.rating = rating;
}
public ProductsResponse getProduct() {
	return product;
}
public void setProduct(ProductsResponse product) {
	this.product = product;
}
public int getOrdersCount() {
	return ordersCount;
}
public void setOrdersCount(int ordersCount) {
	this.ordersCount = ordersCount;
}
public List<ReviewResponse> getReviews() {
	return reviews;
}
public void setReviews(List<ReviewResponse> reviews) {
	this.reviews = reviews;
}


}
