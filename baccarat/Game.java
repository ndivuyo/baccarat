/*
2016
This is the main class for controlling gameplay
*/


package baccarat;

import javafx.application.Platform;

import java.util.*;


public class Game {

    //instance variables
    private ArrayList<Player> playerList;                    //Players in the game
    private static final int MAX_PLAYERS = 4;                //Max players permitted
    private static final double STARTING_BALANCE = 1000;    //Standard starting money for players
    private Deck deck;                                        //The card deck
    private Main main;
    public RunRound runround;

    //Constructor
    public Game() {
        this.playerList = new ArrayList<Player>();
        this.deck = new Deck();
    }


    //addPlayer : method to add a player to the game
    public void addPlayer(String name) {
        String playerName = name;
        double playerBalance = STARTING_BALANCE;
        this.playerList.add(new Player(playerName, playerBalance));
    }

    public void addPlayer(String name, double bal) {
        String playerName = name;
        double playerBalance = bal;
        this.playerList.add(new Player(playerName, playerBalance));
    }

    public void addPlayer(Player player) {
        this.playerList.add(player);
    }


    //removePlayer : method to remove a player from the game
    public void removePlayer(String playerName) {
        for (int i = 0; i < this.playerList.size(); i++) {
            if (this.playerList.get(i).getName().equals(playerName)) {
                this.playerList.remove(i);
                break;
            }
        }
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }


    //startGame : method to start game
    public void startGame() {
        //Thread to run the rounds in
        //Used this way instead so there can be proper communication between the Round thread and the javaFX thread
        runround = new RunRound(this.playerList, this.deck);
        Platform.runLater(runround);
    }


    //endGame : method to end a game
    public void endGame() {
        //stop this.round
    }


    /****
     * RunRound : runnable class for round thread
     ****/
    class RunRound implements Runnable {

        //Instance variables
        private ArrayList<Player> playerList;
        private Deck deck;

        //Constructor
        public RunRound(ArrayList<Player> players, Deck deck) {
            this.playerList = players;
            this.deck = deck;
        }

        //run : overridden method for runnable
        public void run() {
            Round round = new Round();
            //Sequence for each round
            while (true) {

                round.newRound();                        //resets round

                round.placeBets(this.playerList);        //players bet

                round.dealTwoCards(this.deck);            //deal the first 2 cards

                round.dealThirdCard(this.deck);            //determine and deal third card

                round.giveWinnings(round.determineWinner(), this.playerList);        //determine winning hand and distribute money

            }
        }
    }
}
