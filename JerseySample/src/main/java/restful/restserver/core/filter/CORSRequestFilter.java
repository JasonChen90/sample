package restful.restserver.core.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Cookie;

import org.apache.log4j.Logger;

public class CORSRequestFilter implements ContainerRequestFilter {
	
	private static Logger log = Logger.getLogger(CORSRequestFilter.class);

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		//requestContext.setRequestUri(new URI(""));
		InputStream inputStream = requestContext.getEntityStream();
		Map<String,Cookie> cookies = requestContext.getCookies();
		log.info("CORSRequestFilter success");
	}

}
