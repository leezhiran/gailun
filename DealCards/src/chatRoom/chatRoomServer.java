package chatRoom;

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

import Services.PlayerSessions;

/**
 * Servlet implementation class chatRoomServer
 */
@WebServlet(urlPatterns="/chatRoomServer",asyncSupported=true)
public class chatRoomServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final PlayerSessions playSessions=new PlayerSessions();
    private static final ConcurrentMap<Integer,ChatRoom> chatRoomMapping=new ConcurrentHashMap<Integer,ChatRoom>();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public chatRoomServer() {
        super();
        
        // TODO Auto-generated constructor stub
    }
    //Ìí¼Ó¹ã²¥
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action=request.getParameter("Action");
		if(action.equals("Spooling")) {
		int uid=Integer.parseInt(request.getParameter("user_id"));
		AsyncContext asyncContext=request.startAsync();
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
