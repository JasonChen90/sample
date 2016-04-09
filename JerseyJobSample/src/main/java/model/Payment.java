package model;

import java.math.BigDecimal;

public class Payment {
	private String paymentId;
	private BigDecimal payAmount;
	private String payTime;
	
	/**
	 * @return the paymentId
	 */
	public String getPaymentId() {
		return paymentId;
	}
	/**
	 * @param paymentId the paymentId to set
	 */
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	/**
	 * @return the payAmount
	 */
	public BigDecimal getPayAmount() {
		return payAmount;
	}
	/**
	 * @param payAmount the payAmount to set
	 */
	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}
	/**
	 * @return the payTime
	 */
	public String getPayTime() {
		return payTime;
	}
	/**
	 * @param payTime the payTime to set
	 */
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
}
