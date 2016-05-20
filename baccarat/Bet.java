/*
2016
This is a class for making bets
*/


package baccarat;


public class Bet {


	//Instance Variables
	private BetType betType;
	private double amount;


	//Constructor
	public Bet(BetType betType, double amount) {
		this.betType = betType;
		this.amount = amount;
	}
	

	//Getters
	public BetType getBetType() {
		return this.betType;
	}
	public double getAmount() {
		return this.amount;
	}
}