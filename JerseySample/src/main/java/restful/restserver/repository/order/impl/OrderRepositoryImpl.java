package restful.restserver.repository.order.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import restful.restserver.model.Order;
import restful.restserver.model.Person;
import restful.restserver.repository.order.OrderRepository;
import restful.restserver.repository.util.AbstractRepository;

/**
 * The implement of OrderRepository
 * @author JasonChen1
 *
 */
@Configuration
public class OrderRepositoryImpl extends AbstractRepository implements OrderRepository {

	/**
	 * get order by id
	 */
	@Override
	public Order getById(String orderId) {
		
		String sql = "SELECT * FROM ORDER T WHERE T.ORDER_ID = :ORDER_ID";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("ORDER_ID", orderId);
		final Order order = new Order();
		this.namedParameterJdbcTemplate.query(sql, paramMap, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
					order.setOrderId(rs.getString("ORDER_ID"));
					Person person = new Person();
					person.setFullName(rs.getString("NAME"));
					order.setPolicyHolder(person);
				}
		});
		
		//this.transactionTemplate.setTimeout(3000);
		//this.transactionTemplate.setIsolationLevel(2);
		return order;
	}
	
	/**
	 * insert to order table
	 */
	@Override
	public boolean insertOrder(Order order) {
		boolean result = true;
		try{
			String sql = "INSERT INTO ";
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("ORDER_ID",order.getOrderId());
			int count = this.namedParameterJdbcTemplate.update(sql, paramMap);
			System.out.println("count:" + count);
		}catch(Exception ex){
			result = false;
		}
		
		return result;
	}

	/**
	 * execute pre_bbu in transaction
	 */
	@Override
	public Map<String, Object> execute(final Order order) {
		Map<String, Object> resultMap = this.transactionTemplate.execute(new TransactionCallback<Map<String, Object>>() {
			@Override
			public Map<String, Object> doInTransaction(TransactionStatus status) {
				Map<String, Object> resultMap = null;
				try{
					resultMap = subExecute(order);
					int result = Integer.parseInt(resultMap.get("returnValue").toString());
					// 0:successful 2:issue error
					if(result != 0 && result != 2){
						status.setRollbackOnly();
					}
				}catch(Exception ex){
					status.setRollbackOnly();
				}
				return resultMap;
			}
		});
		return resultMap;
	}
	
	/**
	 * 
	 * @param order
	 * @return
	 */
	private Map<String,Object> subExecute(Order order){
		//声明function 参数、oracle声明顺序
		this.simpleJdbcCall.withCatalogName("packageName").withFunctionName("functionName");
		this.simpleJdbcCall.addDeclaredParameter(new SqlOutParameter("returnValue",Types.DECIMAL));
		this.simpleJdbcCall.addDeclaredParameter(new SqlParameter("pi_parm",Types.VARCHAR));
		this.simpleJdbcCall.addDeclaredParameter(new SqlOutParameter("errmsg",Types.VARCHAR));
		this.simpleJdbcCall.withReturnValue();
		this.simpleJdbcCall.withoutProcedureColumnMetaDataAccess();
		
		//组装数据
		Map<String,Object> inParamsMap = new HashMap<String,Object>();
		inParamsMap.put("pi_parm",order.getOrderId());
		
		Map<String, Object> resultMap = this.simpleJdbcCall.execute(inParamsMap);
		return resultMap;
	}
}
