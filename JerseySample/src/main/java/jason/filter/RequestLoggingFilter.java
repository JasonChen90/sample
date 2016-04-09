package jason.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestLoggingFilter implements Filter {

	private ServletContext servletContext;
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		this.servletContext = arg0.getServletContext();
		this.servletContext.log("RequestLoggingFilter inited");

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
			throws IOException, ServletException {
		System.out.println("RequestLoggingFilter starting...");
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		filterChain.doFilter(request, response);
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
