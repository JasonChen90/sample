package restful.restserver.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import restful.restserver.representation.OrderRequest;
import restful.restserver.representation.OrderResponse;
import restful.restserver.representation.RestfulResponse;
import restful.restserver.service.OrderService;

/**
 * Provide Order Rest Service
 * @author JasonChen1
 */
@Path("/orderService")  
public class OrderResource {  
	private int count = 1;
	
	@Autowired
	private OrderService orderService;
	
	/**
	 * @param orderRequest
	 * @return
	 */
	@POST
	@Path("/issuePolicy")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RestfulResponse execute(OrderRequest orderRequest){
		RestfulResponse restfulResponse = null;
		if(orderRequest != null){
			restfulResponse = orderService.issuePolicy(orderRequest);
		}
		return restfulResponse;
	}
	
	/**
	 * @param orderRequest
	 * @return
	 */
	@POST
	@Path("/searchPolicy")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OrderResponse execSearch(OrderRequest orderRequest){
		System.out.println("OrderResource searchPolicy count:" + ++count);
		OrderResponse orderResponse = null;
		if(orderRequest.getOrderId() != null){
			orderResponse = orderService.searchPolicy(orderRequest);
		}
		return orderResponse;
	}
} 
