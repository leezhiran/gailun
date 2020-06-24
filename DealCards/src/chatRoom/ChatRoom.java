package chatRoom;

import java.util.LinkedList;
import java.util.List;

public class ChatRoom {
	private int ChatRoomNo;
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
	
}
