package Kisan.connect.app.Requests;

import org.springframework.web.multipart.MultipartFile;

public class AddProfileRequest {

	
	private MultipartFile profilePic;

	public MultipartFile getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(MultipartFile profilePic) {
		this.profilePic = profilePic;
	}

	
}
