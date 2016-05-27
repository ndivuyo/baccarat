package baccarat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Presets {
	//before using save methods, check duplication
	private static File list = new File("playerlist.txt");
	
	
	//savePlayer : method to save a player to a text file
	public static void savePlayer(Player player) {
		try (FileWriter out = new FileWriter("playerlist.txt", true)) {
				out.write(player.toString()+"\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		Player Lplayer = new Player();
		ArrayList<Player> TotalPlayer = new ArrayList<Player>();
		try (Scanner in = new Scanner(list)) {
			while (in.hasNextLine()) {
				String[] fields = in.nextLine().split(",");
				TotalPlayer.add(new Player(fields[0], Double.parseDouble(fields[1])));
			}
			for(int i=0; i<TotalPlayer.size(); i++){
				if(TotalPlayer.get(i).getName().equalsIgnoreCase(playerName)) {
					Lplayer = TotalPlayer.get(i);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return Lplayer;
	}
	/*
	public static void main(String [] args) {
	
		Player a = new Player("apple", 20.00);
		Player b = new Player("boy", 30.00);
		Player c = new Player("cat", 15.00);
	//	savePlayer(a);
	//	savePlayer(b);
	//	savePlayer(d);
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(a);
		players.add(b);
		players.add(c);
		saveAllPlayers(players);
	//	System.out.println(loadPlayer("han"));
		
	}*/
}