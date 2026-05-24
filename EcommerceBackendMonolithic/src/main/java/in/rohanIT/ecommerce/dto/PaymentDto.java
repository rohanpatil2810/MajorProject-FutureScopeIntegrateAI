package in.rohanIT.ecommerce.dto;

import java.sql.Date;
import java.time.LocalDateTime;

import in.rohanIT.ecommerce.entity.OrderList;
import in.rohanIT.ecommerce.entity.UserDetails;

public class PaymentDto {

	private int id;
	private LocalDateTime transactionDate;
	private String txnId;
	private String paymentType;
	private double amount;
	private String status;
	private String rezorpayID;
	
	private OrderList orderList;
	
	private UserDetails user;
	
	
	
	
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
	
	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getTxnId() {
		return txnId;
	}
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRezorpayID() {
		return rezorpayID;
	}
	public void setRezorpayID(String rezorpayID) {
		this.rezorpayID = rezorpayID;
	}
	
	
}
