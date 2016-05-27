package baccarat;

public class Player {


	//Instance Variables
	private String name;
	private Double balance;


	//Constructor
	public Player(){
		this.name = "";
		this.balance = 0.0;
	}
	
	public Player(String name, Double balance) {
		this.name= name;
		this.balance = balance;
	}


	//Setters
	public void setName(String name) {
		this.name = name;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	

	//Getters
	public String getName() {
		return this.name;
	}
	public double getBalance() {
		return this.balance;
	}
	
	
	//overriding toString method
	@Override
	public String toString()							
	{
		return this.name+","+this.balance;
	}
}