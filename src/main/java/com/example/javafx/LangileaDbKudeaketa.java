package com.example.javafx;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.javafx.FuntzioLaguntzaileak.mezuaPantailaratu;

public class LangileaDbKudeaketa {

    public static int erabiltzaileaKomprobatu(String erab, String pasa) {
        String query = "SELECT postua FROM langilea WHERE izena = ? AND pasahitza = ?";

        try (Connection conn = DbKonexioa.getKonexioa();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, erab);
            stmt.setString(2, pasa);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String lanPostua = rs.getString("postua");
                if ("Gerentea".equalsIgnoreCase(lanPostua)) {
                    return 1; // gerentea da
                } else {
                    return 0; // ez da gerentea
                }
            }
        } catch (SQLException e) {
            System.err.println("Errorea autentikazioan: " + e.getMessage());
        }

        return -1; // ez da existitzen edo errorea
    }

    public static ObservableList<Langilea> getAllLangileak() {
        String query = "SELECT id, dni, izena, abizena, pasahitza, korreoa, telefonoa, postua, txatBaimena FROM langilea";
        ObservableList<Langilea> langileenLista = FXCollections.observableArrayList();

        try (Connection conn = DbKonexioa.getKonexioa();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Langilea langilea = new Langilea(
                        rs.getInt("id"),
                        rs.getString("dni"),
                        rs.getString("izena"),
                        rs.getString("abizena"),
                        rs.getString("pasahitza"),
                        rs.getString("korreoa"),
                        rs.getString("telefonoa"),
                        rs.getString("postua"),
                        rs.getBoolean("txatBaimena")

                );
                langileenLista.add(langilea);
            }

        } catch (SQLException e) {
            System.err.println("Errorea datuak eskuratzerakoan: " + e.getMessage());
        }

        return langileenLista;
    }

    public static void langileaGehitu(Langilea langilea) {
        String query = "INSERT INTO langilea (dni,izena,abizena, pasahitza,korreoa,telefonoa, postua, txatBaimena) VALUES (?,?, ?, ?, ?, ?,?,?)";

        try (Connection conn = DbKonexioa.getKonexioa();
             PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Establecer los valores para los campos en la base de datos
            stmt.setString(1, langilea.getDni());
            stmt.setString(2, langilea.getIzena());
            stmt.setString(3, langilea.getAbizena());
            stmt.setString(4, langilea.getPasahitza());
            stmt.setString(5, langilea.getKorreoa());
            stmt.setString(6, langilea.getTelefonoa());
            stmt.setString(7, langilea.getPostua());
            stmt.setBoolean(8, langilea.isTxatBaimena());

            // Ejecutar la actualización de la base de datos
            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                // Obtener el ID autoincrementado después de insertar
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1); // Obtener el ID autoincremental generado
                        langilea.setId(generatedId); // Establecer el ID generado al objeto Langilea

                        // Mostrar mensaje de éxito
                        String izena = "Langilea Gehitu";
                        String mezuLuzea = "Langilea zuzen gehitu da. Honako hau da bere ID-a: " + generatedId;
                        mezuaPantailaratu(izena, mezuLuzea, Alert.AlertType.INFORMATION);
                    }
                }
            } else {
                // Si no se inserta ninguna fila, mostrar mensaje de error
                String izena = "Errorea";
                String mezuLuzea = "Ez da langilea gehitu.";
                mezuaPantailaratu(izena, mezuLuzea, Alert.AlertType.ERROR);
            }

        } catch (SQLException e) {
            // Si ocurre un error, mostrar el mensaje y trazar la excepción
            String izena = "Errorea";
            String mezuLuzea = "Errorea langilea gehitzean: " + e.getMessage();
            mezuaPantailaratu(izena, mezuLuzea, Alert.AlertType.ERROR);

            e.printStackTrace();
        }
    }

    public static void langileaEzabatu(int langileId) {
        String query = "DELETE FROM langilea WHERE id = ?"; // Query para eliminar un trabajador por ID

        try (Connection conn = DbKonexioa.getKonexioa();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, langileId); // Usar el ID del trabajador

            System.out.println("Ejecutando consulta: " + stmt.toString()); // Imprimir la consulta para depuración

            int filasAfectadas = stmt.executeUpdate();
            System.out.println("Filas afectadas: " + filasAfectadas); // Ver cuántas filas se han afectado

            if (filasAfectadas > 0) {
                String izena = "Langilea Ezabatu";
                String mezuLuzea = "Langilea arrakastaz ezabatu da.";
                mezuaPantailaratu(izena, mezuLuzea, Alert.AlertType.INFORMATION);
            } else {
                String izena = "Errorea";
                String mezuLuzea = "Ez da langilea aurkitu.";
                mezuaPantailaratu(izena, mezuLuzea, Alert.AlertType.ERROR);
            }

        } catch (SQLException e) {
            String izena = "Errorea";
            String mezuLuzea = "Errorea langilea ezabatzean: " + e.getMessage();
            mezuaPantailaratu(izena, mezuLuzea, Alert.AlertType.ERROR);
            e.printStackTrace(); // Imprimir el error completo para depuración
        }
    }
    public static boolean editatuLangilea(Langilea langilea) {
        // Consulta SQL para actualizar los datos del trabajador
        String query = "UPDATE langilea SET dni=?, izena = ?, abizena=?, pasahitza = ?,korreoa = ?,telefonoa = ?, postua = ?, txatBaimena = ? WHERE id = ?";

        try (Connection conn = DbKonexioa.getKonexioa(); // Obtención de la conexión a la base de datos
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Asignar los parámetros al PreparedStatement
            stmt.setString(1, langilea.getDni());
            stmt.setString(2, langilea.getIzena());
            stmt.setString(3, langilea.getAbizena());
            stmt.setString(4, langilea.getPasahitza());
            stmt.setString(5, langilea.getKorreoa());
            stmt.setString(6, langilea.getTelefonoa());
            stmt.setString(7, langilea.getPostua());
            stmt.setBoolean(8, langilea.isTxatBaimena());
            stmt.setInt(9, langilea.getId());

            // Ejecutar la actualización y obtener el número de filas afectadas
            int rowsAffected = stmt.executeUpdate();

            // Si se actualizaron filas, la operación fue exitosa
            return rowsAffected > 0;

        } catch (SQLException e) {
            // Captura y muestra los errores de SQL con más detalle
            System.err.println("Error al actualizar los datos del empleado con ID " + langilea.getId() + ": " + e.getMessage());
            return false;
        }
    }
    public static Langilea langileaLortuIzenaBidez(String izena) {
        String query = "SELECT id, dni, izena, abizena, pasahitza, korreoa, telefonoa, postua, txatBaimena FROM langilea WHERE izena = ?";
        Langilea langilea = null;

        try (Connection conn = DbKonexioa.getKonexioa();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, izena);  // Establecemos el nombre del trabajador para la consulta
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Si se encuentra un resultado, crear el objeto Langilea con los datos obtenidos
                langilea = new Langilea(
                        rs.getInt("id"),
                        rs.getString("dni"),
                        rs.getString("izena"),
                        rs.getString("abizena"),
                        rs.getString("pasahitza"),
                        rs.getString("korreoa"),
                        rs.getString("telefonoa"),
                        rs.getString("postua"),
                        rs.getBoolean("txatBaimena")
                );
            }

        } catch (SQLException e) {
            System.err.println("Errorea langilea eskuratzerakoan: " + e.getMessage());
        }

        return langilea;  // Devuelve el objeto Langilea si lo encontró, o null si no lo encontró
    }



}
