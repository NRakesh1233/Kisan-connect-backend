package Kisan.connect.app.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Kisan.connect.app.entities.Address;
import Kisan.connect.app.entities.Users;

@Repository
public interface AddressRepository extends JpaRepository<Address,Integer>{

	List<Address> findByCustomer(Users users);

}
