package in.rohanIT.ecommerce.dto;

import java.time.LocalDateTime;

import in.rohanIT.ecommerce.entity.OrderList;
import in.rohanIT.ecommerce.entity.PaymentTable;
import in.rohanIT.ecommerce.entity.UserDetails;

public class CustomerOrderDto {

	private int id;
	private double orderAmt;
	private int quantity;
	private String orderStatus;
	private LocalDateTime orderDate;
	
	private UserDetails user;
	
	private OrderList orderList;
	
	private PaymentTable payment;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getOrderAmt() {
		return orderAmt;
	}

	public void setOrderAmt(double orderAmt) {
		this.orderAmt = orderAmt;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	
	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public UserDetails getUser() {
		return user;
	}

	public void setUser(UserDetails user) {
		this.user = user;
	}

	public OrderList getOrderList() {
		return orderList;
	}

	public void setOrderList(OrderList orderList) {
		this.orderList = orderList;
	}

	public PaymentTable getPayment() {
		return payment;
	}

	public void setPayment(PaymentTable payment) {
		this.payment = payment;
	}
	
}
