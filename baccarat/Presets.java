/*
2016
This is the class for managing player presets
*/


package baccarat;

import java.util.*;


public class Presets {


	//savePlayer : method to save a player to a text file
	public static void savePlayer(Player player) {

	}


	//saveAllPlayers : method to save all players info in the game
	public static void saveAllPlayers(ArrayList<Player> players) {
		for (int i = 0; i < players.size(); i++) {
			//Save each player in the list
			savePlayer(players.get(i));
		}
	}
	

	//loadPlayer : method to load a player from a text file
	public static Player loadPlayer(String playerName) {
		return new Player("Test", 0.0);
	}
}