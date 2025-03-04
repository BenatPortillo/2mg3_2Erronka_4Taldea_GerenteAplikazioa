package com.example.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LangileMenuaController extends BaseController {
    @FXML
    private Label erabiltzailea;

    @FXML
    private TableView<Langilea> tableView;

    @FXML
    private TableColumn<Langilea, String> izenaColumn;
    @FXML
    private TableColumn<Langilea, String> emailColumn;
    @FXML
    private TableColumn<Langilea, String> lanPostuaColumn;

    @FXML
    public void setErabiltzailea(String izena) {
        erabiltzailea.setText(izena);
    }

    public void initialize() {

        ObservableList<Langilea> langileak = LangileaDbKudeaketa.getAllLangileak();


        tableView.setItems(langileak);


        izenaColumn.setCellValueFactory(new PropertyValueFactory<>("izena"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("korreoa"));
        lanPostuaColumn.setCellValueFactory(new PropertyValueFactory<>("postua"));

        izenaColumn.setPrefWidth(100);
        emailColumn.setPrefWidth(100);
        lanPostuaColumn.setPrefWidth(100);

        tableView.setPrefWidth(300);
        tableView.setPrefHeight(150);

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

    public void onLangileaGehituBotoiaClick(ActionEvent actionEvent) throws IOException {
        String erab = erabiltzailea.getText();

        FXMLLoader langileaGehitu = new FXMLLoader(App.class.getResource("langileaGehitu.fxml"));
        Scene scene = new Scene(langileaGehitu.load());
        LangileaGehituController lgc = langileaGehitu.getController();
        Stage usingStage = this.getUsingStage();
        lgc.setErabiltzailea(erab);
        lgc.setUsingStage(usingStage);
        usingStage.setScene(scene);
        usingStage.setTitle("Langilea gehitzeko galdetegia");
        usingStage.show();
    }

    public void onLangileaEzabatuBotoiaClick(ActionEvent actionEvent) throws IOException {
        String erab = erabiltzailea.getText();

        FXMLLoader langileaEzabatu = new FXMLLoader(App.class.getResource("langileaEzabatu.fxml"));
        Scene scene = new Scene(langileaEzabatu.load());
        LangileaEzabatuController lec = langileaEzabatu.getController();
        Stage usingStage = this.getUsingStage();
        lec.setErabiltzailea(erab);
        lec.setUsingStage(usingStage);
        usingStage.setScene(scene);
        usingStage.setTitle("Langilea ezabatzeko menua");
        usingStage.show();
    }

    public void onLangileaEditatuBotoiaClick(ActionEvent actionEvent) throws IOException {
        String erab = erabiltzailea.getText();

        FXMLLoader langileaEditatu = new FXMLLoader(App.class.getResource("langileaEditatu.fxml"));
        Scene scene = new Scene(langileaEditatu.load());
        LangileaEditatuController ledc = langileaEditatu.getController();
        Stage usingStage = this.getUsingStage();
        ledc.setErabiltzailea(erab);
        ledc.setUsingStage(usingStage);
        usingStage.setScene(scene);
        usingStage.setTitle("Langilea editatzeko galdetegia");
        usingStage.show();
    }
}
