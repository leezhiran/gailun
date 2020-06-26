package DealCards;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Services.AsyncListenerAdapter;
import Services.Matches;
import Services.PlayerSessions;
import Services.Players;
import Services.Rule;
import chatRoom.chatRoom;
import exceptions.MatchFullException;
import exceptions.MatchNoFoundException;
import exceptions.MultipleContextException;
import exceptions.NoCorrespondingContextException;

/**
 * Servlet implementation class AsyncServer
 */
@WebServlet(urlPatterns="/AsyncServer",asyncSupported=true)
public class AsyncServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public static final AtomicLong ID=new AtomicLong(1);
    public static final PlayerSessions playerSessions=new PlayerSessions();
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
    //����Ƶ��0
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String act=request.getParameter("Action");
		if(act.equals("log_in")) {
			int id=(int)ID.get();
			Players.logIn(id);
			PrintWriter pw=response.getWriter();
		 	chatRoom.joinChatRoom(id,0);
			pw.println("OK");
			pw.print(id);
			ID.incrementAndGet();
		}else if(act.equals("list_match")){
			String matches=Base64.getEncoder().encodeToString(Matches.listGame());
			PrintWriter pw=response.getWriter();
			pw.println("OK");
			pw.print(matches);
		}else if(act.equals("join_match")) {
			String match_id=request.getParameter("match_id");
			String user_id=request.getParameter("user_id");
			try {
				Matches.joinGame(Integer.parseInt(match_id), Integer.parseInt(user_id));
				PrintWriter pw=response.getWriter();
				chatRoom.joinChatRoom( Integer.parseInt(user_id),Integer.parseInt(match_id));
				pw.println("OK");
				AsyncContext asyncContext=request.startAsync();
				playerSessions.addNewContext(Integer.parseInt(user_id), asyncContext);
				pw.print(Matches.getMatch(Integer.parseInt(match_id)));
				asyncContext.complete();
			}catch(MatchNoFoundException me) {
				PrintWriter pw=response.getWriter();
				pw.println("ERR");
				me.printStackTrace(pw);
			} catch (NumberFormatException e) {
				PrintWriter pw=response.getWriter();
				pw.println("ERR");
				e.printStackTrace(pw);
			} catch (MatchFullException e) {
				PrintWriter pw=response.getWriter();
				pw.println("ERR");
				e.printStackTrace(pw);
			} catch (MultipleContextException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(act.equals("create_match")){
			//For test, create a match with 1deck and 2 people
			String user_id=request.getParameter("user_id");
			int s=Matches.createMatch(new Rule(1,2,0,true), Integer.parseInt(user_id));
			AsyncContext asyncContext=request.startAsync();
			PrintWriter pw=asyncContext.getResponse().getWriter();
			chatRoom.newChatRoom(s);
			chatRoom.joinChatRoom(Integer.parseInt(user_id),s);
			pw.println("OK");
			pw.println(s);
			try {
				playerSessions.addNewContext(Integer.parseInt(user_id), asyncContext);
			} catch (NumberFormatException | MultipleContextException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pw.print(Matches.getMatch(s));
			asyncContext.complete();
			pw.close();
		}else if(act.equals("Spooling")) {
			String user_id=request.getParameter("user_id");
			String match_id=request.getParameter("match_id");
			AsyncContext asyncContext=request.startAsync();
			asyncContextInitialize(5000,match_id,asyncContext);
			mainTainSessions(user_id,match_id,asyncContext);
		}		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	public void mainTainSessions(String user_id,String match_id,AsyncContext asyncContext) {
		try {
			playerSessions.refresh(Integer.parseInt(user_id), asyncContext);
			if(Matches.gotEnoughPlayers(Integer.parseInt(match_id))==true) {
				List<Integer> l=Matches.getPlayerIds(Integer.parseInt(match_id));
				for(int i=0;i<l.size();i++) {
					PrintWriter pw=playerSessions.getContext(l.get(i)).getResponse().getWriter();
					pw.println("OK");
					String s=new String(Base64.getEncoder().encode(Matches.sendHands(Integer.parseInt(match_id),i)),"utf-8").replace("\r\n", "");
					pw.println(s);
					playerSessions.getContext(l.get(i)).complete();
				}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoCorrespondingContextException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MultipleContextException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void asyncContextInitialize(int timeoutMilli,String match_id,AsyncContext asyncContext) {
		asyncContext.setTimeout(5000);
		asyncContext.addListener(new AsyncListenerAdapter(){
			@Override
			public void onTimeout(AsyncEvent arg0) {
				try {
					PrintWriter pw= asyncContext.getResponse().getWriter();
					pw.println("Spooling");
					pw.print(Matches.getMatch(Integer.parseInt(match_id)));
					asyncContext.complete();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}
