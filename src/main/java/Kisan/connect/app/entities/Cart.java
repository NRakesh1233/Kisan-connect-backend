package Kisan.connect.app.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Cart {

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer cartId;
	
	
	@ManyToOne
	@JoinColumn(name="productId")
	private Products product;
	
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private Users user;
	
	private Double cartPrice;
	
	private Double  quantity;
	
	private Double totalPrice;

	public Integer getCartId() {
		return cartId;
	}

	public Cart(Integer cartId, Products product, Users user, Double cartPrice, Double quantity, Double totalPrice) {
	
		this.cartId = cartId;
		this.product = product;
		this.user = user;
		this.cartPrice = cartPrice;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
	}

	public Cart() {
		// TODO Auto-generated constructor stub
	}

	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}

	public Products getProduct() {
		return product;
	}

	public void setProduct(Products product) {
		this.product = product;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	
	public Double getCartPrice() {
		return cartPrice;
	}

	public void setCartPrice(Double cartPrice) {
		this.cartPrice = cartPrice;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	
	
}
