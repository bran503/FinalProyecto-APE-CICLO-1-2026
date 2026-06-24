package Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author brand
 */
public class Conexion {
   
    private static final String URL = "jdbc:mariadb://localhost:3306/negocio2";
    private static final String USER = "root";
    private static final String PASSWORD = ""; 
    private static Connection conexion = null;
    
    public static Connection conectar() {
        
        try {
            if (conexion == null || conexion.isClosed()) {
                Class.forName("org.mariadb.jdbc.Driver"); // Carga el driver
                conexion = DriverManager.getConnection(URL, USER, PASSWORD);
                //JOptionPane.showMessageDialog(null,"? Conexi�n establecida correctamente.");
            }
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null,"No se encontro el driver JDBC de MariaDB");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error al conectar: " + e.getMessage());
        }
        return conexion;
    }
    
    public static void desconectar() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                JOptionPane.showMessageDialog(null,"? Conexion cerrada.");
            }
        } catch (SQLException e) {
             JOptionPane.showMessageDialog(null,"? Error al cerrar la conexion: " + e.getMessage());
        }
    }

    
}