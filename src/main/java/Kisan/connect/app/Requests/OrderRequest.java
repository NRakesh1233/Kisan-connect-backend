package Kisan.connect.app.Requests;

import java.util.List;

import Kisan.connect.app.entities.OrderItems;

public class OrderRequest {
	
	private Integer addressId;
	private Integer farmerId;
	private String paymentMethod;
	
	
	private List<OrderItemRequest> orderItems;


	public List<OrderItemRequest> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemRequest> orderItems) {
		this.orderItems = orderItems;
	}

	public Integer getAddressId() {
		return addressId;
	}

	public Integer getFarmerId() {
		return farmerId;
	}

	public void setFarmerId(Integer farmerId) {
		this.farmerId = farmerId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

}
