package Kisan.connect.app.Response;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

public class ProductsResponse {
	private Integer productId;

   private Double avgRating;
   private Integer reviewCount;
	public Integer getReviewCount() {
	return reviewCount;
}
   public void setReviewCount(Integer reviewCount) {
	this.reviewCount = reviewCount;
   }
	public ProductsResponse(Integer productId, String productName, Double price, Double stock, String unit,
			String category, String description, String image, LocalDate harvestDate, LocalDate expiryDate,
			Integer farmerId, String farmerName, String profilePic,Double avgRating,String productImage,Double totalStock) {
	this.totalStock=totalStock;
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.stock = stock;
		this.unit = unit;
		this.category = category;
		this.description = description;
		this.image = image;
		this.harvestDate = harvestDate;
		this.expiryDate = expiryDate;
		this.farmerId = farmerId;
		this.farmerName = farmerName;
		this.profilePic = profilePic;
		this.avgRating=avgRating;
		this.productImage=productImage;
	}
	public Double getAvgRating() {
		return avgRating;
	}
	public void setAvgRating(Double avgRating) {
		this.avgRating = avgRating;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	private String productName;

    private Double price;

    private Double stock;

    private String unit;

    private String category;

    private String description;

    private String image;

    private LocalDate harvestDate;

    private LocalDate expiryDate;
    private Integer farmerId;
    private String farmerName;
    private String profilePic;
    private String productImage;
    private Double totalStock;
    
    
	public Double getTotalStock() {
		return totalStock;
	}
	public void setTotalStock(Double totalStock) {
		this.totalStock = totalStock;
	}
	public String getProductImage() {
		return productImage;
	}
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}
	public String getFarmerName() {
		return farmerName;
	}
	public void setFarmerName(String farmerName) {
		this.farmerName = farmerName;
	}
	public String getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getStock() {
		return stock;
	}
	public void setStock(Double stock) {
		this.stock = stock;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public LocalDate getHarvestDate() {
		return harvestDate;
	}
	public void setHarvestDate(LocalDate harvestDate) {
		this.harvestDate = harvestDate;
	}
	public LocalDate getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}
	public Integer getFarmerId() {
		return farmerId;
	}
	public void setFarmerId(Integer farmerId) {
		this.farmerId = farmerId;
	}

}
