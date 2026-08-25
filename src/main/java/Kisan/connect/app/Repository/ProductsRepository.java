package Kisan.connect.app.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Kisan.connect.app.entities.Products;
import Kisan.connect.app.entities.Users;
@Repository
public interface ProductsRepository extends JpaRepository<Products,Integer> {

	List<Products> findByCategory(String category);

	List<Products> findByFarmer(Users users);
	
	

}
