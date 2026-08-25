package Kisan.connect.app.Requests;



public class OrderItemRequest {

    
    private Integer productId;

    private Double quantity;

   

    

    public OrderItemRequest() {
    }

   

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

   
}