package com.example.javafx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class TxatController extends BaseController {

    @FXML
    private Label erabiltzailea;  // Etiqueta para el nombre de usuario
    @FXML
    private TextArea messagesArea; // Área donde se mostrarán los mensajes
    @FXML
    private TextField messageInput; // Campo de texto para escribir el mensaje
    @FXML
    private Button sendButton; // Botón para enviar el mensaje

    private PrintWriter out; // Para enviar el mensaje al servidor
    private BufferedReader in; // Para recibir los mensajes del servidor
    private Socket socket; // Conexión del cliente con el servidor

    public void setErabiltzailea(String izena) {
        erabiltzailea.setText(izena);
    }

    public void conectarAServidor(String host, int puerto) {
        new Thread(() -> {
            try {
                System.out.println("Intentando conectar al servidor en " + host + ":" + puerto);

                // Conectar con el servidor
                socket = new Socket(host, puerto);
                System.out.println("Conexión con el servidor establecida.");

                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                System.out.println("Canales de entrada y salida inicializados.");

                // Informar que la conexión ha sido establecida
                Platform.runLater(() -> messagesArea.appendText("Conectado al servidor en " + host + ":" + puerto + "\n"));

                // Escuchar mensajes del servidor
                String mensaje;
                while ((mensaje = in.readLine()) != null) {
                    System.out.println("Mensaje recibido del servidor: " + mensaje);
                    final String mensajeFinal = mensaje;
                    Platform.runLater(() -> messagesArea.appendText(mensajeFinal + "\n"));
                }

                System.out.println("El servidor ha cerrado la conexión o no hay más mensajes.");
            } catch (IOException e) {
                System.err.println("Error al conectar con el servidor: " + e.getMessage());
                Platform.runLater(() -> mostrarError("Error al conectar con el servidor", e.getMessage()));
            }
        }).start();
    }

    @FXML
    private void onSendButtonClick() {
        String mensaje = messageInput.getText();
        if (!mensaje.isEmpty()) {
            if (out != null) {
                String usuario = erabiltzailea.getText();
                String mensajeCompleto = usuario + ": " + mensaje;

                // Depuración para verificar que se está enviando el mensaje
                System.out.println("Enviando mensaje: " + mensajeCompleto);

                // Enviar mensaje al servidor
                out.println(mensajeCompleto);

                // Mostrar el mensaje localmente en el área de mensajes
                messagesArea.appendText("Tú: " + mensaje + "\n");

                // Limpiar el campo de texto
                messageInput.clear();
            } else {
                mostrarError("Conexión no establecida", "No se puede enviar el mensaje porque no hay conexión.");
            }
        } else {
            mostrarError("Mensaje vacío", "No puedes enviar un mensaje vacío.");
        }
    }

    public void cerrarConexion() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
            mostrarError("Error al cerrar conexión", e.getMessage());
        }
    }

    private void mostrarError(String titulo, String mensaje) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(titulo);
            alert.setHeaderText(null);
            alert.setContentText(mensaje);
            alert.showAndWait();
        });
    }

    @FXML
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

    @FXML
    public void initialize() {
        System.out.println("Método initialize ejecutado.");
        String host = "localhost"; // Dirección local
        int puerto = 5555; // El mismo puerto que el servidor
        try {
            conectarAServidor(host, puerto);
        } catch (Exception e) {
            mostrarError("Error al inicializar", e.getMessage());
        }
    }
}

