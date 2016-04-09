package restful.restserver.representation;

import java.math.BigDecimal;

/**
 * payment information representation
 * @author JasonChen1
 *
 */
public class PaymentRequest {

	/** pay id*/
	private BigDecimal paymentId;

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
}
