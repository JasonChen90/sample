package restful.restserver.core.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import org.apache.log4j.Logger;

public class CORSResponseFilter implements ContainerResponseFilter {

	private static Logger log = Logger.getLogger(CORSResponseFilter.class);
	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		log.info("CORSResponseFilter success");
	}
}
