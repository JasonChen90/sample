package restful.restserver.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import restful.restserver.model.PaymentInfo;
import restful.restserver.repository.payment.PaymentRepository;
import restful.restserver.representation.OrderRequest;
import restful.restserver.representation.PaymentRequest;
import restful.restserver.representation.RestfulResponse;
import restful.restserver.service.PaymentService;
import restful.task.Payment;
import restful.task.PaymentResult;

/**
 * The implement of PaymentService
 * @author JasonChen1
 *
 */
@Configuration
public class PaymentServiceImpl implements PaymentService {

	/**  */
	@Autowired
	private PaymentRepository paymentRepository;
	
	/**
	 * 
	 */
	@Override
	public RestfulResponse payNotify(PaymentRequest paymentRequest) {
		RestfulResponse restfulResponse = new RestfulResponse();;
		PaymentInfo paymentInfo = null;
		try{
			// 如果db中不存在支付信息，则插入支付信息
			if(paymentRequest.getPaymentId() != null){
				paymentInfo = new PaymentInfo();
				paymentInfo.setPaymentId(paymentRequest.getPaymentId());
				boolean insertResult = true;
				insertResult = paymentRepository.paymentProcedure(paymentInfo);
				if(insertResult){
					restfulResponse.setOrderId(paymentInfo.getOrderId());
					restfulResponse.setStatus("00");
					restfulResponse.setMessage("");
				}else{
					restfulResponse.setOrderId(paymentInfo.getOrderId());
					restfulResponse.setStatus("02");
					restfulResponse.setMessage("录入失败");
				}
				
			}else{
				restfulResponse.setStatus("01");
				restfulResponse.setMessage("缺少支付号");
			}
		}catch(Exception ex){
			restfulResponse.setStatus("01");
			restfulResponse.setMessage(ex.getMessage());
		}
		return restfulResponse;
	}

	/**
	 * 
	 */
	@Override
	public PaymentRequest reconciliation(OrderRequest orderRequest) {
		PaymentRequest paymentRequest = new PaymentRequest();
		paymentRequest.setPaymentId(new BigDecimal(11111111));
		return paymentRequest;
	}

	/**
	 * 
	 */
	@Override
	public List<PaymentRequest> reconciliation2(OrderRequest orderRequest) {
		List<PaymentRequest> paymentRequests = new ArrayList<PaymentRequest>();
		PaymentRequest paymentRequest = new PaymentRequest();
		paymentRequest.setPaymentId(new BigDecimal(11111111));
		paymentRequests.add(paymentRequest);
		return paymentRequests;
	}

	/**
	 * 
	 */
	@Override
	public List<Payment> getPaymentInsure() {
		return paymentRepository.getInsurePayment();
	}

	/**
	 * 
	 */
	@Override
	public boolean updatePaymentInsured(PaymentResult paymentResult) {
		return paymentRepository.updatePaymentForInsuredUpdate(paymentResult);
	}


}
