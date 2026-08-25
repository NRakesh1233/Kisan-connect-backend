package Kisan.connect.app.entities;


import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @Column(nullable = false, length = 100)
    private String productName;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Double stock;

    @Column(nullable = false, length = 20)
    private String unit;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private LocalDate harvestDate;

    @Column(nullable = false)
    private LocalDate expiryDate;
    
    @Column(nullable = false)
    private String category;
    private Double totalStock=stock;

    public Double getTotalStock() {
		return totalStock;
	}

	public void setTotalStock(Double totalStock) {
		this.totalStock = totalStock;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Column(nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farmer_email", nullable = false)
    private Users farmer;

    public Products() {
    }

  

    public Products(Integer productId, String productName, Double price, Double stock, String unit,
			String description, String imageUrl, LocalDate harvestDate, LocalDate expiryDate, String category,
			String status, Users farmer,Double totalStock) {
		
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.stock =stock;
		this.unit = unit;
		this.description = description;
		this.image = imageUrl;
		this.harvestDate = harvestDate;
		this.expiryDate = expiryDate;
		this.category = category;
		this.status = status;
		this.farmer = farmer;
		this.totalStock=totalStock;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

   

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

  

    public Double getStock() {
		return stock;
	}

	public void setStock(Double stock) {
		this.stock = stock;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Users getFarmer() {
        return farmer;
    }

    public void setFarmer(Users farmer) {
        this.farmer = farmer;
    }
}