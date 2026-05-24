package in.rohanIT.ecommerce.services;

import java.security.SecureRandom;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import in.rohanIT.ecommerce.dto.ProductDto;
import in.rohanIT.ecommerce.dto.ProductResponseDto;
import in.rohanIT.ecommerce.dto.VendorDto;
import in.rohanIT.ecommerce.entity.Product;
import in.rohanIT.ecommerce.entity.Vendors;
import in.rohanIT.ecommerce.repo.ProductRepo;
import in.rohanIT.ecommerce.repo.UserRepo;
import in.rohanIT.ecommerce.repo.VendorRepo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class VendorService {

	@Autowired
	private VendorRepo vendorRepo;
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	UserRepo userRepo;
	
	private static final int tempPwLength=12;
	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
	
//	public String vendorRegistration(VendorDto vendorDto) {
//		if(vendorDto.getEmail()==null) {
//			return "Please Provide Valid Email";
//		}
//		 if (vendorRepo.findByEmail(vendorDto.getEmail()) != null) {
//		        return "Email Already Exist";
//		    }
//		 
//		Vendors vendor=new Vendors();
//		vendor.setAddress(vendorDto.getAddress());
//		vendor.setContactNo(vendorDto.getAddress());
//		vendor.setEmail(vendorDto.getEmail());
//		vendor.setFullName(vendorDto.getFullName());
//		vendor.setShopname(vendorDto.getShopname());
//		
//		return vendorRepo.save(vendor)!=null?"Registration Successfull":"Unable to Register";
//	}
	public String vendorRegistration(VendorDto vendorDto) {

	    if (vendorDto.getEmail() == null || vendorDto.getEmail().isEmpty()) {
	        return "Please Provide Valid Email";
	    }

	    if (vendorRepo.findByEmail(vendorDto.getEmail()) != null) {
	        return "Email Already Exist as a Affiliate Marketer";
	    }
	    if(userRepo.findByEmailID(vendorDto.getEmail())!=null) {
	    	return "Already registered as a User";
	    }

	    Vendors vendor = new Vendors();

	    vendor.setAddress(vendorDto.getAddress());
	    vendor.setContactNo(vendorDto.getContactNo());
	    vendor.setEmail(vendorDto.getEmail());
	    vendor.setFullName(vendorDto.getFullName());
	    vendor.setShopname(vendorDto.getShopname());
	    vendor.setRole("Vendor");

	    String tempPw=generatePW();
	    
	    vendor.setPw(tempPw);
	    try {
			sendWelcomeEmail(vendorDto,tempPw);
			return "Email sent to registered email id";
		}
		catch(Exception e){
			System.out.println(e);
			return "Registration done, but failed to send email. Contact support.";
        }
		finally {
			vendorRepo.save(vendor);
		}
//	    return  "Registration Successful";
	}
	
private void sendWelcomeEmail(VendorDto vendorDto, String pw) throws MessagingException {
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(mimeMessage, true);
		helper.setTo(vendorDto.getEmail());
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
                """.formatted(vendorDto.getFullName() != null ? vendorDto.getFullName() : "User", pw);

        helper.setText(htmlBody, true); // true = html

        mailSender.send(mimeMessage);
	}
	
	public String generatePW() {
		SecureRandom sc=new SecureRandom();
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<6;i++) {
			int charAt=sc.nextInt(CHARACTERS.length());
			sb.append(CHARACTERS.charAt(charAt));
		}
		return sb.toString();
	}
	
	public String changePw(VendorDto vendorDto) {
		Vendors vendor=vendorRepo.findByEmail(vendorDto.getEmail());
		if(vendor==null) {
			return "Email Id does not exist";
		}
		vendor.setPw(vendorDto.getPw());
		vendorRepo.save(vendor);
		return "Pw changed successfully";
	}
	
//	public Vendors login(VendorDto vendorDto) {
//		Vendors byEmail = vendorRepo.findByEmail(vendorDto.getEmail());
//		if ( byEmail != null) {
//	        if(byEmail.getPw().equals(vendorDto.getPw())) {
//	        	return byEmail;
//	        }
//	    }
//		return byEmail;
//	}
//	
	public Vendors login(VendorDto vendorDto) {

	    Vendors vendor = vendorRepo.findByEmail(vendorDto.getEmail());

	    if (vendor == null) {
	        throw new RuntimeException("Invalid Email");
	    }

	    if (!vendor.getPw().equals(vendorDto.getPw())) {
	        throw new RuntimeException("Invalid Password");
	    }

	    return vendor;
	}
//	public List<Product> getProducts(Vendors vendor){
//		List<Product> p=productRepo.findByVendors_Id(vendor.getId());
//		for(Product p1:p) {
//			System.out.println(p1.getCategory());	
//			System.out.println(p1 	);
//		}
//		return productRepo.findByVendors_Id(vendor.getId());
//	}
	public List<ProductResponseDto> getProducts(Vendors vendor){
	    List<Product> products = productRepo.findByVendors_Id(vendor.getId());
	    
	    // Convert to DTO to avoid recursion
	    return products.stream()
	            .map(ProductResponseDto::new)
	            .toList();
	}
	
	public Product addProduct(ProductDto productDto) {
		Product productObj=new Product();
		productObj.setCategory(productDto.getCategory());
		productObj.setDescription(productDto.getDescription());
		productObj.setName(productDto.getName());
		productObj.setPrice(productDto.getPrice());
		productObj.setQuantity(productDto.getQuantity());
		productObj.setVendors(productDto.getVendors());
		return productRepo.save(productObj);
	}
	
	public String delAllMyProducts(ProductDto productDto) {

	    long deleted = productRepo
	            .deleteByVendors_Id(productDto.getVendors().getId());

	    return deleted > 0
	            ? "Product Deleted Successfully"
	            : "Something Went Wrong";
	}
//	public String updateProduct(ProductDto productDto) {
//
//	    int updatedRows = productRepo.updateProduct(
//	            productDto.getId(),
//	            productDto.getName(),
//	            productDto.getDescription(),
//	            productDto.getPrice(),
//	            productDto.getQuantity(),
//	            productDto.getCategory(),
//	            productDto.getVendors()
//	    );
//
//	    return updatedRows > 0
//	            ? "Product Updated Successfully"
//	            : "Product Not Found";
//	}
	public String updateProduct(ProductDto productDto) {

	    Product product = productRepo.findById(productDto.getId())
	            .orElseThrow(() ->
	                    new RuntimeException("Product Not Found"));

	    product.setName(productDto.getName());
	    product.setCategory(productDto.getCategory());
	    product.setDescription(productDto.getDescription());
	    product.setPrice(productDto.getPrice());
	    product.setQuantity(productDto.getQuantity());

	    productRepo.save(product);

	    return "Product Updated Successfully";
	}
	
	public String delProduct(int productId) {

	    if(!productRepo.existsById(productId)) {
	        return "Product Not Found";
	    }

	    productRepo.deleteById(productId);

	    return "Product Deleted Successfully";
	}

	public Vendors getVendor(String email) {
		return vendorRepo.findByEmail(email);
	}
}
