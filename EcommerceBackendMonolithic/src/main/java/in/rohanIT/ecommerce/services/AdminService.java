package in.rohanIT.ecommerce.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.rohanIT.ecommerce.dto.AdminDto;
import in.rohanIT.ecommerce.entity.Admin;
import in.rohanIT.ecommerce.entity.CustomerOrder;
import in.rohanIT.ecommerce.entity.Product;
import in.rohanIT.ecommerce.entity.Vendors;
import in.rohanIT.ecommerce.repo.AdminRepo;
import in.rohanIT.ecommerce.repo.CustomerOrderRepo;
import in.rohanIT.ecommerce.repo.ProductRepo;
import in.rohanIT.ecommerce.repo.VendorRepo;

@Service
public class AdminService {

	@Autowired
	private AdminRepo adminRepo;
	
	@Autowired
	private CustomerOrderRepo custOrderRepo;
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private VendorRepo vendorRepo;
	
	public Admin login(AdminDto adminDto) {

	    Admin admin = adminRepo.findByEmail(adminDto.getEmail());

	    if (admin == null) {
	        throw new RuntimeException("Admin not found");
	    }

	    if (!admin.getPw().equals(adminDto.getPw())) {
	        throw new RuntimeException("Invalid Password");
	    }

	    return admin;
	}
	
	public Admin saveAdmin(AdminDto adminDto) {
		Admin admin=new Admin();
		admin.setContactNo(adminDto.getContactNo());
		admin.setEmail(adminDto.getEmail());
		admin.setFullName(adminDto.getFullName());
		admin.setPw(adminDto.getPw());
		admin.setRole("Admin");
		return adminRepo.save(admin);
	}
	
	public List<CustomerOrder> getAllOrders(){
		return custOrderRepo.findAll();
	}
	
	public List<Product> getAllProducts(){
		return productRepo.findAll();
	}
	
	public List<Vendors> getAllVendors(){
		return vendorRepo.findAll();
	}
	
	public List<CustomerOrder> getSortedOrders(
	        LocalDateTime fromDate,
	        LocalDateTime toDate){

	    return custOrderRepo.findByOrderDateBetween(fromDate, toDate);
	}
}
