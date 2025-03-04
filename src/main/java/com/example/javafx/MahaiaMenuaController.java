package com.example.javafx;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MahaiaMenuaController extends BaseController {
    @FXML
    private Label erabiltzailea;
    @FXML
    private TableView<Mahaia> mahaiaTaula;
    @FXML
    private TableColumn<Mahaia, Integer> izenaColumn;
    @FXML
    private TableColumn<Mahaia, Integer> komentsalColumn;

    @FXML
    public void setErabiltzailea(String izena) {
        erabiltzailea.setText(izena);
    }

    public void onAtzeaBotoiaClick(MouseEvent mouseEvent) throws IOException {
        String erab = erabiltzailea.getText();

        FXMLLoader hasierakoMenua = new FXMLLoader(App.class.getResource("hasieraMenua.fxml"));
        Scene scene = new Scene(hasierakoMenua.load());
        HasierakoMenuaController hmc = hasierakoMenua.getController();
        Stage usingStage = this.getUsingStage();
        hmc.setErabiltzailea(erab);
        hmc.setUsingStage(usingStage);
        usingStage.setScene(scene);
        usingStage.setTitle("Hasierako Menua");
        usingStage.show();
    }

    public void initialize() {
        ObservableList<Mahaia> mahaiak = MahaiaDbKudeaketa.getAllMahaiak();
        if (mahaiak != null && !mahaiak.isEmpty()) {
            mahaiaTaula.setItems(mahaiak);
        } else {
            System.out.println("No hay mesas disponibles para mostrar.");
        }


        komentsalColumn.setCellValueFactory(new PropertyValueFactory<>("gehienezkoKopurua"));
        izenaColumn.setCellValueFactory(new PropertyValueFactory<>("zenbakia"));

        izenaColumn.setPrefWidth(100);
        komentsalColumn.setPrefWidth(100);

        mahaiaTaula.setPrefWidth(300);
        mahaiaTaula.setPrefHeight(150);
    }

    public void onMahaiaGehituBotoiaClick(ActionEvent actionEvent) throws IOException {
        String erab = erabiltzailea.getText();

        FXMLLoader mahaiaGehitu = new FXMLLoader(App.class.getResource("mahaiaGehitu.fxml"));
        Scene scene = new Scene(mahaiaGehitu.load());
        MahaiaGehituController mgc = mahaiaGehitu.getController();
        Stage usingStage = this.getUsingStage();
        mgc.setErabiltzailea(erab);
        mgc.setUsingStage(usingStage);
        usingStage.setScene(scene);
        usingStage.setTitle("Mahaia gehitzeko galdetegia");
        usingStage.show();
    }

    public void onMahaiaEzabatuBotoiaClick(ActionEvent actionEvent) throws IOException {
        String erab = erabiltzailea.getText();

        FXMLLoader mahaiaEzabatu = new FXMLLoader(App.class.getResource("mahaiaEzabatu.fxml"));
        Scene scene = new Scene(mahaiaEzabatu.load());
        MahaiaEzabatuController mezc = mahaiaEzabatu.getController();
        Stage usingStage = this.getUsingStage();
        mezc.setErabiltzailea(erab);
        mezc.setUsingStage(usingStage);
        usingStage.setScene(scene);
        usingStage.setTitle("Mahaia ezabatzeko menua");
        usingStage.show();
    }

    public void onMahaiaEditatuBotoiaClick(ActionEvent actionEvent) throws IOException {
        String erab = erabiltzailea.getText();

        FXMLLoader mahaiaEditatu = new FXMLLoader(App.class.getResource("mahaiaEditatu.fxml"));
        Scene scene = new Scene(mahaiaEditatu.load());
        MahaiaEditatuController mec = mahaiaEditatu.getController();
        Stage usingStage = this.getUsingStage();
        mec.setErabiltzailea(erab);
        mec.setUsingStage(usingStage);
        usingStage.setScene(scene);
        usingStage.setTitle("Mahaia editatzeko galdetegia");
        usingStage.show();
    }
}