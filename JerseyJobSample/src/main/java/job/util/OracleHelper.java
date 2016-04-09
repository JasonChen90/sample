package job.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import conf.PropConfig;

/**
 * connection pool oracle helper
 * @author JasonChen1
 *
 */
public class OracleHelper {

	static Logger logger = Logger.getLogger(OracleHelper.class);
	
	static DataSource dataSource = new DataSource();
	
	static{
		PoolProperties poolProperties = new PoolProperties();
		try{
			poolProperties.setUrl(PropConfig.getValueFromProps("DB_CONNECTION"));
			poolProperties.setDriverClassName(PropConfig.getValueFromProps("DB_DRIVER"));
			poolProperties.setUsername(PropConfig.getValueFromProps("USER_NAME"));
			poolProperties.setPassword(PropConfig.getValueFromProps("USER_PWD"));
			poolProperties.setInitialSize(Integer.parseInt(PropConfig.getValueFromProps("INITIAL_SIZE")));
			poolProperties.setMaxActive(Integer.parseInt(PropConfig.getValueFromProps("MAX_ACTIVE")));
			poolProperties.setMaxIdle(Integer.parseInt(PropConfig.getValueFromProps("MAX_IDLE")));
			dataSource.setPoolProperties(poolProperties);
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}
	}
	
	private OracleHelper(){
		super();
	}
	
	/**
	 * get the database connection
	 * @return
	 */
	public static final Connection getDBConnection(){
		Connection con = null;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return con;
	}
	
	/**
	 * close the database connection
	 * @param con
	 */
	public static void closeDBConnection(Connection con){
		try {
			if(con != null && !con.isClosed()){
				con.close();
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}
}
