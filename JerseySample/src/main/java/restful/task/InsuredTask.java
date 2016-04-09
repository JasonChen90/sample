package restful.task;

import java.util.Date;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import restful.restclient.JerseyClient;
import restful.restserver.repository.payment.PaymentRepository;
import restful.restserver.representation.SignRequest;
import restful.restserver.resource.util.MyConvertor;
import restful.restserver.resource.util.MD5Util;
import restful.util.PropConfig;

@Configuration
public class InsuredTask {

	private final static Logger logger = Logger.getLogger(InsuredTask.class);
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	private JerseyClient jerseyClient;
	private String KEY;
	
	public void exec(){
		logger.info(new Date());
		jerseyClient = new JerseyClient();
		KEY = PropConfig.getValueFromProps("MD5KEY");
		
		//get payment status p
		List<Payment> payments = paymentRepository.getInsurePayment();
		SignRequest signRequest = null;
		for(Payment payment : payments){
			CallResult callResult = null;
			try{
				signRequest = new SignRequest();
				signRequest.setBody(MyConvertor.parseObject2Content(payment));
				signRequest.setSign(MD5Util.MD5(signRequest.getBody() + KEY));
				callResult = jerseyClient.httpRequest(Entity.entity(signRequest, MediaType.APPLICATION_JSON), CallResult.class, PropConfig.getValueFromProps("INSURE_URL"));
			}catch(Exception ex){
				if(callResult == null){
					callResult = new CallResult();
					callResult.setResult(1);
					callResult.setErrMsg(ex.getMessage());
				}
			}
			//after_issue to update status P->T
			paymentRepository.updatePaymentForInsure(payment, callResult);
		}
		
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
		}
		
		//get payment status t
		List<PaymentResult> paymentResults = paymentRepository.getInsuredPaymentForUpdate();
		for(PaymentResult paymentResult : paymentResults){
			signRequest = new SignRequest();
			signRequest.setBody(MyConvertor.parseObject2Content(paymentResult));
			signRequest.setSign(MD5Util.MD5(signRequest.getBody() + KEY));
			PaymentResult paymentResultResponse = jerseyClient.httpRequest(Entity.entity(signRequest, MediaType.APPLICATION_JSON), PaymentResult.class, PropConfig.getValueFromProps("INSURED_URL"));
			//update_issue_result status T->F
			paymentRepository.updatePaymentForInsuredUpdate(paymentResultResponse);
		}
		
		logger.info(new Date());
	}
}
