package jason.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/admin/*")
public class AuthorizationFilter implements Filter {

	private ServletContext servletContext;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.servletContext = filterConfig.getServletContext();
		this.servletContext.log("AuthorizationFilter start...");

	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		
		HttpSession httpSession = request.getSession(false);
		String uri = request.getRequestURI();
		if(httpSession == null && uri.endsWith("admin")){
			this.servletContext.log("unauthorized access request");
			response.sendRedirect("view/login.html");
		}else{
			//filterChain.doFilter(req, resp);
			response.sendRedirect("view/admin.html");
		}
	}
	
	@Override
	public void destroy() {
		this.servletContext.log("AuthorizationFilter end.");
	}

}
