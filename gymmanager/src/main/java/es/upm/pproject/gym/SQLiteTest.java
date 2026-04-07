package es.upm.pproject.gym;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class SQLiteTest {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:gym.db";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            System.out.println("✅ Conectado a SQLite");

            // Crear tabla
            String createTable = "CREATE TABLE IF NOT EXISTS usuarios (" +
                                 "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                 "nombre TEXT NOT NULL" +
                                 ");";
            stmt.execute(createTable);

            // Insertar datos
            String insert = "INSERT INTO usuarios(nombre) VALUES('Juan');";
            stmt.execute(insert);

            // Consultar datos
            String query = "SELECT * FROM usuarios;";
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("📋 Usuarios:");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " - " + rs.getString("nombre"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
