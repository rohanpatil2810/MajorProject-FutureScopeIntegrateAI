package in.rohanIT.ecommerce.services;

import java.time.LocalDateTime;
import java.security.SecureRandom;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import in.rohanIT.ecommerce.dto.PaymentDto;
import in.rohanIT.ecommerce.entity.CustomerOrder;
import in.rohanIT.ecommerce.entity.PaymentTable;
import in.rohanIT.ecommerce.repo.CustomerOrderRepo;
import in.rohanIT.ecommerce.repo.PaymentRepo;

@Service
public class PaymentService {

    @Autowired private PaymentRepo paymentRepo;
    @Autowired private CustomerOrderRepo custOrderRepo;

    @Value("${rezorpay.key.id}")
    private String rezorpayId;

    @Value("${rezorpay.key.secret}")
    private String rezorpaySecret;

    private RazorpayClient client;

    private static final int TEMP_ID_LENGTH = 12;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public PaymentDto createOrder(PaymentDto dto) throws RazorpayException {
        client = new RazorpayClient(rezorpayId, rezorpaySecret);

        JSONObject orderReq = new JSONObject();
        orderReq.put("amount", dto.getAmount() * 100);
        orderReq.put("currency", "INR");

        Order razorpayOrder = client.orders.create(orderReq);

        dto.setRezorpayID(razorpayOrder.get("id"));
        dto.setStatus(razorpayOrder.get("status"));
        dto.setTxnId(generateRandomTXNId());

        return dto;
    }

    @Transactional
    public String confirmPayment(String razorpayOrderId, String razorpayPaymentId, PaymentDto dto) {
        try {
            PaymentTable payment = new PaymentTable();
            payment.setRezorpayID(razorpayOrderId);
            payment.setTxnId(razorpayPaymentId);
            payment.setStatus("captured");
            payment.setAmount(dto.getAmount());
            payment.setPaymentType(dto.getPaymentType());
            payment.setTransactionDate(LocalDateTime.now());
            
            // Set User & OrderList - we'll improve this soon
            // For now, assuming you send userId and orderListId from frontend
            // payment.setUser(...);
            // payment.setOrderList(...);

            paymentRepo.save(payment);

            CustomerOrder customerOrder = new CustomerOrder();
            customerOrder.setOrderAmt(dto.getAmount());
            customerOrder.setOrderDate(LocalDateTime.now());
            customerOrder.setOrderStatus("Order Success");
            customerOrder.setUser(dto.getUser());
            customerOrder.setOrderList(dto.getOrderList());
            customerOrder.setPayment(payment);

            custOrderRepo.save(customerOrder);

            return "Payment Confirmed Successfully";

        } catch (Exception e) {
            e.printStackTrace();
            return "Payment Confirmation Failed";
        }
    }

    private String generateRandomTXNId() {
        SecureRandom sc = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < TEMP_ID_LENGTH; i++) {
            int index = sc.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }
}