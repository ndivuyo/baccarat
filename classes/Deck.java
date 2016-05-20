/*
2016
This is a class for the deck of playing cards
*/


package baccarat;
import java.lang.Math;
import java.util.Random;
import java.util.*;


public class Deck {
	

	//Instance Variables
	private ArrayList<Card> activePile;


	//Constructor
	public Deck() {
		this.activePile = new ArrayList<Card>();
		this.buildDeck();
	}


	//Getters
	public ArrayList<Card> getActivePile() {
		return this.activePile;
	}


	//buildDeck : Initializes a deck
	public void buildDeck() {
		for (int i = 0; i < 52; i++) {

			Suit suit;

			//Obtain one of the four suits
			switch( (int) Math.floor(i / 13) ) {
				case 0:
					suit = Suit.SPADE;
					break;
				case 1:
					suit = Suit.HEART;
					break;
				case 2:
					suit = Suit.CLUB;
					break;
				case 3:
					suit = Suit.DIAMOND;
					break;
				default:
					suit = Suit.SPADE;
			}

			//Add a card with suit and face value
			activePile.add(new Card(suit, (i % 13) + 1));
		}
	}


	//dealCard : deals a random card and removes it from the activePile
	public Card dealCard() {
		Random randomInt = new Random();
		int index = randomInt.nextInt(activePile.size());	//Get random number based on how many cards left in activePile
		Card card = activePile.get(index);
		activePile.remove(index);
		return card;
	}
	

	//isEmpty : boolean if there are no more active cards
	public boolean isEmpty() {
		if (activePile.size() == 0)
			return true;
		else
			return false;
	}
}