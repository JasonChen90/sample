package restful.restserver.repository.payment.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import restful.restserver.model.PaymentInfo;
import restful.restserver.repository.payment.PaymentRepository;
import restful.restserver.repository.util.AbstractRepository;
import restful.task.CallResult;
import restful.task.Payment;
import restful.task.PaymentResult;

/**
 * @author JasonChen1
 */
@Configuration
public class PaymentRepositoryImpl extends AbstractRepository implements PaymentRepository {

	/**
	 * search payment by payment id
	 */
	@Override
	public PaymentInfo getById(BigDecimal paymentId) {
		String sql = "SELECT * FROM TPAYMENT WHERE PAYMENT_ID = :PAYMENT_ID";
		Map<String,BigDecimal> paramMap = new HashMap<String,BigDecimal>();
		paramMap.put("PAYMENT_ID", paymentId);
		final PaymentInfo paymentInfo = new PaymentInfo();
		this.namedParameterJdbcTemplate.query(sql, paramMap, new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				paymentInfo.setPaymentId(rs.getBigDecimal("PAYMENT_ID"));
			}
		});
		return paymentInfo;
	}

	/**
	 * insert payment table
	 */
	@Override
	public boolean insertPayment(PaymentInfo paymentInfo) {
		boolean result = true;
		String sql = "INSERT INTO ";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("payment_id",paymentInfo.getPaymentId());
		try
		{
			this.namedParameterJdbcTemplate.update(sql, paramMap);
		}catch(Exception ex){
			result = false;
		}
		return result;
	}

	@Override
	public boolean paymentProcedure(final PaymentInfo paymentInfo) {
		boolean returnValue = true;
		returnValue = this.transactionTemplate.execute(new TransactionCallback<Boolean>() {
			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				boolean returnValue = true;
				Map<String, Object> resultMap = null;
				try{
					resultMap = execPayment(paymentInfo);
					int result = Integer.parseInt(resultMap.get("returnValue").toString());
					if(result != 0 && result != 2){
						status.setRollbackOnly();
						returnValue = false;
					}
				}catch(Exception ex){
					status.setRollbackOnly();
					returnValue = false;
				}
				return returnValue;
			}
		});
		return returnValue;
	}
	
	private Map<String,Object> execPayment(PaymentInfo paymentInfo){
		//声明function 参数、oracle声明顺序
		this.simpleJdbcCall.withCatalogName("packageName").withProcedureName("procedureName");
		this.simpleJdbcCall.addDeclaredParameter(new SqlParameter("pi_parm",Types.VARCHAR));
		this.simpleJdbcCall.withReturnValue();
		this.simpleJdbcCall.withoutProcedureColumnMetaDataAccess();
		
		//组装数据
		Map<String,Object> inParamsMap = new HashMap<String,Object>();
		inParamsMap.put("pi_parm",paymentInfo.getPaymentId());
		
		Map<String, Object> resultMap = this.simpleJdbcCall.execute(inParamsMap);
		return resultMap;
	}
	
	/**
	 * 
	 */
	@Override
	public PaymentInfo getByOrderId(String orderId) {
		String sql = "SELECT * FROM PAYMENT WHERE ORDER_ID = :ORDER_ID";
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("ORDER_ID", orderId);
		this.namedParameterJdbcTemplate.query(sql, paramMap, new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				while(rs.next()){
					
				}
			}
		});
		return null;
	}

	@Override
	public List<PaymentInfo> getByPayTime(Date payTime) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 *获取需要承保的记录，通过web接口发送给ADOS进行承保
	 */
	@Override
	public List<Payment> getInsurePayment() {
		final List<Payment> paymentInfos = new ArrayList<Payment>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM TABLE_NAME");
		this.namedParameterJdbcTemplate.query(sb.toString(), new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				Payment payment = new Payment();
				payment.setPaymentId(rs.getString("payment_id"));
				paymentInfos.add(payment);
			}
		});
		return paymentInfos;
	}

	@Override
	public boolean updatePaymentForInsure(final Payment payment,final CallResult callResult) {
		boolean result = true;
		result = this.transactionTemplate.execute(new TransactionCallback<Boolean>() {

			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				boolean result = true;
				try{
					//insure successful
					if(callResult.getResult() == 0){
						//after_issue
						execAfterInsure(payment);
					}else{
						StringBuilder sb = new StringBuilder();
						sb.append("UPDATE PAYMENT ");
						Map<String,Object> paramMap = new HashMap<String,Object>();
						paramMap.put("id", payment.getPaymentId());
						paramMap.put("errMsg", callResult.getErrMsg());
						namedParameterJdbcTemplate.update(sb.toString(), paramMap);
					}
					
				}catch(Exception ex){
					status.setRollbackOnly();
					result = false;
				}
				
				return result;
			}
		});
		return result;
	}
	
	/**
	 * procedure after_insure
	 * @param paymentInfo
	 */
	private Map<String, Object> execAfterInsure(Payment payment){
		this.simpleJdbcCall.withCatalogName("packageName").withProcedureName("procedureName");
		this.simpleJdbcCall.addDeclaredParameter(new SqlParameter("pi_payment_id",Types.VARCHAR));
		this.simpleJdbcCall.withoutProcedureColumnMetaDataAccess();
		
		//组装数据
		Map<String,Object> inParamsMap = new HashMap<String,Object>();
		inParamsMap.put("pi_payment_id",payment.getPaymentId());
		
		Map<String, Object> resultMap = this.simpleJdbcCall.execute(inParamsMap);
		return resultMap;
	}

	@Override
	public List<PaymentResult> getInsuredPaymentForUpdate() {
		final List<PaymentResult> paymentResults = new ArrayList<PaymentResult>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM PAYMENT pay");
		sb.append("WHERE pay.payment_status = 'T'");
		Map<String,?> paramMap = new HashMap<String,Object>();
		SqlParameterSource paramSource = new MapSqlParameterSource(paramMap);
		//this.namedParameterJdbcTemplate.queryForList(sb.toString(), paramSource, PaymentResult.class);
		return paymentResults;
	}

	@Override
	public boolean updatePaymentForInsuredUpdate(final PaymentResult paymentResult) {
		boolean result = true;
		result = this.transactionTemplate.execute(new TransactionCallback<Boolean>() {

			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				boolean result = true;
				try{
					execAfterInsured(paymentResult);
				}catch(Exception ex){
					status.setRollbackOnly();
					result = false;
				}
				
				return result;
			}
		});
		return result;
	}
	
	/**
	 * procedure after_issue
	 * @param paymentInfo
	 */
	private Map<String, Object> execAfterInsured(PaymentResult paymentResult){
		this.simpleJdbcCall.withCatalogName("packageName").withProcedureName("procedureName");
		this.simpleJdbcCall.addDeclaredParameter(new SqlParameter("pi_payment_id",Types.VARCHAR));
		this.simpleJdbcCall.withoutProcedureColumnMetaDataAccess();
		
		//组装数据
		Map<String,Object> inParamsMap = new HashMap<String,Object>();
		inParamsMap.put("pi_payment_id",paymentResult.getPaymentId());
		
		Map<String, Object> resultMap = this.simpleJdbcCall.execute(inParamsMap);
		return resultMap;
	}
}
