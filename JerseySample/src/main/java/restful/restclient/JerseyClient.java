package restful.restclient;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.glassfish.jersey.SslConfigurator;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;

import restful.restserver.model.Order;
import restful.restserver.representation.OrderRequest;
import restful.restserver.representation.OrderResponse;
import restful.restserver.resource.util.MyConvertor;

/**
 * @author JasonChen1
 */
public class JerseyClient {
	
	public <R> R httpRequest(Entity<?> entity,Class<R> customResponse,String url){
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.register(JacksonFeature.class);
		
		/*SslConfigurator sslConfig = SslConfigurator.newInstance().trustStoreFile("./truststore_client")
		        .trustStorePassword("secret-password-for-truststore")
		        .keyStoreFile("./keystore_client")
		        .keyPassword("secret-password-for-keystore");
		SSLContext sslContext = sslConfig.createSSLContext();
		ClientBuilder.newBuilder().sslContext(sslContext);*/
		Client client = ClientBuilder.newClient(clientConfig);
		
		WebTarget webTarget = client.target(url);
		Response r = webTarget.request(MediaType.APPLICATION_JSON).post(entity);
		R response = r.readEntity(customResponse);
		return response;
	}

	public void httpsRequest() throws NoSuchAlgorithmException{
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.register(JacksonFeature.class);
		
		SslConfigurator sslConfig = SslConfigurator.newInstance().trustStoreFile("./truststore_client")
		        .trustStorePassword("secret-password-for-truststore")
		        .keyStoreFile("./keystore_client")
		        .keyPassword("secret-password-for-keystore");
		SSLContext sslContext = sslConfig.createSSLContext();
		ClientBuilder.newBuilder().sslContext(sslContext);
		Client client = ClientBuilder.newClient(clientConfig);
		
		WebTarget webTarget = client.target("https://localhost:8080/JerseySample/rest/orderService/searchPolicy");
		OrderRequest orderRequest = new OrderRequest();
		orderRequest.setOrderId("1234");
		Response response = webTarget.request(MediaType.APPLICATION_JSON).post(Entity.entity(orderRequest, MediaType.APPLICATION_JSON));
		OrderResponse orderResponse = response.readEntity(OrderResponse.class);
		System.out.println(response);
	}
	
	public void generalRequest(){
		ObjectMapper om = new ObjectMapper();
		String json = "{\"orderId\":5}";
		try {
			Order order = om.readValue(json, Order.class);
			//Order[] orders = om.readValue("", Order[].class);
			//List<Order> orderList = Arrays.asList(orders);
			System.out.println(order.getOrderId());
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Order order = MyConvertor.parseContent2Object(json, Order.class);
		System.out.println(order.getOrderId());
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.register(JacksonFeature.class);
		
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget webTarget = client.target("https://localhost:8080/JerseySample/rest/orderService/searchPolicy");
		OrderRequest orderRequest = new OrderRequest();
		orderRequest.setOrderId("1234");
		Response response = webTarget.request(MediaType.APPLICATION_JSON).post(Entity.entity(orderRequest, MediaType.APPLICATION_JSON));
		
		OrderResponse orderResponse = response.readEntity(OrderResponse.class);
		if(orderResponse != null){
			
		}
		System.out.println(response);
	}
}
