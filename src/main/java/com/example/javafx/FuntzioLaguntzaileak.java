package com.example.javafx;

import javafx.scene.control.Alert;

public class FuntzioLaguntzaileak {

    public static void mezuaPantailaratu(String izena, String mezuLuzea, Alert.AlertType mota) {
        Alert alerta = new Alert(mota);
        alerta.setTitle(izena);
        alerta.setHeaderText(null); // Header hutsa
        alerta.setContentText(mezuLuzea); // Mezu printzipala
        alerta.showAndWait(); // mezua pantailaratu eta itxi
    }

}
