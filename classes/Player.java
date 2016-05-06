/* 
2016
This class is for a user player in the game
*/


package baccarat;


public class Player {

	//Instance Variables
	private String name;
	private Double balance;

	//Constructor
	public Player(String name, Double balance) {
		this.name= name;
		this.balance = balance;
	}

	//Setters
	public void setName(String name) {
		this.name = name;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}

	//Getters
	public String getName() {
		return this.name;
	}
	public double getBalance() {
		return this.balance;
	}
}