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

public class ErreserbaEzabatuController extends BaseController {
    @FXML
    private Label erabiltzailea;
    @FXML
    private TextField izenaField;
    @FXML
    private TableView<Erreserba> erreserbenTaula;
    @FXML
    private TableColumn<Erreserba, String> izenaColumn;
    @FXML
    private TableColumn<Erreserba, String> dataColumn;
    @FXML
    private TableColumn<Erreserba, Integer> pertsonaColumn;
    @FXML
    private TableColumn<Erreserba, Integer> mahaiaColumn;

    private Erreserba aukeratutakoa;

    @FXML
    public void setErabiltzailea(String izena) {
        erabiltzailea.setText(izena);
    }

    @FXML
    public void onEzabatuBotoiaClick(ActionEvent actionEvent) throws IOException {
        Erreserba aukeratutakoa = erreserbenTaula.getSelectionModel().getSelectedItem();

        if (aukeratutakoa != null) {
            // Obtener el ID del trabajador seleccionado en la tabla
            int erreserbaId = aukeratutakoa.getId();

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
                ErreserbaDbKudeaketa.erreserbaEzabatu(erreserbaId);

                // Limpiar campos e interfaz
                izenaField.clear();
                erreserbenTaula.getSelectionModel().clearSelection();

                // Regresar al menú de trabajadores
                String erab = erabiltzailea.getText();
                FXMLLoader erreserbaMenua = new FXMLLoader(App.class.getResource("erreserbaMenua.fxml"));
                Scene scene = new Scene(erreserbaMenua.load());
                ErreserbaMenuaController emc = erreserbaMenua.getController();
                Stage usingStage = this.getUsingStage();
                emc.setErabiltzailea(erab);
                emc.setUsingStage(usingStage);
                usingStage.setScene(scene);
                usingStage.setTitle("Erreserba Menua");
                usingStage.show();
            } else {
                // Si el usuario cancela la eliminación
                String izena = "Erabakia";
                String mezuLuzea = "Ez da ezabatu";
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
        FXMLLoader erreserbaMenua = new FXMLLoader(App.class.getResource("erreserbaMenua.fxml"));
        Scene scene = new Scene(erreserbaMenua.load());
        ErreserbaMenuaController emc = erreserbaMenua.getController();
        Stage usingStage = this.getUsingStage();
        emc.setErabiltzailea(erab);
        emc.setUsingStage(usingStage);
        usingStage.setScene(scene);
        usingStage.setTitle("Erreserba Menua");
        usingStage.show();
    }

    @FXML
    public void initialize() {
        ObservableList<Erreserba> erreserbak = ErreserbaDbKudeaketa.getAllErreserbak();


        erreserbenTaula.setItems(erreserbak);


        izenaColumn.setCellValueFactory(new PropertyValueFactory<>("erreserbaIzena"));
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("erreserbaDate"));
        pertsonaColumn.setCellValueFactory(new PropertyValueFactory<>("pertsonaKopurua"));
        mahaiaColumn.setCellValueFactory(new PropertyValueFactory<>("mahiaId"));

        izenaColumn.setPrefWidth(100);
        dataColumn.setPrefWidth(100);
        pertsonaColumn.setPrefWidth(100);
        mahaiaColumn.setPrefWidth(100);

        erreserbenTaula.setPrefWidth(400);
        erreserbenTaula.setPrefHeight(150);

        erreserbenTaula.setOnMouseClicked(this::onTableRowClick);
    }

    // Evento de clic en una fila de la tabla
    private void onTableRowClick(MouseEvent event) {
        aukeratutakoa = erreserbenTaula.getSelectionModel().getSelectedItem();
        if (aukeratutakoa != null) {
            izenaField.setText(aukeratutakoa.getErreserbaIzena());
        }
    }
}
