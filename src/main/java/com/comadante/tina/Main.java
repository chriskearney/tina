package com.comadante.tina;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        EyeballsHttpClient eyeballsHttpClient = new EyeballsHttpClient();
        RootPane rootPane = new RootPane(eyeballsHttpClient);
        Parent root = rootPane;
        primaryStage.setTitle("Tina");
        primaryStage.setScene(new Scene(root, 885, 502));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

