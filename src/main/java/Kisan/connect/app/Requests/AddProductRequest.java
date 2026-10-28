package Kisan.connect.app.Requests;

import java.time.LocalDate;

import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;

import Kisan.connect.app.entities.Products;

public class AddProductRequest {




    private String productName;

    public Double getTotalStock() {
		return totalStock;
	}




	public void setTotalStock(Double totalStock) {
		this.totalStock = totalStock;
	}



	private Double price;

    private Double stock;
    
    private Double totalStock;
    private String unit;

    private String category;

    private String description;

    private MultipartFile image;

    private LocalDate harvestDate;

    private LocalDate expiryDate;

    public AddProductRequest() {
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
		this.category = category.toLowerCase();
	}




	public String getDescription() {
		return description;
	}




	public void setDescription(String description) {
		this.description = description;
	}




	public AddProductRequest(String productName, Double price,Double stock, Double totalStock, String unit, String category,
			String description, MultipartFile image, LocalDate harvestDate, LocalDate expiryDate) {
		
		this.productName = productName;
		this.price = price;
		this.stock = stock;
		this.totalStock = totalStock;
		this.unit = unit;
		this.category = category;
		this.description = description;
		this.image = image;
		this.harvestDate = harvestDate;
		this.expiryDate = expiryDate;
	}


	public MultipartFile getImage() {
		return image;
	}


	public void setImage(MultipartFile image) {
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
    
    
    
    public static Products getProductEntity(AddProductRequest request) {
    	
    	Products product =new Products();
    	
                          
    	BeanUtils.copyProperties(request, product, "image");
    	
		return product;
    	
    }
    
   
}
