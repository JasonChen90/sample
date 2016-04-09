package restful.restserver.repository.util;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author JasonChen1
 */
@Repository
public abstract class AbstractRepository {

	public NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	/**
	 * @return the namedParameterJdbcTemplate
	 */
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return namedParameterJdbcTemplate;
	}

	public TransactionTemplate transactionTemplate;
	
	public SimpleJdbcCall simpleJdbcCall;
	
	@Autowired
	public void setNamedParameterJdbcTemplate(DataSource dataSource){
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Autowired
	public void setTransactionTemplate(TransactionTemplate transactionTemplate){
		this.transactionTemplate = transactionTemplate;
	}
	
	@Autowired
	public void setSimpleJdbcCall(DataSource dataSource){
		//jdbcTemplate.setResultsMapCaseInsensitive(true);
		this.simpleJdbcCall = new SimpleJdbcCall(dataSource);
	}
}
