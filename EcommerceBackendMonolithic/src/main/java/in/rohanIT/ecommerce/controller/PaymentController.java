package in.rohanIT.ecommerce.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.RazorpayException;

import in.rohanIT.ecommerce.dto.PaymentDto;
import in.rohanIT.ecommerce.entity.UserDetails;
import in.rohanIT.ecommerce.services.PaymentService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create-order")
    public ResponseEntity<PaymentDto> createOrder(@RequestBody PaymentDto payment) throws RazorpayException {
        PaymentDto response = paymentService.createOrder(payment);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/confirm-payment")
    public String confirmPayment(@RequestBody Map<String, Object> data) {
        try {
            String razorpayOrderId = (String) data.get("razorpayOrderId");
            String razorpayPaymentId = (String) data.get("razorpayPaymentId");

            PaymentDto dto = new PaymentDto();
            dto.setAmount(Double.parseDouble(data.get("amount").toString()));
            dto.setPaymentType((String) data.get("paymentType"));

            // Extract User and OrderList from frontend
            @SuppressWarnings("unchecked")
            Map<String, Object> userMap = (Map<String, Object>) data.get("user");
            
            // For now, we set basic user info (you can improve later)
            UserDetails user = new UserDetails();
            user.setId(Integer.parseInt(userMap.get("id").toString()));
            user.setUserName((String) userMap.get("userName"));
            user.setEmailID((String) userMap.get("emailID"));
            dto.setUser(user);

            return paymentService.confirmPayment(razorpayOrderId, razorpayPaymentId, dto);

        } catch (Exception e) {
            e.printStackTrace();
            return "Payment Confirmation Failed";
        }
    }
}