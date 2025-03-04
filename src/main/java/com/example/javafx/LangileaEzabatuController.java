package com.example.javafx;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.javafx.FuntzioLaguntzaileak.mezuaPantailaratu;

public class LangileaEzabatuController extends BaseController {

    @FXML
    private Label erabiltzailea;
    @FXML
    private TextField izenaField;
    @FXML
    private TableView<Langilea> langileTaula;
    @FXML
    private TableColumn<Langilea, String> izenaColumn;
    @FXML
    private TableColumn<Langilea, String> emailColumn;
    @FXML
    private TableColumn<Langilea, String> lanPostuaColumn;

    private Langilea aukeratutakoa;

    @FXML
    public void setErabiltzailea(String izena) {
        erabiltzailea.setText(izena);
    }

    @FXML
    public void onEzabatuBotoiaClick(ActionEvent actionEvent) throws IOException {
        Langilea aukeratutakoa = langileTaula.getSelectionModel().getSelectedItem();

        if (aukeratutakoa != null) {
            // Obtener el ID del trabajador seleccionado en la tabla
            int langileId = aukeratutakoa.getId();

            // Confirmación de eliminación con botones personalizados
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Konfirmazioa");
            alert.setHeaderText("Ziur zaude?");
            alert.setContentText("Langilea ezabatu nahi duzu?");

            // Crear botones personalizados
            ButtonType buttonSi = new ButtonType("Ziur Nago");
            ButtonType buttonNo = new ButtonType("Ez Ezabatu");

            // Establecer los botones personalizados en la alerta
            alert.getButtonTypes().setAll(buttonSi, buttonNo);

            // Mostrar la alerta y obtener la respuesta
            ButtonType result = alert.showAndWait().get();

            // Si el usuario confirma la eliminación
            if (result == buttonSi) {
                // Eliminar al trabajador usando su ID
                LangileaDbKudeaketa.langileaEzabatu(langileId);

                // Limpiar campos e interfaz
                izenaField.clear();
                langileTaula.getSelectionModel().clearSelection();

                // Regresar al menú de trabajadores
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
            } else {
                // Si el usuario cancela la eliminación
                String izena = "Erabakia";
                String mezuLuzea = "Ez da ezabatU";
                mezuaPantailaratu(izena, mezuLuzea, Alert.AlertType.INFORMATION);
            }
        } else {
            String izena = "Errorea";
            String mezuLuzea = "Ez da langilerik aukeratu.";
            mezuaPantailaratu(izena, mezuLuzea, Alert.AlertType.ERROR);
        }
    }

    @FXML
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

    @FXML
    public void initialize() {
        ObservableList<Langilea> langileak = LangileaDbKudeaketa.getAllLangileak();

        // Asignar la lista de trabajadores a la tabla
        langileTaula.setItems(langileak);

        // Configuración de las columnas
        izenaColumn.setCellValueFactory(new PropertyValueFactory<>("izena"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("korreoa"));
        lanPostuaColumn.setCellValueFactory(new PropertyValueFactory<>("postua"));

        // Establecer el tamaño de las columnas
        izenaColumn.setPrefWidth(100);
        emailColumn.setPrefWidth(100);
        lanPostuaColumn.setPrefWidth(100);

        // Establecer el tamaño de la tabla
        langileTaula.setPrefWidth(300);
        langileTaula.setPrefHeight(150);

        // Agregar el evento de clic en la fila
        langileTaula.setOnMouseClicked(this::onTableRowClick);
    }

    // Evento de clic en una fila de la tabla
    private void onTableRowClick(MouseEvent event) {
        aukeratutakoa = langileTaula.getSelectionModel().getSelectedItem();
        if (aukeratutakoa != null) {
            izenaField.setText(aukeratutakoa.getIzena());
        }
    }
}
