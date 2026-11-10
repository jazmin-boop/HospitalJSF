package conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    private Connection con;

    public Connection getConexion() {
        try {
            // Soporta tanto drivers modernos com.mysql.cj.jdbc.Driver como anteriores com.mysql.jdbc.Driver
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                Class.forName("com.mysql.jdbc.Driver");
            }

            // Intenta conectar primero con la contraseña configurada en XAMPP (1234) o vacía ("")
            try {
                con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/hospitalbd?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                    "root",
                    "1234"
                );
            } catch (Exception ex) {
                con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/hospitalbd?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                    "root",
                    ""
                );
            }
            System.out.println("Conexion correcta a hospitalbd");
        } catch (Exception e) {
            System.out.println("Error de conexion: " + e);
        }
        return con;
    }
}
