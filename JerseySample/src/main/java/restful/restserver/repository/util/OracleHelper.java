package restful.restserver.repository.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OracleHelper {

	/** database driver*/
	private static final String DB_DRIVER = "org.postgresql.Driver";
	/** database connection string*/
	private static final String DB_CONNECTION = "jdbc:oracle:thin:@localhost:1521:databaseName";
	/** database connection user name*/
	private static final String DB_USER = "userName";
	/** database connection password*/
	private static final String DB_PASSWORD = "password";
	
	/**
	 * Execute select statement
	 * @return
	 */
	public ResultSet ExecuteReader(PreparedStatement preparedStatement,String sql){
		ResultSet rt = null;
		if(preparedStatement != null){
			try {
				rt = preparedStatement.executeQuery(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				try {
					if(preparedStatement != null){
						preparedStatement.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return rt;
	}
	
	/**
	 * Execute update,insert,delete
	 * @param preparedStatement
	 * @param sql
	 * @return
	 */
	public int ExecuteNonQuery(PreparedStatement preparedStatement,String sql){
		int result = 0;
		if(preparedStatement != null){
			try {
				result = preparedStatement.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				try {
					if(preparedStatement != null){
						preparedStatement.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	/**
	 * Execute oracle procedure
	 * @param callableStatement
	 * @param sql
	 * @return
	 */
	public boolean ExecuteOracleProcedure(CallableStatement callableStatement,String sql){
		boolean result = true;
		if(callableStatement != null){
			try {
				callableStatement.executeUpdate(sql);
			} catch (SQLException e) {
				result = false;
				e.printStackTrace();
			}finally{
				if(callableStatement != null){
					try {
						callableStatement.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * new create database connection
	 * @return
	 */
	public Connection getConnection(){
		Connection connection = null;
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection(DB_CONNECTION,DB_USER,DB_PASSWORD);
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	/**
	 * close database connection
	 * @param connection
	 */
	public void closeConnection(Connection connection){
		if(connection != null){
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
}
