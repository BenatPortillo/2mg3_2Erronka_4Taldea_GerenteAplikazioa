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

public class MahaiaEzabatuController extends BaseController {

    @FXML
    private Label erabiltzailea;
    @FXML
    private TextField zenbakiaField;
    @FXML
    private TableView<Mahaia> mahaienTaula;
    @FXML
    private TableColumn<Mahaia, String> izenaColumn;
    @FXML
    private TableColumn<Mahaia, Integer> komentsalColumn;


    private Mahaia aukeratutakoa;

    @FXML
    public void setErabiltzailea(String izena) {
        erabiltzailea.setText(izena);
    }

    @FXML
    public void onEzabatuBotoiaClick(ActionEvent actionEvent) throws IOException {
        Mahaia aukeratutakoa = mahaienTaula.getSelectionModel().getSelectedItem();

        if (aukeratutakoa != null) {
            // Obtener el ID de la mesa seleccionada en la tabla
            int mahaiaId = aukeratutakoa.getId();

            // Confirmación de eliminación con botones personalizados
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Konfirmazioa");
            alert.setHeaderText("Ziur zaude?");
            alert.setContentText("Mahaia ezabatu nahi duzu?");

            // Crear botones personalizados
            ButtonType buttonSi = new ButtonType("Ziur Nago");
            ButtonType buttonNo = new ButtonType("Ez Ezabatu");

            // Establecer los botones personalizados en la alerta
            alert.getButtonTypes().setAll(buttonSi, buttonNo);

            // Mostrar la alerta y obtener la respuesta
            ButtonType result = alert.showAndWait().get();

            // Si el usuario confirma la eliminación
            if (result == buttonSi) {
                // Eliminar la mesa usando su ID
                MahaiaDbKudeaketa.mahaiaEzabatu(mahaiaId);

                // Limpiar campos e interfaz
                zenbakiaField.clear();
                mahaienTaula.getSelectionModel().clearSelection();

                // Regresar al menú de mesas
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
            } else {
                // Si el usuario cancela la eliminación
                String izena = "Erabakia";
                String mezuLuzea = "Ez da mahaia ezabatu.";
                mezuaPantailaratu(izena, mezuLuzea, Alert.AlertType.INFORMATION);
            }
        } else {
            String izena = "Errorea";
            String mezuLuzea = "Ez da mahaia aukeratu.";
            mezuaPantailaratu(izena, mezuLuzea, Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onAtzeaBotoiaClick(MouseEvent mouseEvent) throws IOException {
        String erab = erabiltzailea.getText();

        FXMLLoader langileMenua = new FXMLLoader(App.class.getResource("mahaiaMenua.fxml"));
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
        ObservableList<Mahaia> mahaiak = MahaiaDbKudeaketa.getAllMahaiak();

        // Asignar la lista de trabajadores a la tabla
        mahaienTaula.setItems(mahaiak);

        // Configuración de las columnas
        izenaColumn.setCellValueFactory(new PropertyValueFactory<>("zenbakia"));
        komentsalColumn.setCellValueFactory(new PropertyValueFactory<>("gehienezkoKopurua"));


        // Establecer el tamaño de las columnas
        izenaColumn.setPrefWidth(100);
        komentsalColumn.setPrefWidth(100);

        // Establecer el tamaño de la tabla
        mahaienTaula.setPrefWidth(300);
        mahaienTaula.setPrefHeight(150);

        // Agregar el evento de clic en la fila
        mahaienTaula.setOnMouseClicked(this::onTableRowClick);
    }

    // Evento de clic en una fila de la tabla
    private void onTableRowClick(MouseEvent event) {
        aukeratutakoa = mahaienTaula.getSelectionModel().getSelectedItem();
        if (aukeratutakoa != null) {
            zenbakiaField.setText(String.valueOf(aukeratutakoa.getId()));
        }
    }
}
