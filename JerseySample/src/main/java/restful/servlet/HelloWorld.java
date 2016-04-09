package restful.servlet;

import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.SessionCookieConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import restful.task.InsuredTask;

public class HelloWorld extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int i = 0;

	@Override
	public void doGet(HttpServletRequest request,HttpServletResponse response){
		try {
			InsuredTask insuredTask = new InsuredTask();
			//insuredTask.exec();
			System.out.println(request.getParameter("parms"));
			System.out.println(i++);
			HttpSession hs = request.getSession(false);
			System.out.println("session id:" + hs.getId());
			hs.getServletContext();
			ServletContext sc = request.getServletContext();
			SessionCookieConfig scc = sc.getSessionCookieConfig();
			
			System.out.println("HttpServlet hashcode: " + this.hashCode());
			System.out.println("HttpServletRequest hashcode: " + request.hashCode());
			
			System.out.println("Sleep...");
			//Thread.sleep(60000);
			System.out.println("Thread id" + Thread.currentThread().getId());
			PrintWriter writer = response.getWriter();
			writer.print("hello");
			writer.flush();
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
