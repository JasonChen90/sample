package restful.restserver.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import restful.restserver.core.util.CustomDateDeserializer;

public class PBC {

	private String paymentId;
	
	private Date startDate;
	
	private Date endDate;

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
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	@JsonDeserialize(using = CustomDateDeserializer.class)
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	@JsonDeserialize(using = CustomDateDeserializer.class)
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
