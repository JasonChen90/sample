package restful.restserver.model.util;

/**
 * Payment status
 * @author JasonChen1
 *
 */
public enum PSTATUS {

	/** 已支付*/
	P("P"),
	
	/** 已导入*/
	T("T"),
	
	/** 导入失败*/
	E("E");
	
	
	private String value;
	
	PSTATUS(String str){
		this.value = str;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
}
