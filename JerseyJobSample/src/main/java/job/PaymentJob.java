package job;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import conf.PropConfig;
import job.util.OracleHelper;
import model.BaseResponse;
import model.Payment;
import model.PaymentResult;
import model.SignRequest;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import task.util.MD5Util;
import task.util.MyHttpRequest;

public class PaymentJob {

	private static Logger logger = Logger.getLogger(PaymentJob.class);
	
	/** initialize data*/
	private Gson gson;
	private MyHttpRequest httpRequest;
	private SignRequest signRequest;
	private SimpleDateFormat sdf;
	
	private String KEY;
	
	public PaymentJob(){
		gson = new GsonBuilder().create();
		httpRequest = new MyHttpRequest();
		KEY = PropConfig.getValueFromProps("MD5KEY");
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
	}
	
	public void exec(){
		
		StringEntity stringEntity = new StringEntity(null, ContentType.create("application/json", "UTF-8"));
		String paymentInsureUrl = PropConfig.getValueFromProps("PAYMENT_INSURE_URL");
		List<Payment> payments = httpRequest.jsonRequest2(stringEntity, paymentInsureUrl, Payment.class);
		
		String paymentInsuredUrl = PropConfig.getValueFromProps("PAYMENT_INSURED_URL");
		for(Payment payment : payments){
		
			//数据库处理订单
			PaymentResult paymentResult= null;
			if(this.processPayment(payment)){
				paymentResult = new PaymentResult();
				paymentResult.setPaymentId(payment.getPaymentId());
			}else{
				continue;
			}
			
			signRequest = new SignRequest();
			signRequest.setBody(gson.toJson(paymentResult));
			signRequest.setSign(MD5Util.MD5(signRequest.getBody() + this.KEY));
			String json = gson.toJson(signRequest);
			stringEntity = new StringEntity(json, ContentType.create("application/json", "UTF-8"));
			BaseResponse baseResponse = httpRequest.jsonRequest(stringEntity, paymentInsuredUrl, BaseResponse.class);
		}
	}
	
	/**
	 * 
	 * @param payment
	 * @return
	 */
	private boolean processPayment(Payment payment){
		Connection connection  = null;
		CallableStatement callableStatement = null;
		OracleCallableStatement stat;
		StringBuilder sb = new StringBuilder();
		sb.append("{ ? = call packageName.functionName(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
		try {
			connection = OracleHelper.getDBConnection();
			callableStatement = connection.prepareCall(sb.toString());
			callableStatement.setString(2, payment.getPaymentId());
			callableStatement.setBigDecimal(3, payment.getPayAmount());
			callableStatement.setDate(4, new Date(sdf.parse(payment.getPayTime()).getTime()));
			callableStatement.registerOutParameter(20, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(1, OracleTypes.INTEGER);
			callableStatement.execute();
			int retValue = callableStatement.getInt(1);
			if(retValue == 0){
				return true;
			}
		} catch(ParseException e){
			logger.error(e.getMessage());
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally{
			OracleHelper.closeDBConnection(connection);
		}
		return false;
	}
}
