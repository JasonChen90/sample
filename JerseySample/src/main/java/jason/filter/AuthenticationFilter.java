package jason.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebFilter("/JerseySample/rest/*")
public class AuthenticationFilter implements Filter {
	
	private int count = 1;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		ServletContext sc = request.getServletContext();
		System.out.println("AuthenticationFilter hash:" + this.hashCode());
		System.out.println("AuthenticationFilter count:" + ++count);
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		// TODO
		Enumeration<String> headers = req.getHeaderNames();
		if(headers != null){
			while(headers.hasMoreElements()){
				String element = headers.nextElement();
				if(element.equals("Authentication")){
					String elementValue = req.getHeader(element);
				}
			}
		}
		chain.doFilter(req, resp);
	}

	@Override
	public void destroy() {
	}

}
