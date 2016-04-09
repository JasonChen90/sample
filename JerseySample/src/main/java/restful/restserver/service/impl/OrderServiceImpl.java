package restful.restserver.service.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import restful.restserver.model.Order;
import restful.restserver.model.Product;
import restful.restserver.repository.order.OrderRepository;
import restful.restserver.repository.product.ProductRepository;
import restful.restserver.representation.OrderRequest;
import restful.restserver.representation.OrderResponse;
import restful.restserver.representation.RestfulResponse;
import restful.restserver.service.OrderService;

/**
 * The implement of OrderService
 * @author JasonChen1
 *
 */
@Configuration
public class OrderServiceImpl implements OrderService {

	private static Logger logger = Logger.getLogger(OrderServiceImpl.class);
	/** */
	@Autowired
	private OrderRepository orderRepository;
	
	/** */
	@Autowired
	private ProductRepository productRepository;
	
	/**
	 * 
	 */
	@Override
	public RestfulResponse issuePolicy(OrderRequest orderRequest) {
		Order order = null;
		RestfulResponse restfulResponse = null;
		Map<String, Object> result = null;
		// insert to db
		if(orderRequest != null){
			order = new Order(); 
			order.setOrderId(orderRequest.getOrderId());
			order.setPolicyHolder(orderRequest.getPolicyHolder());
			result = orderRepository.execute(order);
		}
		
		restfulResponse = new RestfulResponse();
		if(result != null){
			int returnValue = Integer.parseInt(result.get("returnValue").toString());
			if(returnValue == 0){
				restfulResponse.setOrderId(order.getOrderId());
				restfulResponse.setStatus("00");
				restfulResponse.setMessage("");
			}
			else{
				restfulResponse.setOrderId(order.getOrderId());
				restfulResponse.setStatus("01");
				restfulResponse.setMessage(result.get("errmsg").toString());
			}
		}
		return restfulResponse;
	}

	/**
	 * 
	 */
	@Override
	public OrderResponse searchPolicy(OrderRequest orderRequest) {
		Order order = orderRepository.getById(orderRequest.getOrderId());
		OrderResponse orderResponse = new OrderResponse();
		if(order != null){
			if("P".equals(order.getStatus())){
				orderResponse.setStatus("02");
			}else if("F".equals(order.getStatus())){
				orderResponse.setOrderId(order.getOrderId());
				orderResponse.setPolicyHolder(order.getPolicyHolder());
				Product product = productRepository.getById("");
				if(product != null){
				}else{
				}
				orderResponse.setStatus("00");
			}else{
				orderResponse.setStatus("01");
			}
		}else{
			orderResponse.setStatus("01");
		}
		return orderResponse;
	}

}
