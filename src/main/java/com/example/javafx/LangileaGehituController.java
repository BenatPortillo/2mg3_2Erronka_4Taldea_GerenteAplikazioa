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

public class LangileaGehituController extends BaseController {
    @FXML
    private Label erabiltzailea;
    @FXML
    private TextField dniField;
    @FXML
    private TextField izenaField;
    @FXML
    private TextField abizenaField;

    @FXML
    private TextField korreoaField;
    @FXML
    private TextField telefonoaField;

    @FXML
    private ComboBox lanPostuaComboBox;

    @FXML
    private PasswordField pasahitzaField;
    @FXML
    private ComboBox txatBaimenaComboBox;

    @FXML
    public void setErabiltzailea(String izena) {
        erabiltzailea.setText(izena);
    }

    public void onAtzeaBotoiaClick(MouseEvent mouseEvent) throws IOException {
        String erab = erabiltzailea.getText();

        FXMLLoader langileMenua = new FXMLLoader(App.class.getResource("langileMenua.fxml"));
        Scene scene = new Scene(langileMenua.load());
        LangileMenuaController lmc = langileMenua.getController();
        Stage usingStage = this.getUsingStage();
        lmc.setErabiltzailea(erab);
        lmc.setUsingStage(usingStage);
        usingStage.setScene(scene);
        usingStage.setTitle("Langile Menua");
        usingStage.show();
    }

    public void onGehituBotoiaClick(ActionEvent actionEvent) throws IOException {
        String dni = dniField.getText();
        String izena = izenaField.getText();
        String abizena = abizenaField.getText();
        String korreoa = korreoaField.getText();
        String telefonoa = telefonoaField.getText();
        String pasahitza = pasahitzaField.getText();
        String lanPostua = lanPostuaComboBox.getValue().toString();

        // Verificar la selección del ComboBox para "txat_baimena"
        String txatBaimenaSeleccionado = (String) txatBaimenaComboBox.getSelectionModel().getSelectedItem();
        Boolean txatBaimena = false; // Valor predeterminado (false)

        // Si la selección es "Bai", asignar true
        if ("Bai".equals(txatBaimenaSeleccionado)) {
            txatBaimena = true;
        }

        // Verificar si los campos están vacíos
        if (izena.isEmpty() || abizena.isEmpty()|| korreoa.isEmpty() || telefonoa.isEmpty()|| pasahitza.isEmpty() || lanPostua.isEmpty()) {
            String izenaError = "Errorea";
            String mezuLuzeaError = "Datu guztiak sartu behar dituzu.";
            mezuaPantailaratu(izenaError, mezuLuzeaError, Alert.AlertType.ERROR);
            return;
        }

        // Crear el nuevo trabajador
        Langilea langileBerria = new Langilea();
        langileBerria.setDni(dni);
        langileBerria.setIzena(izena);
        langileBerria.setAbizena(abizena);
        langileBerria.setPasahitza(pasahitza);
        langileBerria.setKorreoa(korreoa);
        langileBerria.setTelefonoa(telefonoa);
        langileBerria.setPostua(lanPostua);
        langileBerria.setTxatBaimena(txatBaimena); // Establecer el valor de txat_baimena

        // Llamada a la base de datos para agregar el trabajador
        LangileaDbKudeaketa.langileaGehitu(langileBerria);

        // Limpiar los campos después de añadir
        dniField.clear();
        izenaField.clear();
        abizenaField.clear();
        pasahitzaField.clear();
        korreoaField.clear();
        telefonoaField.clear();
        pasahitzaField.clear();
        lanPostuaComboBox.setValue(null);

        // Redirigir al menú de trabajadores
        String erab = erabiltzailea.getText();

        FXMLLoader langileMenua = new FXMLLoader(App.class.getResource("langileMenua.fxml"));
        Scene scene = new Scene(langileMenua.load());
        LangileMenuaController lmc = langileMenua.getController();
        Stage usingStage = this.getUsingStage();
        lmc.setErabiltzailea(erab);
        lmc.setUsingStage(usingStage);
        usingStage.setScene(scene);
        usingStage.setTitle("Langile Menua");
        usingStage.show();
    }


}
