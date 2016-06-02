/*
2016
This is the class for controlling rounds
*/


package baccarat;

import java.io.IOException;
import java.lang.Math;
import java.util.*;


public class Round {


    //instance variables
    private ArrayList<Hand> handList;    //1st hand is Banker, 2nd is Player
    private ArrayList<Bet> betList;

    //Constructor
    public Round() {
        this.handList = new ArrayList<Hand>();
        this.betList = new ArrayList<Bet>();
        this.handList.add(new Hand(HandType.BANKER));
        this.handList.add(new Hand(HandType.PLAYER));

    }


    //newRound : method to setup a new round
    public void newRound() {
        for (int i = 0; i < 2; i++) {
            //Remove any cards from the hands
            this.handList.get(i).discardHand();
        }
        //Clear all bets
        this.betList.clear();
        Main.play.resetBetCoins();
    }



    //playerBet : method for a player to create a bet
    public Bet playerBet(Player player) {
        double betAmount = 0;

        Main.play.setStatus(player.getName() + " is placing a bet!");

        int betIndex;
        betIndex = Main.play.promptForBetType(player);

        if (betIndex != 3) {

            //Get valid bet amount
            while (true) {
                betAmount = Main.play.promptForBetAmount(player);
                if (betAmount > player.getBalance())
                    Main.play.alert("Error: Bet is higher than available balance.");
                else if (betAmount < 0.0)
                    Main.play.alert("Error: Bet must be greater than 0.");
                else
                    break;
            }

            //Take the money from the player's balance
            player.setBalance(player.getBalance() - betAmount);
            Main.play.updateBalances();
            //Determine correct bet type with input
        }
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

        Main.play.consoleMsg(player.getName() + "'s bet: " + betType);
        Main.play.consoleMsg(player.getName() + "'s bet: " + betAmount);
        return new Bet(betType, betAmount);
    }


    //placeBets : method for players to place bets
    public void placeBets(ArrayList<Player> players) {
        for (int i = 0; i < players.size(); i++) {
            //Add bets to the betList in player order
            Bet bet = playerBet(players.get(i));
            Main.play.showBetCoins(bet, i);
            this.betList.add(bet);

        }
    }


    //deal : method to deal a card to a hand
    public Card deal(Hand hand, Deck deck) {
        //Check if deck is empty, if so add new cards
        if (deck.isEmpty())
            deck.buildDeck();

        Card dealtCard = deck.dealCard();

        //Add card to a hand
        hand.addCard(dealtCard);

        return dealtCard;
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
        boolean playerDrawStatus = false, bankerDrawStatus = false;
        int playerThirdCard = 0;
        int[] handValues = {handList.get(0).calculateValue(), handList.get(1).calculateValue()};

        if (!(handValues[0] > 7) || !(handValues[1] > 7)) {

            //Determine Player Hand first
            if (handValues[1] < 6) {
                playerThirdCard = deal(this.handList.get(1), deck).getPointValue();
                playerDrawStatus = true;
            } else
                playerDrawStatus = false;

            //Determine Banker Hand
            if (playerDrawStatus == false) {
                if (handValues[0] < 6)
                    bankerDrawStatus = true;
            } else {
                if (handValues[0] < 3)
                    bankerDrawStatus = true;
                else {
                    switch (handValues[0]) {
                        case 3:
                            if (playerThirdCard != 8)
                                bankerDrawStatus = true;
                            break;
                        case 4:
                            if (playerThirdCard > 1 && playerThirdCard < 8)
                                bankerDrawStatus = true;
                            break;
                        case 5:
                            if (playerThirdCard > 3 && playerThirdCard < 8)
                                bankerDrawStatus = true;
                            break;
                        case 6:
                            if (playerThirdCard > 5 && playerThirdCard < 8)
                                bankerDrawStatus = true;
                            break;
                        default:
                    }
                }
            }
        }

        //deal banker third card
        if (bankerDrawStatus == true)
            deal(this.handList.get(0), deck);

        for (Hand hand : handList) {
            Main.play.consoleMsg(hand.getHandType() + ": " + hand.calculateValue());
        }
    }


    //determineWinner : method to determine the winner of the round
    public BetType determineWinner() {
        int[] totals = new int[2];

        for (int i = 0; i < 2; i++) {
            totals[i] = this.handList.get(i).calculateValue();
            totals[i] = Math.abs(9 - totals[i]);    //Closest to 9 wins
        }

        if (totals[0] < totals[1])
            return BetType.BANKER;
        else if (totals[0] > totals[1])
            return BetType.PLAYER;
        else
            return BetType.TIE;
    }


    //giveWinnings : method to distribute winnings based on round winner
    public void giveWinnings(BetType winner, ArrayList<Player> players) throws IOException {
        for (int i = 0; i < players.size(); i++) {
            //Determine if player won the bet
            if (this.betList.get(i).getBetType() == winner) {
                //Give winnings to a player
                players.get(i).setBalance(players.get(i).getBalance()+calculateWinnings(winner, this.betList.get(i).getAmount()));

                Main.play.consoleMsg("winner: "+players.get(i).getName());
                Main.play.setStatus(players.get(i).getName()+" Wins!");
                Main.play.moveBetCoins(players.get(i));

            }
        }
        Main.play.finalUpdateBalances();
    }


    //calculateWinnings : method to calculate the winnings for a player's bet
    public double calculateWinnings(BetType winner, double amount) {
        double winnings = 0;

        if (winner == BetType.PLAYER)
            winnings = amount * 2;
        else if (winner == BetType.BANKER)
            winnings = (amount * 2) - (amount * 2 * 0.05);
        else if (winner == BetType.TIE)
            winnings = amount * 8;

        return winnings;
    }
}