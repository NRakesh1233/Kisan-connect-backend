package Kisan.connect.app.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import Kisan.connect.app.entities.OrderStatus;
import Kisan.connect.app.entities.Orders;
import Kisan.connect.app.entities.Users;
@Repository
public interface OrdersRepository extends JpaRepository<Orders,Integer> {

	List<Orders> findByCustomer(Users customer);

	List<Orders> findByFarmer(Users farmer);
	
	@Query("select sum(o.totalAmount) from Orders o where o.farmer=:farmer")
	Double findRevenue(Users farmer);

	List<Orders> findByFarmerAndOrderStatus(Users farmer, OrderStatus status);

}
