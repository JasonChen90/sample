package model;

public class PaymentResult {

private String paymentId;
	
	private String policyRef;
	
	private String termStartDate;
	
	private String termEndDate;

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
	 * @return the policyRef
	 */
	public String getPolicyRef() {
		return policyRef;
	}

	/**
	 * @param policyRef the policyRef to set
	 */
	public void setPolicyRef(String policyRef) {
		this.policyRef = policyRef;
	}

	/**
	 * @return the termStartDate
	 */
	public String getTermStartDate() {
		return termStartDate;
	}

	/**
	 * @param termStartDate the termStartDate to set
	 */
	public void setTermStartDate(String termStartDate) {
		this.termStartDate = termStartDate;
	}

	/**
	 * @return the termEndDate
	 */
	public String getTermEndDate() {
		return termEndDate;
	}

	/**
	 * @param termEndDate the termEndDate to set
	 */
	public void setTermEndDate(String termEndDate) {
		this.termEndDate = termEndDate;
	}
}
