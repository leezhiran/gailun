package Services;
/**
 * {@code Rule} describes the rules of dealing cards.
 * <p>This class must be implicitly instantiated.
 * */
public class Rule {
	private int numOfDecks;
	private int numOfPlayers;
	private int numOfDraw;
	private boolean includeJokers;
	/**
	 * Constructor of the class {@code Rule}.
	 * @param numOfDecks the quantity of decks you want to use in the game.(Should be no less than 1)
	 * @param numOfPlayers the quantity of players you want to have in your game.(Game won't be able to start until enough players join the game)
	 * @param numOfDraw the quantity of cards each player will draw(If set as 0,cards would be dealt evenly.)
	 * @param includeJokers indicates whether the decks in game include jokers.
	 * */
	public Rule(int numOfDecks, int numOfPlayers, int numOfDraw,boolean includeJokers) {
		super();
		this.numOfDecks = numOfDecks;
		this.numOfPlayers = numOfPlayers;
		this.numOfDraw = numOfDraw;
		this.includeJokers = includeJokers;
	}
	public int getNumOfDecks() {
		return numOfDecks;
	}
	public int getNumOfPlayers() {
		return numOfPlayers;
	}
	public int getNumOfDraw() {
		return numOfDraw;
	}
	public boolean isIncludeJokers() {
		return includeJokers;
	}
	
}
