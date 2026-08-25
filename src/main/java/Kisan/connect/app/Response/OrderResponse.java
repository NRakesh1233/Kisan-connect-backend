package Kisan.connect.app.Response;

import java.time.LocalDateTime;
import java.util.List;

import Kisan.connect.app.entities.OrderStatus;

public class OrderResponse {
	private Integer orderId;

    private Double totalAmount;

    private OrderStatus orderStatus;

    private String paymentMethod;

    private String paymentStatus;

    private LocalDateTime orderTime;

    public OrderResponse(Integer orderId, Double totalAmount, OrderStatus orderStatus, String paymentMethod,
			String paymentStatus, LocalDateTime orderTime, Integer customerId, String customerName, Integer farmerId,
			String farmerName, String address, List<OrderItemResponse> orderItems) {

		this.orderId = orderId;
		this.totalAmount = totalAmount;
		this.orderStatus = orderStatus;
		this.paymentMethod = paymentMethod;
		this.paymentStatus = paymentStatus;
		this.orderTime = orderTime;
		this.customerId = customerId;
		this.customerName = customerName;
		this.farmerId = farmerId;
		this.farmerName = farmerName;
		this.address = address;
		this.orderItems = orderItems;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public LocalDateTime getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(LocalDateTime orderTime) {
		this.orderTime = orderTime;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Integer getFarmerId() {
		return farmerId;
	}

	public void setFarmerId(Integer farmerId) {
		this.farmerId = farmerId;
	}

	public String getFarmerName() {
		return farmerName;
	}

	public void setFarmerName(String farmerName) {
		this.farmerName = farmerName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<OrderItemResponse> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemResponse> orderItems) {
		this.orderItems = orderItems;
	}

	private Integer customerId;
    private String customerName;

    private Integer farmerId;
    private String farmerName;

    private String address;

    private List<OrderItemResponse> orderItems;

}
