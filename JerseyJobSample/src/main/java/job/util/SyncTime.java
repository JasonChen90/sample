package job.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

/**
 * 
 * @author JasonChen1
 *
 */
public class SyncTime {
	private static Logger logger = Logger.getLogger(SyncTime.class);
	
	private Timestamp syncTime = null;

	public void searchSyncTime(){
		Connection connection  = null;
		PreparedStatement preparedStatement = null;
		StringBuilder sb = new StringBuilder();
		sb.append("select sysdate this_sync_time");
		sb.append("  from dual");
		try {
			connection = OracleHelper.getDBConnection();
			preparedStatement = connection.prepareStatement(sb.toString());
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()){
				syncTime = rs.getTimestamp("this_sync_time");
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally{
			OracleHelper.closeDBConnection(connection);
		}
	}
	
	public void updateSyncTime(){
		if(syncTime != null){
			Connection connection  = null;
			CallableStatement callableStatement = null;
			StringBuilder sb = new StringBuilder();
			sb.append("{call packageName.functionName(TO_CHAR(?,'YYYY-MM-DD HH24:MI:SS'))}");
			try {
				connection = OracleHelper.getDBConnection();
				callableStatement = connection.prepareCall(sb.toString());
				callableStatement.setTimestamp(1, syncTime);
				callableStatement.executeUpdate();
			} catch (SQLException e) {
				logger.error(e.getMessage());
			} finally{
				OracleHelper.closeDBConnection(connection);
			}
		}
	}
}
