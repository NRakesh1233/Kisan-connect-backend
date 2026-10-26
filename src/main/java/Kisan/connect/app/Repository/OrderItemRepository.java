package Kisan.connect.app.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Kisan.connect.app.entities.OrderItems;
import Kisan.connect.app.entities.Orders;
import Kisan.connect.app.entities.Products;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItems,Integer>{

	List<OrderItems> findByOrder(Orders o);
	
	
	
	@Query("""
		    SELECT DISTINCT r.order
		    FROM OrderItems r
		    WHERE r.product.productId = :p
		""")
		List<Orders> findByProductId(@Param("p") int productId);
	
     
 

	

}
