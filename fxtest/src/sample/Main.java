package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent newroot = FXMLLoader.load(getClass().getResource("EntryScene.fxml"));
        primaryStage.setTitle("Set configuration");
        Scene s = new Scene(newroot, 700, 500);
        //s.getStylesheets().add(0, "styles/my.css");
        primaryStage.setScene(s);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
