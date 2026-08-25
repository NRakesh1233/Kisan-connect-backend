package Kisan.connect.app.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Address {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int addressId;
	

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String mobileNumber;

  
    private String houseNo;


    private String street;

    @Column(nullable = false)
    private String village;

    private String mandal;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String pincode;

    @Column(length = 300)
    private String landmark;
    @Column(nullable =true )
    private String addressType; // HOME, WORK, OTHER

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users customer;

    public Address() {
    }

    // Getters and Setters

    public Address(String fullName, String mobileNumber, String village, String district, String state, String pincode,
			Users customer, String addressType) {
	
		this.fullName = fullName;
		this.mobileNumber = mobileNumber;
		this.village = village;
		this.district = district;
		this.state = state;
		this.pincode = pincode;
		this.customer = customer;
		this.addressType=addressType;
	}

	public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getMandal() {
        return mandal;
    }

    public void setMandal(String mandal) {
        this.mandal = mandal;
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

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public Users getCustomer() {
        return customer;
    }

    public void setCustomer(Users customer) {
        this.customer = customer;
    }
	

}
