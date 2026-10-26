package Kisan.connect.app.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Kisan.connect.app.entities.OrderItems;
import Kisan.connect.app.entities.Products;
import Kisan.connect.app.entities.Review;
import Kisan.connect.app.entities.Users;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer> {
	@Query("SELECT AVG(r.rating) FROM Review r WHERE r.product.productId = :productId")
	Double getAverageRatingByProductId(Integer productId);
	
	
	@Query("SELECT r.product FROM Review r GROUP BY r.product ORDER BY AVG(r.rating) DESC")
	List<Products> getHighRatingProducts(Pageable pageable);

	
	@Query("SELECT r FROM Review r  where r.product.farmer=:farmer")

	List<Review> findByFarmer(Users farmer);

	@Query("SELECT COUNT(r) FROM Review r WHERE r.product.productId = :productId")

	Integer getReviewCount(Integer productId);

	@Query("SELECT r FROM Review r  where r.product = :product")

	List<Review> findByProduct(Products product);
	
	
	@Query("SELECT avg(r.rating) FROM Review r  where r.product.productId = :productId")

	double findByProductRating(Integer productId);
	
	@Query("SELECT AVG(r.rating) FROM Review r WHERE r.product.farmer = :farmer")
    Double findAverageRatingByFarmer(@Param("farmer") Users farmer);


	Optional<Review> findByCustomerAndOrderItem(Users customer, OrderItems item);
}
