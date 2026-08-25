package Kisan.connect.app.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Kisan.connect.app.entities.Cart;
import Kisan.connect.app.entities.Users;
@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {

	List<Cart> findByUser(Users users);

	
	
	
}
