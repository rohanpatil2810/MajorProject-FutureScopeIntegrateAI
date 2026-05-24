package in.rohanIT.ecommerce.entity;

import java.sql.Date;
import java.time.LocalDateTime;

//package in.rohanIT.rezorpay.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Payment_Table")
public class PaymentTable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private LocalDateTime transactionDate;
	private String txnId;
	private String paymentType;
	private double amount;
	private String status;
	private String rezorpayID;
	
	@OneToOne
	@JoinColumn(name="orderList")
	private OrderList orderList;
	
	@ManyToOne
	@JoinColumn(name="userId")
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
