package jason.servlet;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7193419891249586525L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp){
		System.out.println(req.getParameter("userName"));
		String userName = req.getParameter("userName");
		String password = req.getParameter("password");
		
		if(userName != null && userName != ""){
			HttpSession httpSession = req.getSession();
			httpSession.setAttribute(userName, password);
			httpSession.setMaxInactiveInterval(60);
			Cookie userCookie = new Cookie(userName, password);
			resp.addCookie(userCookie);
			try {
				resp.sendRedirect("/JerseySample/view/index.html");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected void doGet(HttpServletRequest req,HttpServletResponse resp){
		System.out.println(req.getParameter("userName"));
		String userName = req.getParameter("userName");
		String password = req.getParameter("password");
		
		if(userName != null && userName != ""){
			HttpSession httpSession = req.getSession();
			httpSession.setAttribute(userName, password);
			httpSession.setMaxInactiveInterval(60);
			Cookie userCookie = new Cookie(userName, password);
			resp.addCookie(userCookie);
			try {
				resp.sendRedirect("/JerseySample/view/index.html");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
