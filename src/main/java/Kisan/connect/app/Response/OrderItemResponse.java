package Kisan.connect.app.Response;


public class OrderItemResponse {

	
	private int OrderItemId;
	
	
	 public OrderItemResponse(int orderItemId, Integer productId, String productName, String image, Integer orderId,
			Double quantity, Double price, Double totalPrice) {
	
		OrderItemId = orderItemId;
		this.productId = productId;
		this.productName = productName;
		this.image = image;
		this.orderId = orderId;
		this.quantity = quantity;
		this.price = price;
		this.totalPrice = totalPrice;
	}

	 public int getOrderItemId() {
		return OrderItemId;
	}

	public void setOrderItemId(int orderItemId) {
		OrderItemId = orderItemId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	 private Integer productId;

	   
	    private String productName;
	    private String image;

	private Integer orderId;
	
	private Double quantity;
	
	private Double price;
	
	private Double totalPrice;
}
