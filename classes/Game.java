/*
2016
This is the main class for controlling gameplay
*/


package baccarat;


public class Game {
	

	//instance variables
	private ArrayList<Player> playerList;
	private static final int MAX_PLAYERS = 4;
	private Deck deck = new Deck();



	//main
	public static void main(String[] args) {
		//Thread to run the rounds in
		Thread round = new Thread(new RoundRun());
		round.start();
	}



	//RoundRun : runnable class for round thread
	class RoundRun implements Runnable {

		//run : overridden method for runnable
		public void run() {

			Round round = new Round();

			//Sequence for each round
			while (true) {
				round.newRound();
				round.placeBets(this.playerList);
				round.dealTwoCards(this.deck);
				round.dealThirdCard(this.deck);
				round.giveWinnings( round.determineWinner(), this.playerList );
			}
		}
	}



	//addPlayer : method to add a player to the game
	public void addPlayer(Player player) {

	}


	//removePlayer : method to remove a player from the game
	public void removePlayer(Player player) {

	}


	//endGame : method to end a game
	public void endGame() {

	}
}