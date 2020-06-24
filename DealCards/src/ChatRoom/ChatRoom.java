package ChatRoom;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.AsyncContext;

import Services.PlayerSessions;
import exceptions.NoCorrespondingContextException;

public class ChatRoom {
	private int ChatRoomNo;
    private static final ConcurrentMap<Integer,ChatRoom> chatRoomMapping=new ConcurrentHashMap<Integer,ChatRoom>();
    static {chatRoomMapping.put(0,new ChatRoom(0));};
	private List<Integer> player_list=new LinkedList<Integer>();
	public void check_in(int user_id) {
		player_list.add(user_id);
	}
	public void check_out(int user_id) {
		player_list.add(user_id);
	}
	public ChatRoom(int ChatRoomNo) {
		this.ChatRoomNo=ChatRoomNo;
	}
	public int getChatRoomNo() {
		return ChatRoomNo;
	}
	public void setChatRoomNo(int chatRoomNo) {
		ChatRoomNo = chatRoomNo;
	}
	public void boradcast(PlayerSessions playerSessions,String s) throws NoCorrespondingContextException {
		for(Integer i:player_list) {
			AsyncContext asyncContext=playerSessions.getContext(i);
			try {
				PrintWriter pw=asyncContext.getResponse().getWriter();
				pw.println(s);
				asyncContext.complete();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void newChatRoom(int no) {
		chatRoomMapping.put(no,new ChatRoom(no));
	}
	public static void destroyChatRoom(int no) {
		chatRoomMapping.remove(no);
	}
	public static void joinChatRoom(int user_id,int chatRoom_id) {
		chatRoomMapping.get(chatRoom_id).check_in(user_id);
	}
	public static void leaveChatRoom(int user_id,int chatRoom_id) {
		chatRoomMapping.get(chatRoom_id).check_in(user_id);
		joinChatRoom(user_id,0);
	}
	
	public static void broadcast(PlayerSessions playerSessions,String str,String chatRoom_id) {
		try {
			chatRoomMapping.get(Integer.parseInt(chatRoom_id)).boradcast(playerSessions, str);
		} catch (NumberFormatException | NoCorrespondingContextException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}
}
