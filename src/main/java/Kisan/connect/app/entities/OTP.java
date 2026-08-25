package Kisan.connect.app.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class OTP {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String mobileNumber;

    private int otp;

    public Integer getId() {
		return id;
	}

	public OTP() {
		
		// TODO Auto-generated constructor stub
	}

	public OTP(String mobileNumber, int otp, LocalDateTime expiryTime, boolean verified) {
	
		this.mobileNumber = mobileNumber;
		this.otp = otp;
		this.expiryTime = expiryTime;
		this.verified = verified;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}

	public LocalDateTime getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(LocalDateTime expiryTime) {
		this.expiryTime = expiryTime;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	private LocalDateTime expiryTime;

    private boolean verified;
}
