package temp;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;

import allianz.restserver.model.Order;
import junit.framework.TestCase;

public class TestTech extends TestCase{

	@Test
	public void test(){
		ObjectMapper om = new ObjectMapper();
		String json = "{\"orderId\":5}";
		try {
			Order order = om.readValue(json, Order.class);
			System.out.println(order.getOrderId());
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
