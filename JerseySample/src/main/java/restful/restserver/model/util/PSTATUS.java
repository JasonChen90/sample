package restful.restserver.model.util;

/**
 * Payment status
 * @author JasonChen1
 *
 */
public enum PSTATUS {

	/** ��֧��*/
	P("P"),
	
	/** �ѵ���*/
	T("T"),
	
	/** ����ʧ��*/
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
