package baccarat;

import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import javax.annotation.Resources;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class PlayscreenController {

    public Text console;
    public Text status;
    public Text p1NameField;
    public Text p1CashField;
    public Text p2NameField;
    public Text p2CashField;
    public Text p3NameField;
    public Text p3CashField;
    public Text p4NameField;
    public Text p4CashField;


    @FXML
    public void initialize(){
        consoleMsg("Good luck!");
        consoleMsg("Welcome to Baccarat!");
        Main.play=this;
        updateBalances();
    }


    public void consoleMsg(String msg){
        String timeStamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        String curr = console.getText();
        String[] lines = curr.split("\n");
        String nw = "";
        nw += msg + "\n";
        if(lines.length>6) {
            for (int i = 0; i < lines.length-1; i++) {
                nw += lines[i]+"\n";
            }
        }else{
            for (int i = 0; i < lines.length; i++) {
                nw += lines[i]+"\n";
            }
        }
        console.setText(timeStamp+" - "+nw);
    }

    public void setStatus(String str){
        status.setText(str);
    }

    public void buttonPress(){
        Main.game.startGame();
    }

    public void updateBalances(){
        int i=1;
        for(Player player: Main.game.getPlayerList()){
            switch (i){
                case 1: {
                    p1NameField.setText(player.getName());
                    p1CashField.setText("$" + player.getBalance());
                    break;
                }
                case 2: {
                    p2NameField.setText(player.getName());
                    p2CashField.setText("$" + player.getBalance());
                    break;
                }
                case 3: {
                    p3NameField.setText(player.getName());
                    p3CashField.setText("$" + player.getBalance());
                    break;
                }
                case 4: {
                    p4NameField.setText(player.getName());
                    p4CashField.setText("$" + player.getBalance());
                    break;
                }
            }
            i++;
        }
    }

    public int promptForBetType(Player player){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(player.getName()+"'s turn");
        alert.setHeaderText(null);
        alert.setContentText("You can choose to bet on either the Banker, the Player, a tie, \nor you can pass.");

        ButtonType buttonTypeOne = new ButtonType("Banker");
        ButtonType buttonTypeTwo = new ButtonType("Player");
        ButtonType buttonTypeThree = new ButtonType("Tie");
        ButtonType buttonTypeFour = new ButtonType("Pass");

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree, buttonTypeFour);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
            return 0;
        } else if (result.get() == buttonTypeTwo) {
            return 1;
        } else if (result.get() == buttonTypeThree) {
            return 2;
        } else {
            return 3;
        }
    }

    public double promptForBetAmount(Player player){
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle(player.getName()+"'s turn");
        dialog.setHeaderText(null);
        dialog.setContentText("Your bet can be anything from 0 to "+player.getBalance()+":");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            return Double.parseDouble(result.get());
        }
        return -1000.0;
    }

    public void alert(String str){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setHeaderText(null);
        alert.setContentText(str);
        alert.showAndWait();
    }
}