package baccarat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
    private Scene currentScene;
    public static Game game;
    @Override
    public void start(Stage primaryStage) throws Exception{
        game = new Game();
        Parent title = FXMLLoader.load(getClass().getResource("title.fxml"));
        Scene titleScene = new Scene(title, 1024, 600);
        primaryStage.setTitle("Baccarat");
        primaryStage.setResizable(false);
        currentScene = titleScene;
        primaryStage.setScene(titleScene);
        primaryStage.show();
    }

    public Scene getCurrentScene(){
        return currentScene;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
