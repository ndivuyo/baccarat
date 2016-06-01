/*
2016
This is the main java fx class for the gameplay and UI
*/

package baccarat;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


//Main class
public class Main extends Application {


    private Scene currentScene;
    public static Game game;
    public static PlayscreenController play;

    //start
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Object to handle gameplay
        game = new Game();

        //FX settings
        Parent title = FXMLLoader.load(getClass().getResource("title.fxml"));
        Scene titleScene = new Scene(title, 1024, 600);
        primaryStage.setTitle("Baccarat");
        primaryStage.setResizable(false);
        primaryStage.setScene(titleScene);
        primaryStage.show();
    }


    /***
     * main method
     ***/
    public static void main(String[] args) {
        //Launch the UI Stage
        launch(args);
    }

    /****/


    //getCurrentScene
    public Scene getCurrentScene() {
        return currentScene;
    }
}
