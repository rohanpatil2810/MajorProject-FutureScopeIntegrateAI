package in.rohanIT.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.rohanIT.ecommerce.entity.Admin;

public interface AdminRepo extends JpaRepository<Admin, Integer>{

	Admin findByEmail(String email);
}
