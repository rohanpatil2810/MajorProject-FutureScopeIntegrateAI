package in.rohanIT.ecommerce.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.rohanIT.ecommerce.entity.CustomerOrder;

public interface CustomerOrderRepo extends JpaRepository<CustomerOrder, Integer> {

	List<CustomerOrder> findByUserId(int userId);
	
    List<CustomerOrder> findByOrderDateBetween(
            LocalDateTime fromDate,
            LocalDateTime toDate
    );
}