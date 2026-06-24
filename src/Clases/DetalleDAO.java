package Clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class DetalleDAO {

    public void guardarDetalle(int idCombo, List<Detalle> listaDetalle) {
        String sql = "INSERT INTO detalle (idCombo, idProducto, cantidad) VALUES (?, ?, ?)";
        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            for (Detalle elemento : listaDetalle) {
                ps.setInt(1, idCombo);
                ps.setInt(2, elemento.getProducto().getIdProducto());
                ps.setInt(3, elemento.getCantidad());
                ps.executeUpdate();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public List<Detalle> listarPorCombo(int idCombo) {
        List<Detalle> lista = new ArrayList<>();

        String sql = "SELECT d.idProducto, p.nombre, p.precio, d.cantidad "
                + "FROM detalle d "
                + "INNER JOIN producto p ON d.idProducto = p.idProducto "
                + "WHERE d.idCombo = ?";

        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCombo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Producto p = new Producto();
                    p.setIdProducto(rs.getInt("idProducto"));
                    p.setNombre(rs.getString("nombre")); // 🔥 importante
                    p.setPrecio(rs.getDouble("precio"));

                    Detalle d = new Detalle();
                    d.setProducto(p);
                    d.setCantidad(rs.getInt("cantidad"));

                    lista.add(d);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void eliminarDetallePorCombo(int idCombo) {
        String sql = "DELETE FROM detalle WHERE idCombo = ?";

        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCombo);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
