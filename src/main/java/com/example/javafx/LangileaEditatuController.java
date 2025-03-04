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

public class LangileaEditatuController extends BaseController {

    @FXML
    private Label erabiltzailea;

    @FXML
    private TableView<Langilea> langileTaula;

    @FXML
    private TableColumn<Langilea, String> dniColumn;

    @FXML
    private TableColumn<Langilea, String> izenaColumn;

    @FXML
    private TableColumn<Langilea, String> abizenaColumn;

    @FXML
    private TableColumn<Langilea, String> emailColumn;

    @FXML
    private TableColumn<Langilea, String> telefonoaColumn;

    @FXML
    private TableColumn<Langilea, String> lanPostuaColumn;

    @FXML
    private TableColumn<Langilea, String> pasahitzaColumn;

    @FXML
    private TableColumn<Langilea, Boolean> txatBaimenaColumn;

    @FXML
    private TextField dniField;

    @FXML
    private TextField izenaField;

    @FXML
    private TextField abizenaField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField telefonoaField;

    @FXML
    private TextField pasahitzaField;

    @FXML
    private ComboBox<String> lanPostuaComboBox;

    @FXML
    private ComboBox<String> txatBaimenaComboBox;

    private Langilea aukeratutakoa;

    @FXML
    public void setErabiltzailea(String izena) {
        erabiltzailea.setText(izena);
    }

    public void onEditatuBotoiaClick(ActionEvent actionEvent) throws IOException {
        // Obtener los valores de los campos de edición
        String dni = dniField.getText();
        String izena = izenaField.getText();
        String abizena = abizenaField.getText();
        String pasahitza = pasahitzaField.getText();
        String korreoa = emailField.getText();
        String telefonoa = telefonoaField.getText();
        String lanPostua = lanPostuaComboBox.getValue(); // Obtiene el valor seleccionado en el ComboBox

        // Verificar si el ComboBox para txatBaimena tiene una selección válida
        String txatBaimenaSeleccionado = txatBaimenaComboBox.getSelectionModel().getSelectedItem();
        Boolean txatBaimena = "Bai".equals(txatBaimenaSeleccionado); // Asigna true si es "Bai", si no, false

        // Verificar que se haya seleccionado un elemento de la tabla antes de proceder
        if (langileTaula.getSelectionModel().getSelectedItem() == null) {
            // Si no se ha seleccionado ningún elemento en la tabla, muestra una alerta
            FuntzioLaguntzaileak.mezuaPantailaratu(
                    "Ez dago hautatutako langilerik",
                    "Mesedez, hautatu langile bat editatzeko.",
                    Alert.AlertType.WARNING
            );
            return; // Salir de la función si no se seleccionó nada
        }

        int id = langileTaula.getSelectionModel().getSelectedItem().getId(); // Obtiene el ID del trabajador seleccionado

        // Crear un objeto Langilea con los nuevos datos
        Langilea langileEditatua = new Langilea(id,dni, izena, abizena, pasahitza, korreoa , telefonoa, lanPostua, txatBaimena);

        // Llamar a la base de datos para editar los datos
        boolean editatuta = LangileaDbKudeaketa.editatuLangilea(langileEditatua);

        if (editatuta) {
            // Alerta de éxito utilizando la clase FuntzioLaguntzaileak
            FuntzioLaguntzaileak.mezuaPantailaratu(
                    "Zuzen editatu da",
                    "Langilearen datuak editatu dira.",
                    Alert.AlertType.INFORMATION
            );

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
            // Alerta de error utilizando la clase FuntzioLaguntzaileak
            FuntzioLaguntzaileak.mezuaPantailaratu(
                    "Errorea editatzean",
                    "Errore bat egon da. Berriro saiatu mesedez.",
                    Alert.AlertType.ERROR
            );
        }
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

    @FXML
    public void initialize() {
        // Cargar los trabajadores desde la base de datos
        ObservableList<Langilea> langileak = LangileaDbKudeaketa.getAllLangileak();

        if (langileak != null) {
            langileTaula.setItems(langileak);
        } else {
            System.err.println("Langilerik ez dago");
        }

        dniColumn.setCellValueFactory(new PropertyValueFactory<>("dni"));
        izenaColumn.setCellValueFactory(new PropertyValueFactory<>("izena"));
        abizenaColumn.setCellValueFactory(new PropertyValueFactory<>("abizena"));
        pasahitzaColumn.setCellValueFactory(new PropertyValueFactory<>("pasahitza"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("korreoa"));
        telefonoaColumn.setCellValueFactory(new PropertyValueFactory<>("telefonoa"));
        lanPostuaColumn.setCellValueFactory(new PropertyValueFactory<>("postua"));
        txatBaimenaColumn.setCellValueFactory(new PropertyValueFactory<>("txatBaimena"));

        langileTaula.setPrefWidth(300);
        langileTaula.setPrefHeight(150);

        langileTaula.setOnMouseClicked(this::onTableRowClick);
    }

    private void onTableRowClick(MouseEvent event) {
        aukeratutakoa = langileTaula.getSelectionModel().getSelectedItem();

        if (aukeratutakoa != null) {
            dniField.setText(aukeratutakoa.getDni());
            izenaField.setText(aukeratutakoa.getIzena());
            abizenaField.setText(aukeratutakoa.getAbizena());
            pasahitzaField.setText(aukeratutakoa.getPasahitza());
            emailField.setText(aukeratutakoa.getKorreoa());
            telefonoaField.setText(aukeratutakoa.getTelefonoa());
            lanPostuaComboBox.setValue(aukeratutakoa.getPostua());


            // Establecer el valor de txatBaimenaComboBox basado en la selección anterior
            txatBaimenaComboBox.setValue(aukeratutakoa.isTxatBaimena() ? "Bai" : "Ez");
        } else {
            System.out.println("No se seleccionó ningún elemento en la tabla.");
        }
    }
}
