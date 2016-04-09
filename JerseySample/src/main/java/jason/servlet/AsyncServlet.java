package jason.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns={"/asyncTest"},asyncSupported=true)
public class AsyncServlet extends HttpServlet {
	
	protected void service(HttpServletRequest req, HttpServletResponse resp){
		if(req.getMethod().equals("GET")){
			doPost(req,resp);
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp){
		
		final AsyncContext asyncContext = req.startAsync();
		asyncContext.start(new Runnable(){

			@Override
			public void run() {

				try {
					Thread.currentThread().sleep(60000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				ServletResponse response = asyncContext.getResponse();
				System.out.println(response.hashCode());
				asyncContext.complete();
			}});
	}
}
