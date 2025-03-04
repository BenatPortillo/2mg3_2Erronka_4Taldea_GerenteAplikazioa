package com.example.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.example.javafx.FuntzioLaguntzaileak.mezuaPantailaratu;

public class ErreserbaGehituController extends BaseController {
    @FXML
    private Label erabiltzailea;
    @FXML
    private TextField izenaField;

    @FXML
    private TextField dataField;

    @FXML
    private TextField pertsonaField;

    @FXML
    private TextField mahaiaField;

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

    public void onGehituBotoiaClick(ActionEvent actionEvent) {
        // Obtener los valores de los campos de entrada
        String erreserbaIzena = izenaField.getText();
        String dataStr = dataField.getText(); // dataStr es un String, no un Date
        String pertsonaStr = pertsonaField.getText();
        String mahaiaStr = mahaiaField.getText();

        // Depuración: Imprimir los valores de los campos
        System.out.println("Valor de izenaField: " + erreserbaIzena);
        System.out.println("Valor de dataField: " + dataStr);
        System.out.println("Valor de pertsonaField: " + pertsonaStr);
        System.out.println("Valor de mahaiaField: " + mahaiaStr);

        // Verificar que los campos no estén vacíos
        if (erreserbaIzena.isEmpty() || dataStr.isEmpty() || pertsonaStr.isEmpty() || mahaiaStr.isEmpty()) {
            String izenaError = "Errorea";
            String mezuLuzeaError = "Datu guztiak sartu behar dituzu.";
            mezuaPantailaratu(izenaError, mezuLuzeaError, Alert.AlertType.ERROR);
            return;
        }

        // Validar que el número de personas no sea 0 o negativo
        int pertsonaKopurua;
        try {
            pertsonaKopurua = Integer.parseInt(pertsonaStr);
            System.out.println("Valor de pertsonaKopurua (después de parsear): " + pertsonaKopurua); // Depuración

            if (pertsonaKopurua <= 0) {
                mezuaPantailaratu("Errorea", "Pertsona kopurua ezin da 0 edo negatiboa izan.", Alert.AlertType.ERROR);
                return;
            }
        } catch (NumberFormatException e) {
            String izenaError = "Errorea";
            String mezuLuzeaError = "Pertsona kopurua zenbaki bat izan behar da.";
            mezuaPantailaratu(izenaError, mezuLuzeaError, Alert.AlertType.ERROR);
            return;
        }

        // Verificar que el ID del "mahaia" sea un número válido
        Integer mahaiaId;
        try {
            mahaiaId = Integer.parseInt(mahaiaStr);
            System.out.println("Valor de mahaiaId (después de parsear): " + mahaiaId); // Depuración
        } catch (NumberFormatException e) {
            String izenaError = "Errorea";
            String mezuLuzeaError = "Mahaia ID bat izan behar da.";
            mezuaPantailaratu(izenaError, mezuLuzeaError, Alert.AlertType.ERROR);
            return;
        }

        // Convertir la fecha (si es posible) usando LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");  // Formato esperado
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(dataStr, formatter); // Convertir la fecha desde el String
            System.out.println("Valor de localDate (después de parsear): " + localDate); // Depuración
        } catch (Exception e) {
            String izenaError = "Errorea";
            String mezuLuzeaError = "Data ez da egokia. Erabiltzaileak 'yyyy-MM-dd' formatuan sartu behar du.";
            mezuaPantailaratu(izenaError, mezuLuzeaError, Alert.AlertType.ERROR);
            return;
        }

        // Crear un objeto Erreserba
        Erreserba erreserbaBerria = new Erreserba();
        erreserbaBerria.setErreserbaIzena(erreserbaIzena);
        erreserbaBerria.setErreserbaDate(java.sql.Date.valueOf(localDate)); // Use localDate instead of erreserbaData
        erreserbaBerria.setPertsonaKopurua(pertsonaKopurua);
        erreserbaBerria.setMahiaId(mahaiaId);

        // Depuración: Imprimir los valores antes de agregar la reserva
        System.out.println("Valores antes de agregar la reserva:");
        System.out.println("Erreserba Izena: " + erreserbaBerria.getErreserbaIzena());
        System.out.println("Fecha de reserva: " + erreserbaBerria.getErreserbaDate());
        System.out.println("Número de personas: " + erreserbaBerria.getPertsonaKopurua());
        System.out.println("ID de mesa: " + erreserbaBerria.getMahiaId());

        // Intentar agregar la reserva a la base de datos
        boolean ondo = ErreserbaDbKudeaketa.erreserbaGehitu(erreserbaBerria);

        if (ondo) {
            // Limpiar los campos después de agregar la reserva
            izenaField.clear();
            dataField.clear();
            pertsonaField.clear();
            mahaiaField.clear();  // Limpiar el TextField de mahaia

            // Navegar a la siguiente pantalla (por ejemplo, Menú de reservas)
            String erab = erabiltzailea.getText();
            FXMLLoader erreserbaMenua = new FXMLLoader(App.class.getResource("erreserbaMenua.fxml"));
            try {
                Scene scene = new Scene(erreserbaMenua.load());
                ErreserbaMenuaController emc = erreserbaMenua.getController();
                Stage usingStage = this.getUsingStage();
                emc.setErabiltzailea(erab);
                emc.setUsingStage(usingStage);
                usingStage.setScene(scene);
                usingStage.setTitle("Erreserba Menua");
                usingStage.show();
            } catch (IOException e) {
                e.printStackTrace();
                String izenaError = "Errorea";
                String mezuLuzeaError = "Ez da posible menuan sartzea.";
                mezuaPantailaratu(izenaError, mezuLuzeaError, Alert.AlertType.ERROR);
            }
        } else {
            String izenaError = "Errorea";
            String mezuLuzeaError = "Ez da erreserba gehitu.";
            mezuaPantailaratu(izenaError, mezuLuzeaError, Alert.AlertType.ERROR);
        }
    }


}
