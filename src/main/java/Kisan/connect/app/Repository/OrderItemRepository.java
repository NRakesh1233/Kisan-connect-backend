package Kisan.connect.app.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Kisan.connect.app.entities.OrderItems;
import Kisan.connect.app.entities.Orders;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItems,Integer>{

	List<OrderItems> findByOrder(Orders o);

}
