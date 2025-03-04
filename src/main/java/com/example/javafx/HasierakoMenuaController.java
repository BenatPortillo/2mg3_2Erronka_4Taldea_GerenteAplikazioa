package com.example.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import static com.example.javafx.FuntzioLaguntzaileak.mezuaPantailaratu;

public class HasierakoMenuaController extends BaseController {
    @FXML
    private Label erabiltzailea;

    private Stage usingStage;

    @FXML
    private Button eguraldiaButton;

    public void setErabiltzailea(String izena) {
        erabiltzailea.setText(izena);
    }
    public String getErabiltzailea() {
        return erabiltzailea.getText();
    }
    @FXML
    protected void onLangileakKudeatuBotoiaClick() throws IOException {
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

    public void onMahaiakKudeatuBotoiaClick(ActionEvent actionEvent) throws IOException {
        String erab = erabiltzailea.getText();

        FXMLLoader mahaiaMenua = new FXMLLoader(App.class.getResource("mahaiaMenua.fxml"));
        Scene scene = new Scene(mahaiaMenua.load());
        MahaiaMenuaController mmc = mahaiaMenua.getController();
        Stage usingStage = this.getUsingStage();
        mmc.setErabiltzailea(erab);
        mmc.setUsingStage(usingStage);
        usingStage.setScene(scene);
        usingStage.setTitle("Langile Menua");
        usingStage.show();
    }

    public void onErreserbakKudeatuBotoiaClick(ActionEvent actionEvent) throws IOException {
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

    public void onTxataBotoiaClick(ActionEvent actionEvent) throws IOException {
        String erab = erabiltzailea.getText();

        // Obtener el Langilea a partir del nombre (erabiltzailea)
        Langilea langilea = LangileaDbKudeaketa.langileaLortuIzenaBidez(erab);

        if (langilea != null && langilea.isTxatBaimena()) {
            // Si tiene permiso para el chat
            FXMLLoader txata = new FXMLLoader(getClass().getResource("txata.fxml"));
            Scene scene = new Scene(txata.load());
            TxatController tc = txata.getController();  // Obtener el controlador
            Stage usingStage = this.getUsingStage();
            tc.setErabiltzailea(erab);
            tc.setUsingStage(usingStage);
            usingStage.setScene(scene);
            usingStage.setTitle("Erreserba Menua");
            usingStage.show();
        } else {
            // Si no tiene permiso para el chat
            String izenaError = "Errorea";
            String mezuLuzeaError = "Ez duzu txat-erako baimenik.";
            mezuaPantailaratu(izenaError, mezuLuzeaError, Alert.AlertType.ERROR);
        }
    }


    public void onSaioaItxiBotoiaClick(ActionEvent actionEvent) throws IOException {
        String erab = erabiltzailea.getText();

        FXMLLoader saioaHasi = new FXMLLoader(App.class.getResource("saioaHasi.fxml"));
        Scene scene = new Scene(saioaHasi.load());
        SaioaHasiController shc = saioaHasi.getController();
        Stage usingStage = this.getUsingStage();
        shc.setErabiltzailea(erab);
        shc.setUsingStage(usingStage);
        usingStage.setScene(scene);
        usingStage.setTitle("Erreserba Menua");
        usingStage.show();
    }

    public void onEguraldiaBotoiaClick(ActionEvent actionEvent) throws XPathExpressionException, ParserConfigurationException, IOException, NoSuchAlgorithmException, KeyManagementException, TransformerException, SAXException {
        EguraldiaController ec= new EguraldiaController();
        ec.xmlDescargatuetaEditatu();
    }
}

