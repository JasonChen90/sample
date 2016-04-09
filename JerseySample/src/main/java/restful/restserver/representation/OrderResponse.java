package restful.restserver.representation;

import restful.restserver.model.Person;

public class OrderResponse {

	/** order id*/
	private String orderId;
	
	/** status of order*/
	private String status;
	
	/** policy holder*/
	private Person policyHolder;

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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the policyHolder
	 */
	public Person getPolicyHolder() {
		return policyHolder;
	}

	/**
	 * @param policyHolder the policyHolder to set
	 */
	public void setPolicyHolder(Person policyHolder) {
		this.policyHolder = policyHolder;
	}
}
