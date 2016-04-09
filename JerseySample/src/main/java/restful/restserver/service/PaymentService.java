package restful.restserver.service;

import java.util.List;

import org.springframework.context.annotation.Configuration;

import restful.restserver.representation.RestfulResponse;
import restful.restserver.representation.OrderRequest;
import restful.restserver.representation.PaymentRequest;
import restful.task.Payment;
import restful.task.PaymentResult;

/**
 * Payment Business Service
 * @author JasonChen1
 *
 */
@Configuration
public interface PaymentService {
	
	/**
	 * The notification of successful payment
	 * @param paymentRequest
	 * @return
	 */
	RestfulResponse payNotify(PaymentRequest paymentRequest);
	
	/**
	 * The reconciliation of single order by order id
	 * @param orderRequest
	 * @return
	 */
	PaymentRequest reconciliation(OrderRequest orderRequest);
	
	/**
	 * The reconciliation of multiple orders by order date
	 * @param orderRequest
	 * @return
	 */
	List<PaymentRequest> reconciliation2(OrderRequest orderRequest);
	
	/**
	 * Get payment that status is P
	 * @return
	 */
	List<Payment> getPaymentInsure();
	
	/**
	 * Update payment status from P to T
	 * @param paymentResult
	 * @return
	 */
	boolean updatePaymentInsured(PaymentResult paymentResult);
}
