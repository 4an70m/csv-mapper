package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Created by 4an70m on 21.01.2018.
 */


public class MainLayout extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        String fxmlDocPath = "MainLayout.fxml";
        FXMLLoader loader = new FXMLLoader(this.getClass().getClassLoader().getResource(fxmlDocPath));
        loader.setController(new MainController(primaryStage));
        AnchorPane root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
