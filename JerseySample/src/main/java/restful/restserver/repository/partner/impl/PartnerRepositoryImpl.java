package restful.restserver.repository.partner.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import restful.restserver.model.Partner;
import restful.restserver.repository.partner.PartnerRepository;
import restful.restserver.repository.util.AbstractRepository;

/**
 * @author JasonChen1
 */
@Configuration
public class PartnerRepositoryImpl extends AbstractRepository implements PartnerRepository {

	@Override
	public Partner getById(BigDecimal k) {
		return null;
	}

	/**
	 * merge related data with database
	 */
	@Override
	public boolean merge(final List<Partner> partners) {
		boolean result = true;
		result = this.transactionTemplate.execute(new TransactionCallback<Boolean>() {

			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				boolean result = true;
				try{
					//step 1: batch delete related data
					String delete = "DELETE FROM PARTNER WHERE PART_ID = :partId";
					SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(partners.toArray());
					namedParameterJdbcTemplate.batchUpdate(delete, batch);
					
					//step 2: batch insert sync data
					String insert = "INSERT INTO PARTNER(NAME) VALUES(:fullName)";
					namedParameterJdbcTemplate.batchUpdate(insert, batch);
				}catch(Exception ex){
					status.setRollbackOnly();
					result = false;
				}
				
				return result;
			}
		});
		return result;
	}
}
