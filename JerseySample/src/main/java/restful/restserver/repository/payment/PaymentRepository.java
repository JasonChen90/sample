package restful.restserver.repository.payment;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Configuration;

import restful.restserver.model.PaymentInfo;
import restful.restserver.repository.util.GenericRepository;
import restful.task.CallResult;
import restful.task.Payment;
import restful.task.PaymentResult;

@Configuration
public interface PaymentRepository extends GenericRepository<PaymentInfo, BigDecimal> {

	/**
	 * @param paymentInfo
	 * @return
	 */
	boolean insertPayment(PaymentInfo paymentInfo);
	
	/**
	 * @param orderId
	 * @return
	 */
	PaymentInfo getByOrderId(String orderId);
	
	/**
	 * @param payTime
	 * @return
	 */
	List<PaymentInfo> getByPayTime(Date payTime);
	
	/**
	 * @param paymentInfo
	 * @return
	 */
	boolean paymentProcedure(PaymentInfo paymentInfo);
	
	/**
	 * @return
	 */
	List<Payment> getInsurePayment();
	
	/**
	 * @return
	 */
	boolean updatePaymentForInsure(Payment payment,CallResult callResult);
	
	/**
	 * @return
	 */
	List<PaymentResult> getInsuredPaymentForUpdate();
	
	/**
	 * @param paymentInfo
	 * @param callResult
	 * @return
	 */
	boolean updatePaymentForInsuredUpdate(PaymentResult paymentResult);
}
