package restful.restserver.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Payment information
 * @author JasonChen1
 *
 */
public class PaymentInfo {

	/** pay id*/
	private BigDecimal paymentId;
	
	/** order id*/
	private String orderId;
	
	/** amount of payment*/
	private BigDecimal payAmount;
	
	/** time of payment*/
	private Date payTime;

	/**
	 * @return the paymentId
	 */
	public BigDecimal getPaymentId() {
		return paymentId;
	}

	/**
	 * @param paymentId the paymentId to set
	 */
	public void setPaymentId(BigDecimal paymentId) {
		this.paymentId = paymentId;
	}

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
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
	public Date getPayTime() {
		return payTime;
	}

	/**
	 * @param payTime the payTime to set
	 */
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
}
