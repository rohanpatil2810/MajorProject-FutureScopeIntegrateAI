package in.rohanIT.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.rohanIT.ecommerce.entity.UserDetails;

public interface UserRepo extends JpaRepository<UserDetails, Integer>{

	UserDetails findByEmailID(String emailId);
	UserDetails findByUserContact(String contact);
	
}
