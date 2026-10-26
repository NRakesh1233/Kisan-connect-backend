package Kisan.connect.app.Response;

import java.time.LocalDate;

import Kisan.connect.app.entities.Active;
import Kisan.connect.app.entities.Gender;
import Kisan.connect.app.entities.Role;


public class UserResponse {
	
	private Integer Id;
	
	private String name;
	
	private String email;
private Double rating;

	public Double getRating() {
	return rating;
}
public void setRating(Double rating) {
	this.rating = rating;
}
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
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
	public String getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public LocalDate getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDate getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDate updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Active getActive() {
		return active;
	}
	public void setActive(Active active) {
		this.active = active;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	private Gender gender;
	private LocalDate dateOfBirth;
	private String mobileNumber;
	private String profilePic;
	private String bio;
	private LocalDate createdAt;
	private LocalDate updatedAt;
	private Active active;
	private Role role;


	public UserResponse(Integer id, String name, String email, Gender gender, LocalDate dateOfBirth,
			String mobileNumber, String profilePic, String bio, LocalDate createdAt, LocalDate updatedAt, Active active,
			Role role) {
		Id = id;
		this.name = name;
		this.email = email;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.mobileNumber = mobileNumber;
		this.profilePic = profilePic;
		this.bio = bio;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.active = active;
		this.role = role;
	}


}
