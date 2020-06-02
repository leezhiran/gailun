package Services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Players {
	static private final ConcurrentMap<Integer,Player> playerMapping=new ConcurrentHashMap<Integer,Player>();
	
}
class Player{
	int user_id;
	String user_nickname;
	boolean inGame;
	public Player(String user_nickname,int user_id) {
		this.user_nickname=user_nickname;
		this.user_id=user_id;
		this.inGame=false;
	}
	public void createGame(Rule rule) {
		//Matches.createGame(rule);
	}
}