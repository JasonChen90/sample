package restful.restserver.model;

public class Person {

	/** policy holder full name*/
	private String fullName;
	
	/** policy holder id card*/
	private String idNo;
	
	/** policy holder mobile number*/
	private String mobile;
	
	/** policy holder email*/
	private String email;
	
	/** province address of policy holder*/
	private String addrProvince;
	
	/** city address of policy holder*/
	private String addrCity;

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String phFullName) {
		this.fullName = phFullName;
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
	public void setIdNo(String phNationalId) {
		this.idNo = phNationalId;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String phMobile) {
		this.mobile = phMobile;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String phEmail) {
		this.email = phEmail;
	}

	/**
	 * @return the addrProvince
	 */
	public String getAddrProvince() {
		return addrProvince;
	}

	/**
	 * @param addrProvince the addrProvince to set
	 */
	public void setAddrProvince(String phAddrProvince) {
		this.addrProvince = phAddrProvince;
	}

	/**
	 * @return the addrCity
	 */
	public String getAddrCity() {
		return addrCity;
	}

	/**
	 * @param addrCity the addrCity to set
	 */
	public void setAddrCity(String phAddrCity) {
		this.addrCity = phAddrCity;
	}
}
