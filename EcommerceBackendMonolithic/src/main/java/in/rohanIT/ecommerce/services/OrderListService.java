//package in.rohanIT.ecommerce.services;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import in.rohanIT.ecommerce.dto.OrderListDto;
//import in.rohanIT.ecommerce.entity.OrderList;
//import in.rohanIT.ecommerce.entity.Product;
//import in.rohanIT.ecommerce.entity.UserDetails;
//import in.rohanIT.ecommerce.repo.OrderListRepo;
//import in.rohanIT.ecommerce.repo.ProductRepo;
//import in.rohanIT.ecommerce.repo.UserRepo;
//import jakarta.transaction.Transactional;
//
//@Service
//@Transactional
//public class OrderListService {
//
//    @Autowired
//    private OrderListRepo orderListRepo;
//
//    @Autowired
//    private ProductRepo productRepo;
//
//    @Autowired
//    private UserRepo userRepo;
//
//    // ✅ ADD TO CART
//    public String addToCart(OrderListDto dto) {
//
//        Optional<UserDetails> userOpt = userRepo.findById(dto.getUserId());
//        if (userOpt.isEmpty()) return "User not found";
//
//        Optional<Product> productOpt = productRepo.findById(dto.getProductId());
//        if (productOpt.isEmpty()) return "Product not found";
//
//        Product product = productOpt.get();
//
//        if (product.getQuantity() < dto.getQuantity()) {
//            return "Only " + product.getQuantity() + " items available";
//        }
//
//        // Check if already in cart
//        Optional<OrderList> existing =
//                orderListRepo.findByUserIdAndProductId(dto.getUserId(), dto.getProductId());
//
//        if (existing.isPresent()) {
//            OrderList order = existing.get();
//            order.setProductQuantity(order.getProductQuantity() + dto.getQuantity());
//            orderListRepo.save(order);
//            return "Cart updated";
//        }
//
//        OrderList order = new OrderList();
//        order.setUser(userOpt.get());
//        order.setProduct(product);
//        order.setProductQuantity(dto.getQuantity());
//
//        orderListRepo.save(order);
//        return "Product added to cart";
//    }
//
//    // ✅ GET CART
//    public List<OrderList> getCart(int userId) {
//        return orderListRepo.findByUserId(userId);
//    }
//
//    // ✅ REMOVE ITEM
// // ✅ REMOVE ITEM - FIXED
//    public String removeFromCart(int userId, int productId) {
//        try {
//            int deleted = orderListRepo.deleteByUserIdAndProductId(userId, productId);
//            return deleted > 0 ? "Item removed from cart" : "Item not found in cart";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Error removing item";
//        }
//    }
//
//    public String updateCart(OrderListDto dto) {
//        Optional<OrderList> existing = 
//            orderListRepo.findByUserIdAndProductId(dto.getUserId(), dto.getProductId());
//        
//        if (existing.isEmpty()) return "Item not found";
//
//        OrderList order = existing.get();
//
//        if (dto.getQuantity() <= 0) {
//            orderListRepo.deleteByUserIdAndProductId(dto.getUserId(), dto.getProductId());
//            return "Item removed";
//        }
//
//        order.setProductQuantity(dto.getQuantity());
//        orderListRepo.save(order);
//        return "Cart updated";
//    }
//}

package in.rohanIT.ecommerce.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.rohanIT.ecommerce.dto.OrderListDto;
import in.rohanIT.ecommerce.entity.OrderList;
import in.rohanIT.ecommerce.entity.Product;
import in.rohanIT.ecommerce.entity.UserDetails;
import in.rohanIT.ecommerce.repo.OrderListRepo;
import in.rohanIT.ecommerce.repo.ProductRepo;
import in.rohanIT.ecommerce.repo.UserRepo;

@Service
@Transactional   // ← THIS IS IMPORTANT
public class OrderListService {

    @Autowired private OrderListRepo orderListRepo;
    @Autowired private ProductRepo productRepo;
    @Autowired private UserRepo userRepo;

    public String addToCart(OrderListDto dto) {
        // ... your existing code (keep as is)
        Optional<UserDetails> userOpt = userRepo.findById(dto.getUserId());
        if (userOpt.isEmpty()) return "User not found";
        Optional<Product> productOpt = productRepo.findById(dto.getProductId());
        if (productOpt.isEmpty()) return "Product not found";
        Product product = productOpt.get();
        if (product.getQuantity() < dto.getQuantity()) {
            return "Only " + product.getQuantity() + " items available";
        }

        Optional<OrderList> existing = orderListRepo.findByUserIdAndProductId(dto.getUserId(), dto.getProductId());
        if (existing.isPresent()) {
            OrderList order = existing.get();
            order.setProductQuantity(order.getProductQuantity() + dto.getQuantity());
            orderListRepo.save(order);
            return "Cart updated";
        }

        OrderList order = new OrderList();
        order.setUser(userOpt.get());
        order.setProduct(product);
        order.setProductQuantity(dto.getQuantity());
        orderListRepo.save(order);
        return "Product added to cart";
    }

    public List<OrderList> getCart(int userId) {
        return orderListRepo.findByUserId(userId);
    }

    public String removeFromCart(int userId, int productId) {
        int deleted = orderListRepo.deleteByUserIdAndProductId(userId, productId);
        return deleted > 0 ? "Item removed from cart" : "Item not found in cart";
    }

    public String updateCart(OrderListDto dto) {
        Optional<OrderList> existing = orderListRepo.findByUserIdAndProductId(dto.getUserId(), dto.getProductId());
        if (existing.isEmpty()) return "Item not found";

        OrderList order = existing.get();

        if (dto.getQuantity() <= 0) {
            orderListRepo.deleteByUserIdAndProductId(dto.getUserId(), dto.getProductId());
            return "Item removed";
        }

        order.setProductQuantity(dto.getQuantity());
        orderListRepo.save(order);
        return "Cart updated";
    }
}