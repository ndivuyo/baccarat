package baccarat;

import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

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


    public Text p1betText;
    public Text p2betText;
    public Text p3betText;
    public Text p4betText;

    public Group group1;
    public Group group2;
    public Group group3;
    public Group group4;

    public ArrayList<Group> groups = new ArrayList<>();
    public ArrayList<Text> betTexts = new ArrayList<>();


    @FXML
    public void initialize(){
        consoleMsg("Good luck!");
        consoleMsg("Welcome to Baccarat!");
        groups.add(group1);
        groups.add(group2);
        groups.add(group3);
        groups.add(group4);
        betTexts.add(p1betText);
        betTexts.add(p2betText);
        betTexts.add(p3betText);
        betTexts.add(p4betText);

        for(Group box: groups){
            box.setVisible(false);
        }
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
        for(Group box: groups){
            box.setVisible(false);
        }
    }

    public void updateBalances(){

        int i=1;
        for(Player player: Main.game.getPlayerList()){
            switch (i){
                case 1: {
                    p1NameField.setText(player.getName());
                    p1CashField.setText("$" + String.format("%.2f", player.getBalance()));
                    break;
                }
                case 2: {
                    p2NameField.setText(player.getName());
                    p2CashField.setText("$" + String.format("%.2f", player.getBalance()));
                    break;
                }
                case 3: {
                    p3NameField.setText(player.getName());
                    p3CashField.setText("$" + String.format("%.2f", player.getBalance()));
                    break;
                }
                case 4: {
                    p4NameField.setText(player.getName());
                    p4CashField.setText("$" + String.format("%.2f", player.getBalance()));
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
                    p1CashField.setText("$" + String.format("%.2f", player.getBalance()));
                    break;
                }
                case 2: {
                    p2NameField.setText(player.getName());
                    p2CashField.setText("$" + String.format("%.2f", player.getBalance()));
                    break;
                }
                case 3: {
                    p3NameField.setText(player.getName());
                    p3CashField.setText("$" + String.format("%.2f", player.getBalance()));
                    break;
                }
                case 4: {
                    p4NameField.setText(player.getName());
                    p4CashField.setText("$" + String.format("%.2f", player.getBalance()));
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

    public void showBetCoins(Bet bet, int i){
        if(bet.getBetType()!=BetType.PASS) {
            betTexts.get(i).setText("$" + String.format("%.2f", bet.getAmount()));
            groups.get(i).setVisible(true);
            final Timeline timeline = new Timeline();
            timeline.setCycleCount(1);
            timeline.setAutoReverse(true);
            KeyValue kv = new KeyValue(groups.get(i).translateYProperty(), 285);
            if(bet.getBetType()==BetType.BANKER) {
                kv = new KeyValue(groups.get(i).translateYProperty(), 285);
            }
            if(bet.getBetType()==BetType.PLAYER) {
                kv = new KeyValue(groups.get(i).translateYProperty(), 385);
            }
            if(bet.getBetType()==BetType.TIE) {
                kv = new KeyValue(groups.get(i).translateYProperty(), 205);
            }
            final KeyFrame kf = new KeyFrame(Duration.millis(600), kv);
            timeline.getKeyFrames().add(kf);
            timeline.play();
        }
    }

    public void resetBetCoins(){
        int i = 0;
        for(Group g: groups){
            g.setTranslateY(450);
            if(i==0){
                g.setTranslateX(75);
            }
            if(i==1){
                g.setTranslateX(300);
            }
            if(i==2){
                g.setTranslateX(525);
            }
            if(i==3){
                g.setTranslateX(775);
            }
            g.setVisible(false);
            i++;
        }
    }

    public void moveBetCoins(Player player){
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(true);


        int playerNo=0;
        int playerX=0;

        for(int i=0;i<Main.game.getPlayerList().size();i++){
            if(Main.game.getPlayerList().get(i).equals(player)){
                playerNo=i;
            }
        }

        switch(playerNo){
            case 0:
                playerX=75;
                break;
            case 1:
                playerX=300;
                break;
            case 2:
                playerX=525;
                break;
            case 3:
                playerX=775;
                break;
            default:
                break;
        }

        for(Group group: groups){
            KeyValue kv = new KeyValue(group.translateXProperty(), playerX);
            KeyFrame kf = new KeyFrame(Duration.millis(750), kv);
            timeline.getKeyFrames().add(kf);
            KeyValue kv1 = new KeyValue(group.translateYProperty(), 450);
            KeyFrame kf1 = new KeyFrame(Duration.millis(750), kv1);
            timeline.getKeyFrames().add(kf1);
        }
        timeline.play();
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for(Group group: groups){
                    group.setVisible(false);
                }
            }
        });

    }

}