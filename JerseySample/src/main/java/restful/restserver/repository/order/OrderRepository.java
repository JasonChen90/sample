package restful.restserver.repository.order;

import java.util.Map;

import org.springframework.context.annotation.Configuration;

import restful.restserver.model.Order;
import restful.restserver.repository.util.GenericRepository;

@Configuration
public interface OrderRepository extends GenericRepository<Order,String> {
	
	boolean insertOrder(Order order);
	
	Map<String, Object> execute(Order order);

}
