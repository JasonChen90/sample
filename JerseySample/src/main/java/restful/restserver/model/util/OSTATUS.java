package restful.restserver.model.util;

/**
 * Order Status
 * @author JasonChen1
 *
 */
public enum OSTATUS {

	/** 前*/
	K("K"),
	
	/** 不通过*/
	I("I"),
	
	/** 通过*/
	U("U"),
	
	/** 过期*/
	E("E"),
	
	/** 已支付*/
	P("P"),
	

	T("T");
	
	private String value;
	
	private OSTATUS(String str){
		this.value = str;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
	@Override
	public String toString(){
		return value;
	}
}
