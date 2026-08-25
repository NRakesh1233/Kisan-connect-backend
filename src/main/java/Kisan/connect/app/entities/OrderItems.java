package Kisan.connect.app.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class OrderItems {

	
	public OrderItems() {
	
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int OrderItemId;
	
	
	@ManyToOne
	private Products product;
	

	@ManyToOne
	private Orders order;
	
	private Double quantity;
	
	private Double price;
	
	private Double totalPrice;
	
	
	
	public OrderItems( Products product, Orders order, Double quantity, Double price, Double totalPrice) {
		
		
		this.product = product;
		this.order = order;
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

	public Products getProduct() {
		return product;
	}

	public void setProduct(Products product) {
		this.product = product;
	}

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
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

	
}
