package restful.restserver.repository.pcb.impl;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import restful.restserver.model.PBC;
import restful.restserver.repository.pcb.PCBRepository;
import restful.restserver.repository.util.AbstractRepository;

@Configuration
public class PCBRepositoryImpl extends AbstractRepository implements PCBRepository {

	@Override
	public PBC getById(BigDecimal k) {
		return null;
	}

	@Override
	public boolean merge(final List<PBC> pBCs) {
		boolean result = true;
		result = this.transactionTemplate.execute(new TransactionCallback<Boolean>() {

			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				boolean result = true;
				try{
					//step 1: batch delete related data
					String delete = "";
					SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(pBCs.toArray());
					namedParameterJdbcTemplate.batchUpdate(delete, batch);
					
					//step 2: batch insert related data
					String insert = "";
					namedParameterJdbcTemplate.batchUpdate(insert, batch);
					
					execPayment(pBCs);
				}catch(Exception ex){
					status.setRollbackOnly();
					result = false;
				}
				
				return result;
			}
		});
		return result;
	}
	
	private void execPayment(List<PBC> pBCs){
		for(PBC p : pBCs){
			if(p.getPaymentId() != null && p.getPaymentId() != ""){
				//声明function 参数、oracle声明顺序
				this.simpleJdbcCall.withCatalogName("packageName").withProcedureName("procedureName");
				this.simpleJdbcCall.addDeclaredParameter(new SqlParameter("pi_payment_id",Types.VARCHAR));
				this.simpleJdbcCall.withReturnValue();
				this.simpleJdbcCall.withoutProcedureColumnMetaDataAccess();
				
				//组装数据
				Map<String,Object> inParamsMap = new HashMap<String,Object>();
				inParamsMap.put("pi_payment_id",p.getPaymentId());
				
				this.simpleJdbcCall.execute(inParamsMap);
			}
		}
	}

}
