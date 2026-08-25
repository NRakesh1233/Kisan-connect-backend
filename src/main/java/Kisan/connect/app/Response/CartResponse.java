package Kisan.connect.app.Response;


public class CartResponse {

    private Integer cartId;

    private Integer productId;
    private String productName;

    public CartResponse(Integer cartId, Integer productId, String productName, Double productPrice, Double quantity,
			Double totalPrice, String productImage, String category, String unit, Double availableStock,
			Integer farmerId, String farmerName, String farmerProfilePic) {
	
		this.cartId = cartId;
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
		this.productImage = productImage;
		this.category = category;
		this.unit = unit;
		this.availableStock = availableStock;
		this.farmerId = farmerId;
		this.farmerName = farmerName;
		this.farmerProfilePic = farmerProfilePic;
	}

	private Double productPrice;

    private Double quantity;

    private Double totalPrice;

    private String productImage;

    private String category;

    private String unit;

    private Double availableStock;

    private Integer farmerId;

    private String farmerName;

    private String farmerProfilePic;

    public CartResponse() {
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
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

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
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

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(Double availableStock) {
        this.availableStock = availableStock;
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

    public String getFarmerProfilePic() {
        return farmerProfilePic;
    }

    public void setFarmerProfilePic(String farmerProfilePic) {
        this.farmerProfilePic = farmerProfilePic;
    }
}