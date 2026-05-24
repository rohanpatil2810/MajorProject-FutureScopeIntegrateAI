package in.rohanIT.ecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import in.rohanIT.ecommerce.dto.UserDto;
import in.rohanIT.ecommerce.dto.VendorDto;
import in.rohanIT.ecommerce.entity.UserDetails;
import in.rohanIT.ecommerce.entity.Vendors;
import in.rohanIT.ecommerce.repo.UserRepo;
import in.rohanIT.ecommerce.repo.VendorRepo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class ProfileService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private VendorRepo vendorRepo;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public UserDetails updateUser(UserDto userDto) throws MessagingException {
		String email=userDto.getEmailID();
		String contactNo=userDto.getUserContact();
		UserDetails userByEmail=userRepo.findByEmailID(email);
		UserDetails userByContact=userRepo.findByUserContact(userDto.getUserContact());
		if(userRepo.findByEmailID(email)!=null) {
			if(userRepo.findByUserContact(contactNo)!=null) {
				userByEmail=null;
				   throw new RuntimeException("Contact number already exists");
			}
			userByEmail.setUserContact(userDto.getUserContact());
			userByEmail.setAddress(userDto.getAddress());
//			user.setEmailID(userDto.getEmailID());
			userByEmail.setUserName(userDto.getUserName());			
		}
		else if(userRepo.findByUserContact(userDto.getUserContact())!=null) {
			UserDetails byEmailID = userRepo.findByEmailID(email);
			if(byEmailID!=null) {
				throw new RuntimeException("Email Already exist");
			}
			userByContact.setEmailID(userDto.getEmailID());
//			user.setUserContact(userDto.getUserContact());
			userByContact.setAddress(userDto.getAddress());
			userByContact.setEmailID(email);
		}
		
		sendEmail(userDto);	
		if(userByContact!=null) {			
			return userRepo.save(userByContact);
		}
		return userRepo.save(userByEmail);
	}
	public Vendors updateVendor(VendorDto vendorDto) throws MessagingException {
		String email=vendorDto.getEmail();
		String contactNo=vendorDto.getContactNo();
		Vendors vendorByEmail=vendorRepo.findByEmail(email);
		Vendors vendorByContact=vendorRepo.findByContactNo(contactNo);
		if(vendorByEmail!=null) {
			if(vendorByContact!=null) {
				vendorByEmail=null;
				   throw new RuntimeException("Contact number already exists");
			}
			vendorByEmail.setContactNo(vendorDto.getContactNo());
			vendorByEmail.setAddress(vendorDto.getAddress());
//			user.setEmailID(userDto.getEmailID());
			vendorByEmail.setEmail(vendorDto.getEmail());
			vendorByEmail.setFullName(vendorDto.getFullName());
			vendorByEmail.setRole("Vendor");
			vendorByEmail.setShopname(vendorDto.getShopname());
		}
		else if(vendorByContact!=null) {
			if(vendorByEmail!=null) {
				vendorByContact=null;
				throw new RuntimeException("Email Already exist");
			}
			vendorByContact.setContactNo(vendorDto.getContactNo());
			vendorByContact.setAddress(vendorDto.getAddress());
//			user.setEmailID(userDto.getEmailID());
			vendorByContact.setEmail(vendorDto.getEmail());
			vendorByContact.setFullName(vendorDto.getFullName());
			vendorByContact.setRole("Vendor");
			vendorByContact.setShopname(vendorDto.getShopname());
		}
		
		sendEmailVendor(vendorDto);	
		if(vendorByContact!=null) {			
			return vendorRepo.save(vendorByContact);
		}
		return vendorRepo.save(vendorByEmail);
	}

	private void sendEmail(UserDto userDto) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(mimeMessage, true);
		helper.setTo(userDto.getEmailID());
		helper.setSubject("Profile Update Successfully!");
		String htmlBody = """
                <html>
                <body>
                    <h2>Welcome Once Again!, %s!</h2>
                    <p>Your Profile has been updated successfully.</p>
                    <p><strong>Enjoy shopping with email:</strong> %s</p>
                    <br>
                    <p>Best regards,<br>Your Quotes App Team</p>
                </body>
                </html>
                """.formatted(userDto.getUserName(),userDto.getEmailID());

        helper.setText(htmlBody, true); // true = html

        javaMailSender.send(mimeMessage);
		
	}
	
	private void sendEmailVendor(VendorDto userDto) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(mimeMessage, true);
		helper.setTo(userDto.getEmail());
		helper.setSubject("Profile Update Successfully!");
		String htmlBody = """
                <html>
                <body>
                    <h2>Welcome Once Again!, %s!</h2>
                    <p>Your Profile has been updated successfully.</p>
                    <p><strong>Enjoy shopping with email:</strong> %s</p>
                    <br>
                    <p>Best regards,<br>Your Quotes App Team</p>
                </body>
                </html>
                """.formatted(userDto.getFullName(),userDto.getEmail());

        helper.setText(htmlBody, true); // true = html

        javaMailSender.send(mimeMessage);
		
	}
	
	public UserDetails getUserByEmail(String email) {
	    return userRepo.findByEmailID(email);
	}

	public Vendors getVendorByEmail(String email) {
	    return vendorRepo.findByEmail(email);
	}
}

