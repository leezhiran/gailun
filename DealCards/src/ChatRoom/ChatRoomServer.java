package ChatRoom;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DealCards.AsyncServer;
import Services.PlayerSessions;

/**
 * Servlet implementation class chatRoomServer
 */
@WebServlet(urlPatterns="/ChatRoomServer",asyncSupported=true)
public class ChatRoomServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final PlayerSessions playSessions=new PlayerSessions();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChatRoomServer() {
        super();
        
        // TODO Auto-generated constructor stub
    }
    //���ӹ㲥
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action=request.getParameter("Action");
		String match_id=request.getParameter("match_id");
		if(action.equals("Spooling")) {
		AsyncContext asyncContext=request.startAsync();
		AsyncServer.asyncContextInitialize(5000,match_id,asyncContext);
		}else if(action.equals("Broadcast")){
			match_id=String.valueOf(match_id);
			String user_id=request.getParameter("user_id");
			String s=user_id+":"+request.getParameter("content");
			ChatRoom.broadcast(playSessions,s, match_id);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
}