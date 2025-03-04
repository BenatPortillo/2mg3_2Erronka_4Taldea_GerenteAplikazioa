package com.example.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private TxatController txatController;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader saioaHasi = new FXMLLoader(App.class.getResource("saioaHasi.fxml"));
        Scene scene = new Scene(saioaHasi.load(), 320, 240);

        stage.setTitle("Saioa Hasi");
        stage.setMaximized(true);
        SaioaHasiController shc = saioaHasi.getController();
        stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
        stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());

        shc.setUsingStage(stage);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        if (txatController != null) {
            txatController.cerrarConexion();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
