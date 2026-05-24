package in.rohanIT.ecommerce.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.rohanIT.ecommerce.entity.OrderList;

public interface OrderListRepo extends JpaRepository<OrderList, Integer>{

	List<OrderList> findByUserId(int userId);

	Optional<OrderList> findByUserIdAndProductId(int userId, int productId);

	int deleteByUserIdAndProductId(int userId, int productId);
}
