package Kisan.connect.app.Requests;

import Kisan.connect.app.entities.OrderStatus;

public class OrderStatusRequest {

	
	private OrderStatus status;

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}
}
