package baccarat;

import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.annotation.Resources;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static javafx.scene.control.DialogEvent.DIALOG_CLOSE_REQUEST;

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
    public Button start;
    public Button exitButton;


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
        start.setVisible(false);
        exitButton.setVisible(false);
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

    public void finalUpdateBalances() throws IOException {
        ArrayList<Player> removeList = new ArrayList<Player>();
        for(Player player: Main.game.getPlayerList()){
            if(player.getBalance()<=0){
                removeList.add(player);
            }
        }

        for(Player player: removeList){
            Main.game.getPlayerList().remove(player);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(player.getName()+" ran out of money!");
            alert.setHeaderText(player.getName()+" ran out of money. Removing from the table.");
            alert.setContentText(null);

            alert.showAndWait();
        }

        if(Main.game.getPlayerList().size()==0){
            goHome();
        }

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

    public int promptForBetType(Player player) {
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

    public void exitclick(ActionEvent actionEvent) {
        exit();
    }

    public void exit(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Are you sure you want to quit?");
        alert.setContentText(null);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            promptForSave();
        } else {
            return;
        }
    }

    public void promptForSave(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Save current players?");
        alert.setContentText(null);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Presets.saveAllPlayers(Main.game.getPlayerList());
            confirmSave();
        } else {
            System.exit(0);
        }
    }

    public void confirmSave(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Players saved.");
        alert.setContentText(null);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            System.exit(0);
        } else {
            System.exit(0);
        }
    }

    public void goHome() throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Players ran out of money!");
        alert.setHeaderText("No more players are left with money. Lets go home.");
        alert.setContentText(null);

        alert.showAndWait();
        Parent root;
        Stage stage;
        stage = (Stage) console.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("title.fxml"));
        Scene scene = new Scene(root, 1024, 600);
        stage.setScene(scene);
        stage.show();

    }

}