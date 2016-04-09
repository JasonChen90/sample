package restful.restserver.model.util;

/**
 * Order Status
 * @author JasonChen1
 *
 */
public enum OSTATUS {

	/** ǰ*/
	K("K"),
	
	/** ��ͨ��*/
	I("I"),
	
	/** ͨ��*/
	U("U"),
	
	/** ����*/
	E("E"),
	
	/** ��֧��*/
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
