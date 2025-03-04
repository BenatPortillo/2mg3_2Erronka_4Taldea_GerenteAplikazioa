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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class ErreserbaEditatuController extends BaseController {

    @FXML
    private Label erabiltzailea;

    @FXML
    private TableView<Erreserba> erreserbenTaula;

    @FXML
    private TableColumn<Erreserba, String> izenaColumn;

    @FXML
    private TableColumn<Erreserba, String> dataColumn;

    @FXML
    private TableColumn<Erreserba, String> pertsonaColumn;
    @FXML
    private TableColumn<Erreserba, String> mahaiaColumn;

    @FXML
    private TextField izenaField;

    @FXML
    private TextField dataField;

    @FXML
    private TextField pertsonaField;

    @FXML
    private TextField mahaiaField;

    private Erreserba aukeratutakoa;

    @FXML
    public void setErabiltzailea(String izena) {
        erabiltzailea.setText(izena);
    }




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
    public void onEditatuBotoiaClick(ActionEvent actionEvent) throws IOException {
        String izena = izenaField.getText();
        String dataStr = dataField.getText();
        String pertsonaStr = pertsonaField.getText();
        String mahaiaStr = mahaiaField.getText();

        // Verificar que el campo de personas y mesa se puedan convertir a enteros
        int pertsona = 0;
        int mahaia = 0;
        try {
            pertsona = Integer.parseInt(pertsonaStr);
            mahaia = Integer.parseInt(mahaiaStr);
        } catch (NumberFormatException e) {
            FuntzioLaguntzaileak.mezuaPantailaratu(
                    "Errorea",
                    "Pertsona kopurua eta mahaia zenbakiak izan behar dira.",
                    Alert.AlertType.ERROR
            );
            return;  // Salir si hay un error al convertir los números
        }

        // Verificar que la fecha se pueda parsear correctamente
        LocalDate localDate = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            localDate = LocalDate.parse(dataStr, formatter);
        } catch (DateTimeParseException e) {
            FuntzioLaguntzaileak.mezuaPantailaratu(
                    "Errorea",
                    "Data formatu egokia ez du.",
                    Alert.AlertType.ERROR
            );
            return;  // Salir si la fecha no es válida
        }

        java.sql.Date data = java.sql.Date.valueOf(localDate);

        // Obtener el ID de la reserva seleccionada
        int id = erreserbenTaula.getSelectionModel().getSelectedItem().getId();

        // Crear objeto Erreserba con los datos editados
        Erreserba erreserbaEditatua = new Erreserba(id, izena, data, pertsona, mahaia);

        // Llamar al método que actualiza la reserva en la base de datos
        boolean editatuta = ErreserbaDbKudeaketa.editatuErreserba(erreserbaEditatua);

        // Verificar si la edición fue exitosa
        if (editatuta) {
            // Alerta de éxito
            FuntzioLaguntzaileak.mezuaPantailaratu(
                    "Zuzen editatu da",
                    "Erreserbaren datuak editatu dira.",
                    Alert.AlertType.INFORMATION
            );
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
            // Alerta de error si la edición no fue exitosa
            FuntzioLaguntzaileak.mezuaPantailaratu(
                    "Errorea editatzean",
                    "Errore bat egon da. Berriro saiatu mesedez.",
                    Alert.AlertType.ERROR
            );
        }
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

    private void onTableRowClick(MouseEvent event) {
        aukeratutakoa = erreserbenTaula.getSelectionModel().getSelectedItem();

        if (aukeratutakoa != null) {
            // Convertir los valores a String antes de asignarlos a los TextFields
            izenaField.setText(aukeratutakoa.getErreserbaIzena());

            // Convertir la fecha de tipo Date a String (si es necesario formatear la fecha, puedes usar un SimpleDateFormat)
            dataField.setText(aukeratutakoa.getErreserbaDate().toString());  // Si es necesario un formato específico, usa un SimpleDateFormat

            // Convertir el int a String
            pertsonaField.setText(String.valueOf(aukeratutakoa.getPertsonaKopurua()));
            mahaiaField.setText(String.valueOf(aukeratutakoa.getMahiaId()));
        } else {
            System.out.println("No se seleccionó ningún elemento en la tabla.");
        }
    }

}
