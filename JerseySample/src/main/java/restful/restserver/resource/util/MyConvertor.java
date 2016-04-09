package restful.restserver.resource.util;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * 
 * @author JasonChen1
 *
 */
public class MyConvertor {

	private static Logger logger = Logger.getLogger(MyConvertor.class);
	/**
	 * Invoke jackson api for converting string to object
	 * @param content
	 * @param returnValue
	 * @return
	 */
	public static <T> T parseContent2Object(String content,Class<T> returnValue){
		ObjectMapper om = new ObjectMapper();
		T obj = null;
		try {
			obj = (T) om.readValue(content, returnValue);
		} catch (JsonParseException e) {
			logger.error(e.getMessage());
		} catch (JsonMappingException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		
		return obj;
	}
	
	public static <T> String parseObject2Content(T obj){
		ObjectMapper om = new ObjectMapper();
		try {
			return om.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			logger.error(e.getMessage());
		} catch (JsonMappingException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return "";
		
	}
}
