package Kisan.connect.app.Requests;



import java.time.LocalDate;

import Kisan.connect.app.entities.Active;
import Kisan.connect.app.entities.Gender;
import Kisan.connect.app.entities.Role;
import Kisan.connect.app.entities.Verifyed;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;





public class UserRegisterRequest {
	
	

	private String name;

	private String email;
	private String password;
	
	
	private Role role;

	private Gender gender;
	private LocalDate dateOfBirth;
	private String mobileNumber;
	private String bio;
	
	private String village;
	private String  district;
	private String state;
	private String pincode;
	
	
	
	public String getVillage() {
		return village;
	}


	public void setVillage(String village) {
		this.village = village;
	}


	public String getDistrict() {
		return district;
	}


	public void setDistrict(String district) {
		this.district = district;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getPincode() {
		return pincode;
	}


	public void setPincode(String pincode) {
		this.pincode = pincode;
	}


	public Gender getGender() {
		return gender;
	}


	public void setGender(Gender gender) {
		this.gender = gender;
	}


	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}


	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}


	public String getMobileNumber() {
		return mobileNumber;
	}


	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}


	public String getBio() {
		return bio;
	}


	public void setBio(String bio) {
		this.bio = bio;
	}



	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}
	


}
