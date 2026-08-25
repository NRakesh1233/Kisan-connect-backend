package Kisan.connect.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Kisan.connect.app.entities.OTP;

public interface OtpRepository extends JpaRepository<OTP,Integer> {

	OTP findByMobileNumber(String mobile);

}
