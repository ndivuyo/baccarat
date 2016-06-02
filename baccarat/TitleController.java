package baccarat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TitleController {
    public TextField nameField;
    public Text console;
    public Text playerList;

    @FXML
    public void initialize(){
        updatePList();
    }

    public void addPlayer(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        if(nameField.getText().trim().length() == 0) {
            alert.setContentText("Invalid name. Try again.");
            alert.showAndWait();
            consoleMsg("Invalid name");
            nameField.setText("");
        }else if(nameField.getText().trim().length() > 20) {
            alert.setContentText("Max. name length is 20 characters. Try again.");
            alert.showAndWait();
            consoleMsg("name too long: max length: 20");
            nameField.setText("");
        }else if(Main.game.getPlayerList().size()>3) {
            alert.setContentText("Already at maximum player count. Please remove a player then try again.");
            alert.showAndWait();
            consoleMsg("Already at max players");
            nameField.setText("");
        }else {
            String uname = nameField.getText().trim();
            Main.game.addPlayer(uname);
            consoleMsg("Player added: " + uname);
            nameField.setText("");
            updatePList();
        }
    }

    public void loadPlayer(ActionEvent actionEvent){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        String uname = nameField.getText().trim();
        if(Presets.loadPlayer(uname).getName().equals("")){
            alert.setContentText("Player not found.");
            alert.showAndWait();
            consoleMsg("player not found.");
            nameField.setText("");
        }else {
            Main.game.addPlayer(Presets.loadPlayer(uname));
            consoleMsg("Player added: " + uname);
            nameField.setText("");
            updatePList();
        }
    }

    public void removePlayer(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        String uname = nameField.getText().trim();
        boolean exists = false;
        for(Player player : Main.game.getPlayerList()){
            if(player.getName().equals(uname)){
                exists=true;
            }
        }
        if(exists) {
            Main.game.removePlayer(uname);
            consoleMsg("Player removed: "+uname);
            nameField.setText("");
            updatePList();
        }else{
            alert.setContentText("Player '"+uname+"' does not exist.");
            alert.showAndWait();
            consoleMsg("error removing "+uname);
            nameField.setText("");
        }
    }

    public void consoleMsg(String msg){
        String timeStamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        String curr = console.getText();
        String[] lines = curr.split("\n");
        String nw = "";
        nw += msg + "\n";
        if(lines.length>11) {
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

    private void updatePList(){
        ArrayList<Player> plist = Main.game.getPlayerList();
        playerList.setText("");
        for (Player player : plist) {
            playerList.setText(playerList.getText()+player.getName());
            for(int i = 1; i < (30-player.getName().length());i++){
                playerList.setText(playerList.getText()+" ");
            }
            playerList.setText(playerList.getText()+"$"+String.format("%.2f", player.getBalance())+"\n");
        }
    }




    public void startGame(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        if(Main.game.getPlayerList().size()<1){
            alert.setContentText("Please add at least one player.");
            alert.showAndWait();
            consoleMsg("need more players to start game.");
        }else {
            Parent root;
            Stage stage;
            stage = (Stage) console.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("playscreen.fxml"));
            Scene scene = new Scene(root, 1024, 600);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }
}
