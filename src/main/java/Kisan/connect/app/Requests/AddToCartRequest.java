package Kisan.connect.app.Requests;

import org.springframework.beans.BeanUtils;

import Kisan.connect.app.entities.Cart;
import Kisan.connect.app.entities.Products;
import Kisan.connect.app.entities.Users;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class AddToCartRequest {

	
private int productId;

	
	private Double cartPrice;
	
	private Double quantity;
	
	private Double totalPrice;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
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

	public static Cart getCartEntity( AddToCartRequest request) {
				
		Cart cart=new Cart();
		BeanUtils.copyProperties(request, cart);
		
		return cart;
		
	}
	
}
