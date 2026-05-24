package in.rohanIT.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.rohanIT.ecommerce.entity.Vendors;

public interface VendorRepo extends JpaRepository<Vendors, Integer>{

	Vendors findByEmail(String email);
	Vendors findByContactNo(String contact);
}
