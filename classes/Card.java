/*
2016
Class for a playing card
*/


package baccarat;


public class Card {
	
	//Instance Variables
	private Suit suit;
	private int faceValue, pointValue;

	//Constructor
	public Card(Suit suit, int faceValue) {
		this.suit = suit;
		this.faceValue = faceValue;

		//Set the point value based on facevalue
		if (faceValue > 9)
			pointValue = 0;
		else
			pointValue = faceValue;
	}

	//Getters
	public Suit getSuit() {
		return this.suit;
	}
	public int getFaceValue() {
		return this.faceValue;
	}
	public int getPointValue() {
		return this.pointValue;
	}
}