package job;

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
import model.Partner;
import model.SignRequest;
import task.util.MD5Util;
import task.util.MyHttpRequest;

/**
 * partner sync job
 * @author JasonChen1
 *
 */
public class PartnerSyncJob {
	
	private static Logger logger = Logger.getLogger(PartnerSyncJob.class);
	
	/** initialize data*/
	private Gson gson;
	private MyHttpRequest httpRequest;
	private SignRequest signRequest;
	
	private String KEY;
	private int SYNC_SCALE;
	
	public PartnerSyncJob(){
		gson = new GsonBuilder().create();
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
			//获取需要同步的客户信息
			List<Partner> partners = this.getSyncData();
			
			//计算需要分批处理次数
			count = SYNC_SCALE == 0 ? 10 : partners.size() / SYNC_SCALE + 1;
			for(int i = 0; i < count; i++){
				
				if(i < count - 1){
					toIndex = i * SYNC_SCALE + SYNC_SCALE;
					
				}else{
					toIndex = partners.size();
				}
				List<Partner> subPartners = partners.subList(i * SYNC_SCALE, toIndex);
				if(subPartners.size() > 0){
					//封装request
					signRequest = new SignRequest();
					signRequest.setBody(gson.toJson(subPartners));
					signRequest.setSign(MD5Util.MD5(signRequest.getBody() + this.KEY));
					String partnersJson = gson.toJson(signRequest);
					StringEntity stringEntity = new StringEntity(partnersJson, ContentType.create("application/json", "UTF-8"));
					String partnerUrl = PropConfig.getValueFromProps("PARTNER_SYNC_URL");
					BaseResponse baseResponse = httpRequest.jsonRequest(stringEntity, partnerUrl, BaseResponse.class);
					//如果有正常返回，并且是错误处理，则返回false
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
	 * 获取需要同步的客户数据
	 * @return
	 */
	private List<Partner> getSyncData(){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		List<Partner> partners = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select part_id");
		sql.append("  from TABLE_NAME ");
		sql.append(" where event_date >= sysdate - 10 ");
		sql.append("   and event_date < sysdate ");
		try {
			connection = OracleHelper.getDBConnection();
			preparedStatement = connection.prepareStatement(sql.toString());
			ResultSet rs = preparedStatement.executeQuery();
			partners = new ArrayList<Partner>();
			Partner partner = null;
			while(rs.next()){
				partner = new Partner();
				partner.setPartId(rs.getBigDecimal("part_id"));
				partner.setFullName(rs.getString("full_name"));
				partner.setIdNo(rs.getString("national_id"));
				partners.add(partner);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally{
			OracleHelper.closeDBConnection(connection);
		}
		
		return partners;
	}
}
