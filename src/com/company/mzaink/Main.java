package com.company.mzaink;

import com.company.nateshmbhat.streamIt.server.MyHttpServer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 800, 400));
        primaryStage.show();
        MyHttpServer.startServer();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
