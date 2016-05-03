package com.comadante.tina;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class Controller {


    @FXML public PasswordField passwordField;
    @FXML public TextField usernameField;

    @FXML
    public void initialize() {
    }

    public void handleSubmitLoginButton(ActionEvent actionEvent) throws IOException {
        String password = passwordField.getText();
        String username = usernameField.getText();
        EyeballsHttpClient eyeballsHttpClient = new EyeballsHttpClient(username, password);
        RootPane rootPane = new RootPane(eyeballsHttpClient);
        Main.primaryStage.setTitle("Tina");
        Main.primaryStage.setScene(new Scene(rootPane, 885, 502));
        Main.primaryStage.show();
    }


}
