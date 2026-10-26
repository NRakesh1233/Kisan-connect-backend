package Kisan.connect.app.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import Kisan.connect.app.entities.Cart;
import Kisan.connect.app.entities.Users;
@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {

	List<Cart> findByUser(Users users);

@Modifying
@Transactional
@Query("DELETE FROM Cart c WHERE c.user = :user")
void deleteByUser(@Param("user") Users user);

	
	
	
}
