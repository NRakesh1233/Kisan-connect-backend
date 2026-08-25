package Kisan.connect.app.Requests;



public class ReviewRequest {
	
	private Integer OrderItemId;
	private int rating;
	private String review;
	public Integer getOrderItemId() {
		return OrderItemId;
	}
	public void setOrderItemId(Integer orderItemId) {
		OrderItemId = orderItemId;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	

}
