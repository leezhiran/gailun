package Services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import exceptions.MatchFullException;
import exceptions.MatchNoFoundException;

public class Players {
	static private final ConcurrentMap<Integer,Player> playerMapping=new ConcurrentHashMap<Integer,Player>();
	static public void logIn(int user_id) {
		playerMapping.put(user_id,new Player(user_id));
	}
	static public void logout(int user_id) {
		playerMapping.remove(user_id);
	}
	static public Player getPlayer(int user_id) {
		return playerMapping.get(user_id);
	}
}
