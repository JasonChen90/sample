package restful.restserver.model;

public class Product {

	/** combined product code*/
	private String productId;
	
	/** combined product name*/
	private String productName;

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String skuCode) {
		this.productId = skuCode;
	}

	/**
	 * @return the skuName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param skuName the skuName to set
	 */
	public void setProductName(String skuName) {
		this.productName = skuName;
	}
}
