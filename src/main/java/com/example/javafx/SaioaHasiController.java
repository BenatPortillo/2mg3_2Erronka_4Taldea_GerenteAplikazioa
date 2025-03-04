package com.example.javafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SaioaHasiController extends BaseController {
    @FXML
    private TextField erabiltzailea;
    @FXML
    private TextField pasahitza;

    public void setErabiltzailea(String izena) {
        erabiltzailea.setText(izena);
    }

    @FXML
    protected void onsaioaHasiBotoiaClick() throws IOException {
        String erab = erabiltzailea.getText();
        String pasa = pasahitza.getText();

        //if (erab.isEmpty() || pasa.isEmpty()) {
            //FuntzioLaguntzaileak.mezuaPantailaratu("Arazoa", "Datu guztiak idatzi behar dira.", Alert.AlertType.ERROR);
        //} else {
            //int loginStatus = LangileaDbKudeaketa.erabiltzaileaKomprobatu(erab, pasa);
            int loginStatus = 1;
            if (loginStatus == 1) {
                FuntzioLaguntzaileak.mezuaPantailaratu("Ongi", "Saioa zuzen hasi duzu", Alert.AlertType.INFORMATION);

                FXMLLoader hasierakoMenua = new FXMLLoader(App.class.getResource("hasieraMenua.fxml"));
                Scene scene = new Scene(hasierakoMenua.load());
                HasierakoMenuaController hmc = hasierakoMenua.getController();
                Stage usingStage = this.getUsingStage();
                hmc.setErabiltzailea(erab);
                hmc.setUsingStage(usingStage);
                usingStage.setScene(scene);
                usingStage.setTitle("Hasierako Menua");
                usingStage.show();

            } else if (loginStatus == 0) {
                FuntzioLaguntzaileak.mezuaPantailaratu("Arazoa", "Langilea hau ez da gerentea", Alert.AlertType.ERROR);
            } else {
                FuntzioLaguntzaileak.mezuaPantailaratu("Arazoa", "Erabiltzailea edo Pasahitza gaizki sartu dituzu", Alert.AlertType.ERROR);
            }
        //}
    }
}