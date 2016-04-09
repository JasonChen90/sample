package restful.restserver.service;

import org.springframework.context.annotation.Configuration;

import restful.restserver.representation.RestfulResponse;
import restful.restserver.representation.OrderRequest;
import restful.restserver.representation.OrderResponse;

@Configuration
public interface OrderService {

	/**
	 * The issue of policy
	 * @param orderRequest
	 * @return
	 */
	RestfulResponse issuePolicy(OrderRequest orderRequest);
	
	/**
	 * Search policy by id
	 * @param orderRequest
	 */
	OrderResponse searchPolicy(OrderRequest orderRequest);
}
