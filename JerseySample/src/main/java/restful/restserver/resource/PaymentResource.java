package restful.restserver.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import restful.restserver.representation.OrderRequest;
import restful.restserver.representation.PaymentRequest;
import restful.restserver.representation.RestfulResponse;
import restful.restserver.service.PaymentService;
import restful.task.Payment;
import restful.task.PaymentResult;

/**
 * Provide Payment Rest Service
 * @author JasonChen1
 *
 */
@Path("/paymentService")
public class PaymentResource {

	@Autowired
	private PaymentService paymentService;
	
	/**
	 * @param paymentRequest
	 * @return
	 */
	@POST
	@Path("/payNotify")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RestfulResponse execPayNotify(PaymentRequest paymentRequest){
		RestfulResponse restfulResponse = null;
		if(paymentRequest != null){
			restfulResponse = paymentService.payNotify(paymentRequest);
		}
		
		return restfulResponse;
	}
	
	/**
	 * @param orderRequest
	 * @return
	 */
	@POST
	@Path("/reconcile")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PaymentRequest execReconciliation(OrderRequest orderRequest){
		PaymentRequest paymentRequest = null;
		if(orderRequest != null){
			paymentRequest = paymentService.reconciliation(orderRequest);
		}
		
		return paymentRequest;
	}
	
	/**
	 * @param orderRequest
	 * @return
	 */
	@POST
	@Path("/reconcile2")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<PaymentRequest> execReconciliation2(OrderRequest orderRequest){
		List<PaymentRequest> paymentRequests = null;
		if(orderRequest != null){
			paymentRequests = paymentService.reconciliation2(orderRequest);
		}
		
		return paymentRequests;
	}
	
	/**
	 * @return
	 */
	@POST
	@Path("/getPaymentInsure")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Payment> getPaymentInsure(){
		List<Payment> payments = paymentService.getPaymentInsure();
		return payments;
	}
	
	/**
	 * @param paymentResult
	 */
	@POST
	@Path("/updatePaymentInsured")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updatePaymentInsured(PaymentResult paymentResult){
		if(paymentResult != null){
			paymentService.updatePaymentInsured(paymentResult);
		}
	}
}
