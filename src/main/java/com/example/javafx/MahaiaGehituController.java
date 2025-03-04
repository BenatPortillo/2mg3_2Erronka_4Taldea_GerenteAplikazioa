package com.example.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.javafx.FuntzioLaguntzaileak.mezuaPantailaratu;

public class MahaiaGehituController extends BaseController {
    @FXML
    private Label erabiltzailea;
    @FXML
    private TextField izenaField;
    @FXML
    private TextField komentsalField;

    @FXML
    public void setErabiltzailea(String izena) {
        erabiltzailea.setText(izena);
    }

    public void onAtzeaBotoiaClick(MouseEvent mouseEvent) throws IOException {
        String erab = erabiltzailea.getText();

        FXMLLoader mahaiaMenua = new FXMLLoader(App.class.getResource("mahaiaMenua.fxml"));
        Scene scene = new Scene(mahaiaMenua.load());
        MahaiaMenuaController mmc = mahaiaMenua.getController();
        Stage usingStage = this.getUsingStage();
        mmc.setErabiltzailea(erab);
        mmc.setUsingStage(usingStage);
        usingStage.setScene(scene);
        usingStage.setTitle("Mahaien Menua");
        usingStage.show();
    }
    public void onGehituBotoiaClick(ActionEvent actionEvent) throws IOException {
        String komentsal = komentsalField.getText();
        String izena = izenaField.getText();

        // Verificar si los campos están vacíos
        if (izena.isEmpty() || komentsal.isEmpty()) {
            String izenaError = "Errorea";
            String mezuLuzeaError = "Datu guztiak sartu behar dituzu.";
            mezuaPantailaratu(izenaError, mezuLuzeaError, Alert.AlertType.ERROR);
            return;
        }

        // Crear el nuevo objeto Mahaia
        Mahaia mahaiBerria = new Mahaia(0, Integer.parseInt(izena), Integer.parseInt(komentsal));

        // Llamar al método para agregar el nuevo Mahaia a la base de datos
        MahaiaDbKudeaketa.mahaiaGehitu(mahaiBerria);

        // Limpiar los campos después de añadir
        komentsalField.clear();
        izenaField.clear();

        // Redirigir al menú principal
        String erab = erabiltzailea.getText();
        FXMLLoader mahaiaMenua = new FXMLLoader(App.class.getResource("mahaiaMenua.fxml"));
        Scene scene = new Scene(mahaiaMenua.load());
        MahaiaMenuaController mmc = mahaiaMenua.getController();
        Stage usingStage = this.getUsingStage();
        mmc.setErabiltzailea(erab);
        mmc.setUsingStage(usingStage);
        usingStage.setScene(scene);
        usingStage.setTitle("Mahaien Menua");
        usingStage.show();
    }


}
