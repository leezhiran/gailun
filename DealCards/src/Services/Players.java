package Services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Players {
	static private final ConcurrentMap<Integer,Player> playerMapping=new ConcurrentHashMap<Integer,Player>();
	static public void logIn(int user_id) {
		playerMapping.put(user_id,new Player(user_id));
	}
	static public void logout(int user_id) {
		playerMapping.remove(user_id);
	}
}
class Player{
	int user_id;
	//String user_nickname;
	boolean inGame;
	public Player(int user_id) {

		this.user_id=user_id;
		this.inGame=false;
	}
	public void createGame(Rule rule) {
		//Matches.createGame(rule);
	}
}