package in.rohanIT.ecommerce.services;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import in.rohanIT.ecommerce.dto.UserDto;
import in.rohanIT.ecommerce.entity.CustomerOrder;
import in.rohanIT.ecommerce.entity.UserDetails;
import in.rohanIT.ecommerce.repo.CustomerOrderRepo;
import in.rohanIT.ecommerce.repo.UserRepo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class UserService {

	@Autowired
	UserRepo userRepo;
	
	@Autowired
	JavaMailSender mailSender;

//	@Autowired
//	UserEntity userEntity;
	
	@Autowired
	private CustomerOrderRepo custOrderRepo;
	
	
	private static final int tempPwLength=12;
	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
	private static final String optNumbers="0123456789";
	
	public String userRegistration(UserDto userDto) {
	    if (userDto == null) {
	        throw new RuntimeException("User data cannot be null");
	    }

	    UserDetails userDetails = new UserDetails();
	    String emailId=userDto.getEmailID();
	    if(userRepo.findByEmailID(emailId)!=null) {
	    	return "Email Already Exists";
	    }
	    if(userRepo.findByUserContact(userDto.getUserContact())!=null) {
	    	return "Contact number already exist";
	    }
	    userDetails.setUserName(userDto.getUserName());
	    userDetails.setEmailID(userDto.getEmailID());
	    userDetails.setUserContact(userDto.getUserContact());
	    userDetails.setAddress(userDto.getAddress());
	    userDetails.setPwChanged(false);
	    
	    String tempPw=generateRandomPw();
	    userDetails.setPassword(tempPw);
		try {
			sendWelcomeEmail(userDto,tempPw);
			return "Email sent to registered email id";
		}
		catch(Exception e){
			System.out.println(e);
			return "Registration done, but failed to send email. Contact support.";
        }
		finally {
			userRepo.save(userDetails);
		}
	}
	
private void sendWelcomeEmail(UserDto userDto, String pw) throws MessagingException {
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(mimeMessage, true);
		helper.setTo(userDto.getEmailID());
		helper.setSubject("Welcome! Your temp pw");
		String htmlBody = """
                <html>
                <body>
                    <h2>Welcome, %s!</h2>
                    <p>Your account has been created successfully.</p>
                    <p><strong>Temporary Password:</strong> %s</p>
                    <p><strong>Important:</strong> For security reasons, please log in and change your password immediately.</p>
                    <br>
                    <p>Best regards,<br>Your Quotes App Team</p>
                </body>
                </html>
                """.formatted(userDto.getUserName() != null ? userDto.getUserName() : "User", pw);

        helper.setText(htmlBody, true); // true = html

        mailSender.send(mimeMessage);
	}

	private String generateRandomPw() {
		SecureRandom sc=new SecureRandom();
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<tempPwLength;i++) {
			int indx=sc.nextInt(CHARACTERS.length());
			sb.append(CHARACTERS.charAt(indx));
		}
		return sb.toString();
	}

	public Map<String, Object> userLogin(UserDto userDto) {

	    Map<String, Object> response = new HashMap<>();

	    UserDetails user = userRepo.findByEmailID(userDto.getEmailID());

	    if (user == null) {
	        response.put("status", "error");
	        response.put("message", "User not found");
	        return response;
	    }
	    String pw=user.getPassword();
	    if (!pw.equals(userDto.getPassword())) {
	        response.put("status", "error");
	        response.put("message", "Wrong password");
	        return response;
	    }
	    Map<String, Object> userData = new HashMap<>();
	    userData.put("id", user.getId());
	    userData.put("userName", user.getUserName());
	    userData.put("emailID", user.getEmailID());
	    userData.put("userContact", user.getUserContact());
	    userData.put("address", user.getAddress());
	    userData.put("pwChanged", user.isPwChanged());


	    response.put("status", "success");
	    response.put("message", "User found");
	    response.put("data", userData);
//	    response.put("data", user);

	    return response;
	}

	public String userPwChange(UserDto userDto) {
		
		 UserDetails user = userRepo.findByEmailID(userDto.getEmailID());

		    if (user == null) {
		        return "User Not Found For This Email";
		    }
		    user.setPassword(userDto.getPassword());
		    user.setPwChanged(true);
		    userRepo.save(user);
		    return "Password Change Successfully"; 

	}
	
	public List<CustomerOrder> getMyOrders(UserDto userDto){
		return custOrderRepo.findByUserId(userDto.getId());
	}

	public UserDetails getUser(String email) {
		// TODO Auto-generated method stub
		return userRepo.findByEmailID(email);
	}
	
}
