package Services;

import exceptions.MatchFullException;
import exceptions.MatchNoFoundException;

public class Player{
	int user_id;
	//String user_nickname;
	boolean inGame;
	public Player(int user_id) {
		this.user_id=user_id;
		this.inGame=false;
	}
	public void createMatch(Rule rule) {
		Matches.createMatch(rule,user_id);
	}
	public void joinGame(int match_id) throws MatchNoFoundException, MatchFullException {
		Matches.joinGame(match_id,user_id);
	}
}