//package in.rohanIT.ecommerce.services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//
//import in.rohanIT.ecommerce.dto.UserDto;
//import in.rohanIT.ecommerce.dto.VendorDto;
//import in.rohanIT.ecommerce.entity.UserDetails;
//import in.rohanIT.ecommerce.entity.Vendors;
//import in.rohanIT.ecommerce.repo.UserRepo;
//import in.rohanIT.ecommerce.repo.VendorRepo;
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//
//@Service
//public class ProfileService {
//
//    @Autowired private UserRepo userRepo;
//    @Autowired private VendorRepo vendorRepo;
//    @Autowired private JavaMailSender javaMailSender;
//
//    public UserDetails updateUser(UserDto userDto) {
//        UserDetails user = userRepo.findByEmailID(userDto.getEmailID());
//        if (user == null) {
//            throw new RuntimeException("User not found");
//        }
//
//        // Check if contact number is taken by another user
//        UserDetails existingContact = userRepo.findByUserContact(userDto.getUserContact());
//        if (existingContact != null && existingContact.getId() != user.getId()) {
//            throw new RuntimeException("Contact number already exists");
//        }
//
//        user.setUserName(userDto.getUserName());
//        user.setUserContact(userDto.getUserContact());
//        user.setAddress(userDto.getAddress());
//
//        sendEmail(userDto);
//        return userRepo.save(user);
//    }
//
//    public Vendors updateVendor(VendorDto vendorDto) {
//        Vendors vendor = vendorRepo.findByEmail(vendorDto.getEmail());
//        if (vendor == null) {
//            throw new RuntimeException("Vendor not found");
//        }
//
//        // Check if contact number is taken by another vendor
//        Vendors existingContact = vendorRepo.findByContactNo(vendorDto.getContactNo());
//        if (existingContact != null && existingContact.getId() != vendor.getId()) {
//            throw new RuntimeException("Contact number already exists");
//        }
//
//        vendor.setFullName(vendorDto.getFullName());
//        vendor.setContactNo(vendorDto.getContactNo());
//        vendor.setAddress(vendorDto.getAddress());
//        vendor.setShopname(vendorDto.getShopname());
//
//        sendEmailVendor(vendorDto);
//        return vendorRepo.save(vendor);
//    }
//
//    private void sendEmail(UserDto userDto) {
//        try {
//            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//            helper.setTo(userDto.getEmailID());
//            helper.setSubject("Profile Updated Successfully!");
//            String htmlBody = """
//                <html><body>
//                    <h2>Profile Updated!</h2>
//                    <p>Dear %s,</p>
//                    <p>Your profile has been updated successfully.</p>
//                    <br>
//                    <p>Best regards,<br>Ecommerce Team</p>
//                </body></html>
//                """.formatted(userDto.getUserName());
//            helper.setText(htmlBody, true);
//            javaMailSender.send(mimeMessage);
//        } catch (Exception e) {
//            System.out.println("Email failed: " + e.getMessage());
//        }
//    }
//
//    private void sendEmailVendor(VendorDto vendorDto) {
//        try {
//            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//            helper.setTo(vendorDto.getEmail());
//            helper.setSubject("Profile Updated Successfully!");
//            String htmlBody = """
//                <html><body>
//                    <h2>Profile Updated!</h2>
//                    <p>Dear %s,</p>
//                    <p>Your vendor profile has been updated successfully.</p>
//                    <br>
//                    <p>Best regards,<br>Ecommerce Team</p>
//                </body></html>
//                """.formatted(vendorDto.getFullName());
//            helper.setText(htmlBody, true);
//            javaMailSender.send(mimeMessage);
//        } catch (Exception e) {
//            System.out.println("Email failed: " + e.getMessage());
//        }
//    }
//
//    public UserDetails getUserByEmail(String email) {
//        return userRepo.findByEmailID(email);
//    }
//
//    public Vendors getVendorByEmail(String email) {
//        return vendorRepo.findByEmail(email);
//    }
//}