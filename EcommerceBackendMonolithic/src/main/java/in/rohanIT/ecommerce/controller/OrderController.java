package in.rohanIT.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.rohanIT.ecommerce.dto.OrderListDto;
import in.rohanIT.ecommerce.entity.OrderList;
import in.rohanIT.ecommerce.services.OrderListService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderListService service;

    @PostMapping("/add")
    public String addToCart(@RequestBody OrderListDto dto) {
        return service.addToCart(dto);
    }

    @GetMapping("/cart/{userId}")
    public List<OrderList> getCart(@PathVariable int userId) {
        return service.getCart(userId);
    }

    @DeleteMapping("/remove")
    public String remove(@RequestParam int userId,
                         @RequestParam int productId) {
        return service.removeFromCart(userId, productId);
    }

    @PutMapping("/update")
    public String update(@RequestBody OrderListDto dto) {
        return service.updateCart(dto);
    }
}