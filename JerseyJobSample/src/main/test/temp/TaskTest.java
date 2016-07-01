package temp;

import java.math.BigDecimal;

import org.junit.Test;

import job.PaymentJob;
import model.Payment;
import task.InfoSyncTask;

public class TaskTest {

	@Test
	public void test(){
		InfoSyncTask task = new InfoSyncTask();
		//task.exec();
		PaymentJob job = new PaymentJob();
		Payment payment = new Payment();
		 payment.setPaymentId("456");
		 payment.setPayAmount(new BigDecimal("5.26"));
		 payment.setPayTime("2015-12-15 08:00:00");
		 //job.processPayment(payment);
	}
	
	@Test
	public void Test2(){
		int temp[] = {};
		
		for (int str : temp) {
			String tt = String.format("%012d", str);
			System.out.println(tt);
		}
	}
}
