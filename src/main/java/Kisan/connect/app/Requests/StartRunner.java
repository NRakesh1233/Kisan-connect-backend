package Kisan.connect.app.Requests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import Kisan.connect.app.Services.MailService;
@Component
public class StartRunner implements CommandLineRunner {

	@Autowired
	private MailService mail;
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		mail.sendMessage(
				//nrakeshraki282@gmail.com
				"nakka.240101261@iiitbh.ac.in",			
				 "Kisan Connect Started",
				" Hello Priya, I hope you're doing well.	"
				+ " I wanted to share something special with you..."

				

			);
		System.out.println("sent email successfully");
	}
	


}
