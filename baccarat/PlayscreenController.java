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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    public ImageView pcard1;
    public ImageView pcard2;
    public ImageView pcard3;
    public ImageView dcard1;
    public ImageView dcard2;
    public ImageView dcard3;

    public String cardstr0="/images/cover.png";
    public String cardstr1="/images/cover.png";
    public String cardstr2="/images/cover.png";
    public String cardstr3="/images/cover.png";
    public String cardstr4="/images/cover.png";
    public String cardstr5="/images/cover.png";

    ArrayList<ImageView> cards = new ArrayList<>();


    @FXML
    public void initialize(){
        consoleMsg("Good luck!");
        consoleMsg("Welcome to Baccarat!");
        setStatus("Welcome to Baccarat!");
        groups.add(group1);
        groups.add(group2);
        groups.add(group3);
        groups.add(group4);
        betTexts.add(p1betText);
        betTexts.add(p2betText);
        betTexts.add(p3betText);
        betTexts.add(p4betText);
        cards.add(pcard1);
        cards.add(pcard2);
        cards.add(pcard3);
        cards.add(dcard1);
        cards.add(dcard2);
        cards.add(dcard3);

        for(Group box: groups){
            box.setVisible(false);
        }
        Main.play=this;
        updateBalances();
        resetCards();

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
        alert.setHeaderText("You can choose to bet on either the Dealer, the Player, a tie, \nor you can pass.");
        alert.setContentText(null);

        ButtonType buttonTypeOne = new ButtonType("Dealer");
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

    public void resetCards(){
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(true);

        for(ImageView card: cards){
            card.setImage(new Image("images/cover.png"));
            KeyValue kv = new KeyValue(card.translateXProperty(), 875);
            KeyFrame kf = new KeyFrame(Duration.millis(750), kv);
            KeyValue kv1 = new KeyValue(card.translateYProperty(), 75);
            KeyFrame kf1 = new KeyFrame(Duration.millis(750), kv1);
            timeline.getKeyFrames().addAll(kf, kf1);
        }

        timeline.play();
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for(ImageView card: cards){
                    card.setVisible(false);
                }
            }
        });

    }

    /* DO NOTTTTT LOOK IN HERE ITS LONG AND MESSY BUT IT WORKS */

    public void showCards() throws IOException {
        setStatus("Dealing out the cards...");
        String cardstr0="/images/cover.png";
        String cardstr1="/images/cover.png";
        String cardstr2="/images/cover.png";
        String cardstr3="/images/cover.png";
        String cardstr4="/images/cover.png";
        String cardstr5="/images/cover.png";

        final Timeline timeline0 = new Timeline();
        timeline0.setCycleCount(1);
        timeline0.setAutoReverse(true);
        final Timeline timeline1 = new Timeline();
        timeline1.setCycleCount(1);
        timeline1.setAutoReverse(true);
        final Timeline timeline2 = new Timeline();
        timeline2.setCycleCount(1);
        timeline2.setAutoReverse(true);
        final Timeline timeline3 = new Timeline();
        timeline3.setCycleCount(1);
        timeline3.setAutoReverse(true);
        final Timeline timeline4 = new Timeline();
        timeline4.setCycleCount(1);
        timeline4.setAutoReverse(true);
        final Timeline timeline5 = new Timeline();
        timeline5.setCycleCount(1);
        timeline5.setAutoReverse(true);

        int i = 0;
        for(Hand hand: Main.game.runround.round.getHands()){
            int x = 0;
            for(Card card: hand.getCardList()){
                if(i==0){
                    if(x==0){
                        Main.play.cardstr0 = getCardImgStr(card);
                        KeyValue kv = new KeyValue(pcard1.translateXProperty(), 280);
                        KeyFrame kf = new KeyFrame(Duration.millis(750), kv);
                        KeyValue kv1 = new KeyValue(pcard1.translateYProperty(), 115);
                        KeyFrame kf1 = new KeyFrame(Duration.millis(750), kv1);
                        timeline0.getKeyFrames().addAll(kf, kf1);
                        pcard1.setVisible(true);
                    }
                    if(x==1){
                        Main.play.cardstr1 = getCardImgStr(card);
                        KeyValue kv = new KeyValue(pcard2.translateXProperty(), 345);
                        KeyFrame kf = new KeyFrame(Duration.millis(1000), kv);
                        KeyValue kv1 = new KeyValue(pcard2.translateYProperty(), 105);
                        KeyFrame kf1 = new KeyFrame(Duration.millis(1000), kv1);
                        timeline2.getKeyFrames().addAll(kf, kf1);
                        pcard2.setVisible(true);
                    }
                    if(x==2){
                        Main.play.cardstr2 = getCardImgStr(card);
                        KeyValue kv = new KeyValue(pcard3.translateXProperty(), 410);
                        KeyFrame kf = new KeyFrame(Duration.millis(1250), kv);
                        KeyValue kv1 = new KeyValue(pcard3.translateYProperty(), 95);
                        KeyFrame kf1 = new KeyFrame(Duration.millis(1250), kv1);
                        timeline4.getKeyFrames().addAll(kf, kf1);
                        pcard3.setVisible(true);
                    }
                }
                if(i==1){
                    if(x==0){
                        Main.play.cardstr3 = getCardImgStr(card);
                        KeyValue kv = new KeyValue(dcard1.translateXProperty(), 500);
                        KeyFrame kf = new KeyFrame(Duration.millis(750), kv);
                        KeyValue kv1 = new KeyValue(dcard1.translateYProperty(), 85);
                        KeyFrame kf1 = new KeyFrame(Duration.millis(750), kv1);
                        timeline1.getKeyFrames().addAll(kf, kf1);
                        dcard1.setVisible(true);
                    }
                    if(x==1){
                        Main.play.cardstr4 = getCardImgStr(card);
                        KeyValue kv = new KeyValue(dcard2.translateXProperty(), 565);
                        KeyFrame kf = new KeyFrame(Duration.millis(1000), kv);
                        KeyValue kv1 = new KeyValue(dcard2.translateYProperty(), 75);
                        KeyFrame kf1 = new KeyFrame(Duration.millis(1000), kv1);
                        timeline3.getKeyFrames().addAll(kf, kf1);
                        dcard2.setVisible(true);
                    }
                    if(x==2){
                        Main.play.cardstr5 = getCardImgStr(card);
                        KeyValue kv = new KeyValue(dcard3.translateXProperty(), 630);
                        KeyFrame kf = new KeyFrame(Duration.millis(1250), kv);
                        KeyValue kv1 = new KeyValue(dcard3.translateYProperty(), 65);
                        KeyFrame kf1 = new KeyFrame(Duration.millis(1250), kv1);
                        timeline5.getKeyFrames().addAll(kf, kf1);
                        dcard3.setVisible(true);
                    }
                }
                x++;
            }
            i++;
        }


        timeline0.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pcard1.setImage(new Image(Main.play.cardstr0));
                timeline1.play();

            }
        });
        timeline1.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dcard1.setImage(new Image(Main.play.cardstr1));
                timeline2.play();

            }
        });
        timeline2.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pcard2.setImage(new Image(Main.play.cardstr2));
                timeline3.play();

            }
        });
        timeline3.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dcard2.setImage(new Image(Main.play.cardstr3));
                timeline4.play();

            }
        });
        timeline4.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pcard3.setImage(new Image(Main.play.cardstr4));
                timeline5.play();

            }
        });
        timeline5.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dcard3.setImage(new Image(Main.play.cardstr5));
                try {
                    Main.game.runround.round.giveWinnings(Main.game.runround.round.determineWinner(), Main.game.getPlayerList());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });



        timeline0.play();

    }



    private String getCardImgStr(Card card){
        String str="images/";
        switch(card.getSuit()){
            case CLUB:
                str+="Clubs/";
                break;
            case DIAMOND:
                str+="Diamonds/";
                break;
            case HEART:
                str+="Hearts/";
                break;
            case SPADE:
                str+="Spades/";
                break;
        }
        str+=""+card.getFaceValue()+".png";
        return str;
    }

}