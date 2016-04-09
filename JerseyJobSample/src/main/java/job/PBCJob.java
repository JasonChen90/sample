package job;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import conf.PropConfig;
import job.util.OracleHelper;
import model.BaseResponse;
import model.PCB;
import model.SignRequest;
import task.util.MD5Util;
import task.util.MyHttpRequest;

/**
 * 
 * @author JasonChen1
 *
 */
public class PBCJob {

	private static Logger logger = Logger.getLogger(PBCJob.class);
	
	/** initialize data*/
	private Gson gson;
	private GsonBuilder gsonBuilder;
	private MyHttpRequest httpRequest;
	private SignRequest signRequest;
	
	private String KEY;
	private int SYNC_SCALE;
	
	public PBCJob(){
		gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat("yyyy-MM-dd");
		gson = gsonBuilder.create();
		httpRequest = new MyHttpRequest();
		KEY = PropConfig.getValueFromProps("MD5KEY");
		SYNC_SCALE = Integer.parseInt(PropConfig.getValueFromProps("SYNC_SCALE"));
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean exec(){
		boolean returnValue = true;
		int count = 0;
		int toIndex = 0;
		try{
			List<PCB> pCBs = this.getSyncData();
			count = SYNC_SCALE == 0 ? 10 : pCBs.size() / SYNC_SCALE + 1;
			for(int i = 0; i < count; i++){
				if(i < count - 1){
					toIndex = i * SYNC_SCALE + SYNC_SCALE;
					
				}else{
					toIndex = pCBs.size();
				}
				List<PCB> subPolicyCoverBenefits = pCBs.subList(i * SYNC_SCALE, toIndex);
				if(subPolicyCoverBenefits.size() > 0){
					signRequest = new SignRequest();
					signRequest.setBody(gson.toJson(subPolicyCoverBenefits));
					signRequest.setSign(MD5Util.MD5(signRequest.getBody() + this.KEY));
					String json = gson.toJson(signRequest);
					StringEntity stringEntity = new StringEntity(json, ContentType.create("application/json", "UTF-8"));
					stringEntity.setContentEncoding("UTF-8");
					String policyUrl = PropConfig.getValueFromProps("POLICY_SYNC_URL");;
					BaseResponse baseResponse = httpRequest.jsonRequest(stringEntity, policyUrl, BaseResponse.class);
					if(returnValue && (baseResponse == null || !baseResponse.getStatus().equals("00"))){
						returnValue = false;
					}
				}
			}
		}catch(Exception ex){
			logger.error(ex.getMessage());
			returnValue = false;
		}
		return returnValue;
	}
	
	/**
	 * @return
	 */
	private List<PCB> getSyncData(){
		List<PCB> pCBs = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		List<BigDecimal> contractIds = null;
		
		try{
			contractIds = new ArrayList<BigDecimal>();
			StringBuilder sb = new StringBuilder();
			sb.append("select ");
			connection = OracleHelper.getDBConnection();
			preparedStatement = connection.prepareStatement(sb.toString());
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()){
				contractIds.add(rs.getBigDecimal("CONTRACT_ID"));
			}
		
			if(contractIds != null && contractIds.size() > 0){
				pCBs = new ArrayList<PCB>();
				for(BigDecimal a : contractIds){
					sb = new StringBuilder();
					sb.append("select ");
					
					preparedStatement = connection.prepareStatement(sb.toString());
					preparedStatement.setBigDecimal(0, a);
					preparedStatement.setBigDecimal(1, a);
					rs = preparedStatement.executeQuery();
					
					PCB pCB = null;
					while(rs.next()){
						pCB = new PCB();
						pCB.setPaymentId(rs.getString("payment_id"));
						pCB.setStartDate(rs.getDate("START_DATE"));
						pCB.setEndDate(rs.getDate("END_DATE"));
						pCBs.add(pCB);
					}
				}
			}
		}catch(SQLException ex){
			logger.error(ex.getMessage());
		}finally{
			OracleHelper.closeDBConnection(connection);
		}
		return pCBs;
	}
	
	/**
	 * 循环添加in参数
	 * @param length
	 * @return
	 */
	private String preparePlaceHolders(int length) {

	    StringBuilder builder = new StringBuilder();

	    for (int i = 0; i < length;) {

	        builder.append("?");

	        if (++i < length) {

	            builder.append(",");

	        }

	    }

	    return builder.toString();

	}
	
	/**
	 * 为in参数添加值
	 * @param preparedStatement
	 * @param startIndex
	 * @param values
	 * @throws SQLException
	 */
	private void preparedStatementSetValues(PreparedStatement preparedStatement, int startIndex, Object... values) throws SQLException {

	    for (int i = startIndex; i < values.length + startIndex; i++) {

	        preparedStatement.setObject(i + 1, values[i - startIndex]);

	    }

	}
}
