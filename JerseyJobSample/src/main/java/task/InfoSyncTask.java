package task;

import org.apache.log4j.Logger;

import job.PBCJob;
import job.PartnerSyncJob;
import job.PaymentJob;
import job.util.SyncTime;

/**
 * 
 * @author JasonChen1
 *
 */
public class InfoSyncTask {
	
	private static Logger logger = Logger.getLogger(InfoSyncTask.class);
	/** jobs*/
	private PartnerSyncJob partnerSyncJob;
	private PBCJob pBCJob;
	private PaymentJob paymentJob;
	private SyncTime syncTime;

	/**
	 * Constructor
	 */
	public InfoSyncTask(){
		partnerSyncJob = new PartnerSyncJob();
		pBCJob = new PBCJob();
		paymentJob = new PaymentJob();
		syncTime = new SyncTime();
	}
	
	/**
	 * execute jobs
	 */
	public void exec(){
		logger.info("InfoSyncTask starting...");
		boolean jobResult = true;
		try{
			/*
			syncTime.searchSyncTime();
			if(!partnerSyncJob.exec()){
				jobResult = false;
			}
			if(!pBCJob.exec()){
				jobResult = false;
			}else{
				paymentJob.exec();
			}
			if(jobResult){
				syncTime.updateSyncTime();
			}
			*/
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}
		logger.info("InfoSyncTask ending...");
	}
	
	public static void main(String[] arg0){
		InfoSyncTask infoSyncTask = new InfoSyncTask();
		infoSyncTask.exec();
	}
}
