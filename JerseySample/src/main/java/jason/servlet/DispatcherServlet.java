package jason.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/view/*")
public class DispatcherServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4089543517406586905L;
	
//	protected void service(HttpServletRequest req, HttpServletResponse resp){
//	}

	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp){
		String method = req.getMethod();
		if(method.equals("GET")){
			doPost(req,resp);
		}
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp){
		System.out.println("Dispatch doPost Start...");
		
		RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/asyncTest");
		try {
			dispatcher.forward(req, resp);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
		System.out.println("Dispatch doPost End.");
	}
}
