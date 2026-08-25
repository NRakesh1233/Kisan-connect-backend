package Kisan.connect.app.Response;


import java.time.LocalDateTime;

public class ReviewResponse {
	
	 
	private Integer id;
	private String customer;
	private Integer rating;
	private String comment;
	private LocalDateTime date;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public Integer getRating() {
		return rating;
	}
	public void setRating(Integer integer) {
		this.rating = integer;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime localDateTime) {
		this.date = localDateTime;
	}
	

}
