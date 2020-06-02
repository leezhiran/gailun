package Services;

import java.util.Random;
import java.util.Vector;
import java.util.stream.IntStream;

public class CardDealer {
	static public Deck[][] DealCards(Rule rule) {
		int QDeck=rule.getNumOfDecks();
		int QPlayer=rule.getNumOfPlayers();
		int NumberOfDraw=rule.getNumOfDraw();
		
		int Decksize;
		boolean includeJokers=rule.isIncludeJokers();
		int cardsCount;
		if(includeJokers==true) {
			Decksize=54;
		}else {
			Decksize=52;
		}
		cardsCount=Decksize*QDeck;
		if(NumberOfDraw==0) {
			NumberOfDraw=cardsCount/QPlayer;
		}
		Deck[] d=Deck.values();
		Vector<Deck> wholeDeck=new Vector<Deck>();
		int k=0;
		for(int i=0;i<cardsCount;i++) {
			if(k>Decksize) {
				k=0;
			}
			wholeDeck.add(d[k]);
			k++;
		}
		Random r=new Random();
		IntStream rs=r.ints();
		Deck[][] ret=new Deck[QPlayer][NumberOfDraw];
		for(int i=0;i<QPlayer;i++) {
			for(int j=0;j<NumberOfDraw;j++) {
				int tmp=Math.abs(r.nextInt())%cardsCount;
				ret[i][j]=wholeDeck.get(tmp);
				wholeDeck.remove(tmp);
				cardsCount--;
			}
		}
		return ret;
	}
}
