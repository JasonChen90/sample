package model;

import java.math.BigDecimal;

/**
 * @author JasonChen1
 *
 */
public class Partner {

	private BigDecimal partId;
	
	private String fullName;
	
	private String idNo;

	/**
	 * @return the partId
	 */
	public BigDecimal getPartId() {
		return partId;
	}

	/**
	 * @param partId the partId to set
	 */
	public void setPartId(BigDecimal partId) {
		this.partId = partId;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return the idNo
	 */
	public String getIdNo() {
		return idNo;
	}

	/**
	 * @param idNo the idNo to set
	 */
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
}
