/*
2016
This is the class for controlling rounds
*/


package baccarat;

import java.util.Scanner;
import java.lang.Math;
import java.util.*;


public class Round {
	

	//instance variables
	private ArrayList<Hand> handList;	//1st hand is Banker, 2nd is Player
	private ArrayList<Bet> betList;


	//Constructor
	public Round() {
		this.handList = new ArrayList<Hand>();
		this.betList = new ArrayList<Bet>();
		this.handList.add( new Hand(HandType.BANKER) );
		this.handList.add( new Hand(HandType.PLAYER) );
	}


	//newRound : method to setup a new round
	public void newRound() {
		for (int i = 0; i < 2; i++) {
			//Remove any cards from the hands
			this.handList.get(i).discardHand();
		}
		//Clear all bets
		this.betList.clear();
	}


	//playerBet : method for a player to create a bet
	public Bet playerBet(Player player) {


		//MUCH OF THIS WILL CHANGE FOR GUI, SOME WILL STAY


		//User input
		Scanner scan = new Scanner(System.in);
		System.out.println("Who will you bet on?\n(0) for Banker, (1) for Player, (2) for Tie, (3) for Pass.\n");
		int betIndex;

		//Get valid bet type
		while (true) {
			betIndex = scan.nextInt();
			if (betIndex >= 0 && betIndex <= 3)
				break;
			else
				System.out.println("\nError: Please enter a correct number. (0 - 3)\n");
		}

		System.out.println("How much will you bet?\n");
		double betAmount;

		//Get valid bet amount
		while (true) {
			betAmount = scan.nextDouble();
			if (betAmount > 0)
				break;
			else if (player.getBalance() < betAmount)
				System.out.println("\nError: Bet is higher than available balance.\n");
			else
				System.out.println("\nError: Bet must be greater than 0.\n");
		}

		//Take the money from the player's balance
		player.setBalance( player.getBalance() - betAmount );

		//Determine correct bet type with input
		BetType betType;
		switch (betIndex) {
			case 0:
				betType = BetType.BANKER;
				break;
			case 1:
				betType = BetType.PLAYER;
				break;
			case 2:
				betType = BetType.TIE;
				break;
			case 3:
			default:
				betType = BetType.PASS;
		}

		return new Bet(betType, betAmount);
	}


	//placeBets : method for players to place bets
	public void placeBets(ArrayList<Player> players) {
		for (int i = 0; i < players.size(); i++) {
			//Add bets to the betList in player order
			this.betList.add( this.playerBet(players.get(i)) );
		}
	}


	//deal : method to deal a card to a hand
	public void deal(Hand hand, Deck deck) {
		//Check if deck is empty, if so add new cards
		if (deck.isEmpty())
			deck.buildDeck();

		//Add card to a hand
		hand.addCard( deck.dealCard() );
	}


	//dealTwoCards : method to deal 2 cards to each hand
	public void dealTwoCards(Deck deck) {
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				//Call deal method to handle card dealing
				deal(this.handList.get(i), deck);
			}
		}
	}


	//dealThirdCard : method to determine and deal third card
	public void dealThirdCard(Deck deck) {

	}


	//determineWinner : method to determine the winner of the round
	public BetType determineWinner() {
		int[] totals = new int[2];

		for (int i = 0; i < 2; i++) {
			totals[i] = this.handList.get(i).calculateValue();
			totals[i] = Math.abs(9 - totals[i]);	//Closest to 9 wins
		}

		if (totals[0] < totals[1])
			return BetType.BANKER;
		else if (totals[0] > totals[1])
			return BetType.PLAYER;
		else
			return BetType.TIE;
	}


	//giveWinnings : method to distribute winnings based on round winner
	public void giveWinnings(BetType winner, ArrayList<Player> players) {
		for (int i = 0; i < players.size(); i++) {
			//Determine if player won the bet
			if (this.betList.get(i).getBetType() == winner) {
				//Give winnings to a player
				players.get(i).setBalance( calculateWinnings(this.betList.get(i)) );
			}
		}
	}


	//calculateWinnings : method to calculate the winnings for a player's bet
	public double calculateWinnings(Bet bet) {
		return 0;
	}
}