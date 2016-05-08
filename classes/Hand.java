/*
2016
A hand class for holding and using playing cards
*/


package baccarat;


public class Hand {
	

	//Instance Variables
	private HandType handType;
	private ArrayList<Card> cardList;


	//Constructor
	public Hand(HandType handType) {
		this.handType = handType;
	}


	//Getters
	public HandType getHandType() {
		return this.handType;
	}
	public ArrayList<Card> getCardList() {
		return this.cardList;
	}


	//addCard method : Adds a card to the hand
	public void addCard(Card card) {
		this.cardList.add(card);
	}


	//discardHand : method to remove all cards from the hand
	public void discardHand() {
		this.cardList.clear();
	}
	

	//calculateValue : calculates point value of the hand
	public int calculateValue() {
		int total = 0;

		for (Card card : this.cardList) {
			total += card.getPointValue();
		}

		total %= 10;
		return total;
	}
}