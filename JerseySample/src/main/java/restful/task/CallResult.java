package restful.task;

public class CallResult {

	private long Result;
	
	private String ErrMsg;

	/**
	 * @return the Result
	 */
	public long getResult() {
		return Result;
	}

	/**
	 * @param Result the Result to set
	 */
	public void setResult(long result) {
		this.Result = result;
	}

	/**
	 * @return the ErrMsg
	 */
	public String getErrMsg() {
		return ErrMsg;
	}

	/**
	 * @param ErrMsg the ErrMsg to set
	 */
	public void setErrMsg(String errMsg) {
		this.ErrMsg = errMsg;
	}
}
