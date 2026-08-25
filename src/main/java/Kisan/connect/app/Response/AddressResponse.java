package Kisan.connect.app.Response;

public class AddressResponse {
	private int addressId;

    public AddressResponse(int addressId, String fullName, String mobileNumber, String houseNo, String street,
			String village, String mandal, String district, String state, String pincode, String landmark,
			String addressType, String fullAddress) {
		
		this.addressId = addressId;
		this.fullName = fullName;
		this.mobileNumber = mobileNumber;
		this.houseNo = houseNo;
		this.street = street;
		this.village = village;
		this.mandal = mandal;
		this.district = district;
		this.state = state;
		this.pincode = pincode;
		this.landmark = landmark;
		this.addressType = addressType;
		this.fullAddress = fullAddress;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	private String fullName;

    private String mobileNumber;

    private String houseNo;

    private String street;

    private String village;

    private String mandal;

    private String district;

    private String state;

    private String pincode;

    private String landmark;

    private String addressType;

   private String fullAddress;
    public String getFullAddress() {
	return fullAddress;
}

   public void setFullAddress(String fullAddress) {
	this.fullAddress = fullAddress;
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
    
}
