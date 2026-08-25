package Kisan.connect.app.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

	
	@Autowired
	private JavaMailSender sender;
	
	public void sendMessage(String to,String subject,String body) {
		
		
		SimpleMailMessage message=new SimpleMailMessage();
		message.setFrom("nrakeshraki282@gmail.com");
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		sender.send(message);
	}
	
	
}
