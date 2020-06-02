package DealCards;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AsyncServer
 */
@WebServlet(urlPatterns="/AsyncServer",asyncSupported=true)
public class AsyncServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AsyncServer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		final AsyncContext asyncContext=request.startAsync();
		System.out.println("o");
		asyncContext.setTimeout(5000);
		asyncContext.start(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try{
					Thread.sleep(4000);
				}catch(InterruptedException e){
					
				}
				try {
					response.getWriter().print("delayed");
					asyncContext.complete();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
