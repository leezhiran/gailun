package chatRoom;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DealCards.AsyncServer;
import Services.AsyncListenerAdapter;
import Services.Matches;
import Services.PlayerSessions;
import exceptions.MultipleContextException;
import exceptions.NoCorrespondingContextException;

/**
 * Servlet implementation class chatRoomServer
 */
@WebServlet(urlPatterns="/ChatRoomServer",asyncSupported=true)
public class ChatRoomServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final PlayerSessions playSessions=new PlayerSessions();
    private static List<String> messages=new ArrayList<String>();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChatRoomServer() {
        super();
        
        // TODO Auto-generated constructor stub
    }
    //Ìí¼Ó¹ã²¥
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action=request.getParameter("Action");
		String match_id=request.getParameter("match_id");
		String user_id=request.getParameter("user_id");
		System.out.println(action);
		if(action.equals("sign_in")) {
			AsyncContext ac=request.startAsync();
			AsyncServer.asyncContextInitialize(5000,"0", ac,false);
			try {
				playSessions.addNewContext(Integer.parseInt(user_id), ac);
			} catch (NumberFormatException | MultipleContextException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(action.equals("Spooling")) {
			AsyncContext ac=request.startAsync();
			asyncContextInitialize(100,"0", ac,true);
			try {
				playSessions.refresh(Integer.parseInt(user_id), ac);
			} catch (NumberFormatException | MultipleContextException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoCorrespondingContextException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(action.equals("Broadcast")) {
			messages.add(user_id+":"+request.getParameter("Content"));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
 
	}
	public static void asyncContextInitialize(int timeoutMilli,String match_id,AsyncContext asyncContext,boolean ret) {
		asyncContext.setTimeout(5000);
		asyncContext.addListener(new AsyncListenerAdapter(){
			@Override
			public void onTimeout(AsyncEvent arg0) {
				try {
					PrintWriter pw= asyncContext.getResponse().getWriter();
					pw.println("OK");
					if(ret)
						messages.forEach((k)->{pw.println(k);});
					asyncContext.complete();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}
