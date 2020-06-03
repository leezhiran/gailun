package Services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import exceptions.MatchFullException;
import exceptions.MatchNoFoundException;

public class Matches {
	private final static ConcurrentMap<Integer,Match> matchList=new ConcurrentHashMap<Integer,Match>();
	private final static AtomicLong ID=new AtomicLong(0);
	public static int createMatch(Rule rule,int user_id) {
		Match ret=new Match(ID.intValue(),rule,user_id);
		matchList.put(ID.intValue(),ret);
		ID.incrementAndGet();
		return ret.getMatch_id();
	}
	public static Match getMatch(int match_id) {
		return matchList.get(match_id);
	}
	public static void joinGame(int match_id,int user_id) throws MatchNoFoundException, MatchFullException {
		Match s;
		if((s=matchList.get(match_id))==null) {
			throw new MatchNoFoundException();
			
		}
		s.joinMatch(user_id);
	}
	public static boolean gotEnoughPlayers(int match_id) {
		System.out.println(match_id);
		return matchList.get(match_id).gotEnoughPlayers();
	}
	public static byte[] sendHands(int match_id,int i) {
		return matchList.get(match_id).sendHands(i);
	}
	public static byte[]  listGame() {
		List<String> l=new ArrayList<String>();
		for(Map.Entry<Integer, Match> entry:matchList.entrySet()) {
			l.add(entry.getValue().toString());
		}
		byte[] ret=null;
		try {
			ByteArrayOutputStream bas=new ByteArrayOutputStream();
			ObjectOutputStream oos=new ObjectOutputStream(bas);
			oos.writeObject(l);
			oos.flush();
			bas.flush();
			ret= bas.toByteArray();
			bas.close();
			oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	public static List<Integer> getPlayerIds(int match_id) {
		return matchList.get(match_id).getPlayerids();
	}
}
class Match{
	int Player_count;
	int Match_id;
	List<Integer> Player_ids; 
	Deck[][] cardsForEach; 
	public Match(int Match_id,Rule rule,int user_id) {
		Player_count=rule.getNumOfPlayers();
		Player_ids=new ArrayList<Integer>();
		cardsForEach=CardDealer.DealCards(rule);
		Player_ids.add(user_id);
		this.Match_id=Match_id;
	}
	public void joinMatch(int user_id) throws MatchFullException {
		if(Player_ids.size()>=Player_count) {
			throw new MatchFullException();
		}
		Player_ids.add(user_id);
	}
	public int getMatch_id() {
		return Match_id;
	}
	public boolean gotEnoughPlayers() {
		return Player_ids.size()==Player_count;
	}
	public byte[] sendHands(int i) {
		Deck[] h=cardsForEach[i];
		byte[] ret=null;
		ByteArrayOutputStream bao=new ByteArrayOutputStream();
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(bao);
			oos.writeObject(h);
			oos.flush();
			bao.flush();
			ret=bao.toByteArray();
			oos.close();
			bao.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	public List<Integer> getPlayerids(){
		return Player_ids;
	}
	@Override 
	public String toString() {
		String str=new String();
		str+=String.valueOf(Match_id)+"\t"+String.valueOf(Player_ids.size())+"/"+Player_count;
		return str;
	}
}