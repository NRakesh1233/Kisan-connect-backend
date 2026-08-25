package Kisan.connect.app.entities;

import java.time.LocalDate;

import jakarta.persistence.*;


@Entity
public class Users {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer Id;
	
	private String name;
	 @Column(unique=true)
	private String email;
	private String password;
	@Enumerated(EnumType.STRING)
	private Gender gender;
	private LocalDate dateOfBirth;
	private String mobileNumber;
	private String profilePic;
	private String bio;
	private LocalDate createdAt;
	private LocalDate updatedAt;
	@Enumerated(EnumType.STRING)
	private Verifyed mobileVerified;
	@Enumerated(EnumType.STRING)
	private Verifyed mailVerified;
	@Enumerated(EnumType.STRING)
	private Active active;
	@Enumerated(EnumType.STRING)
	private Role role;                                                                       

	
	 public Users(String name, String email, String password, Gender gender, LocalDate dateOfBirth, String mobileNumber,
			String profilePic, String bio, LocalDate createdAt, LocalDate updatedAt, Verifyed mobileVerified,
			Verifyed mailVerified, Active active, Role role) {

		this.name = name;
		this.email = email;
		this.password = password;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.mobileNumber = mobileNumber;
		this.profilePic = profilePic;
		this.bio = bio;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.mobileVerified = mobileVerified;
		this.mailVerified = mailVerified;
		this.active = active;
		this.role = role;
	}



	 public Users() {
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



		public Gender getGender() {
			return gender;
		}



		public void setGender(Gender string) {
			this.gender = string;
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



		public Verifyed getMobileVerified() {
			return mobileVerified;
		}



		public void setMobileVerified(Verifyed mobileVerified) {
			this.mobileVerified = mobileVerified;
		}



		public Verifyed getMailVerified() {
			return mailVerified;
		}



		public void setMailVerified(Verifyed mailVerified) {
			this.mailVerified = mailVerified;
		}



		public Active getActive() {
			return active;
		}



		public void setActive(Active active) {
			this.active = active;
		}
	
	
	
	
	
	
	
	
	
	
	
	
}
