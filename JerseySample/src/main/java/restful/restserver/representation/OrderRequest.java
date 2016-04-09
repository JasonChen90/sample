package restful.restserver.representation;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import restful.restserver.core.util.CustomDateTimeDeserializer;
import restful.restserver.model.Person;

/**
 * Order Request
 * @author JasonChen1
 *
 */
public class OrderRequest {

	/** order id*/
	private String orderId;
	
	/** generated time of order*/
	private Date orderTime;
	
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
	 * @return the orderTime
	 */
	public Date getOrderTime() {
		return orderTime;
	}

	/**
	 * @param orderTime the orderTime to set
	 */
	@JsonDeserialize(using = CustomDateTimeDeserializer.class)
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
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
