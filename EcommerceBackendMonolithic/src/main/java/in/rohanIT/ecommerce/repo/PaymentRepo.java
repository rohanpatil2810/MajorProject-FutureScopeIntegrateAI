package in.rohanIT.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.rohanIT.ecommerce.entity.PaymentTable;

public interface PaymentRepo extends JpaRepository<PaymentTable, Integer>{

}

