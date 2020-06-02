package Services;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Matches {
	private final static ConcurrentMap<Integer,Match> matchList=new ConcurrentHashMap<Integer,Match>();
}
class Match{
	int Player_count;
	int[] Player_ids; 
	Deck[][] cardsForEach; 
	public Match(Rule rule) {
		Player_count=rule.getNumOfPlayers();
		Player_ids=new int[Player_count];
	}
}