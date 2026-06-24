package Clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DetalleOrdenDAO {

    private int obtenerSiguienteLinea(Connection con, int idOrden) throws SQLException {
        String sql = "SELECT COALESCE(MAX(idLinea), 0) + 1 FROM detalleOrden WHERE idOrden = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idOrden);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 1;
    }

    public void insertarDetalle(Connection con, int idOrden, DetalleOrden d) throws SQLException {

        int idLinea = obtenerSiguienteLinea(con, idOrden);

        String sql;

        if (d.getTipo().equals("PRODUCTO")) {
            sql = "INSERT INTO detalleOrden (idOrden, idLinea, idProducto, cantidad, precioUnitario, modified_by) VALUES (?, ?, ?, ?, ?, ?)";
        } else {
            sql = "INSERT INTO detalleOrden (idOrden, idLinea, idCombo, cantidad, precioUnitario, modified_by) VALUES (?, ?, ?, ?, ?, ?)";
        }

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idOrden);
            ps.setInt(2, idLinea);
            ps.setInt(3, d.getId());
            ps.setInt(4, d.getCantidad());
            ps.setDouble(5, d.getPrecio());
            if (d.getModifiedBy() != null) {
                ps.setString(6, d.getModifiedBy());
            } else {
                ps.setNull(6, java.sql.Types.VARCHAR);
            }
            ps.executeUpdate();
        }
    }

    public List<Object[]> listarDetallePorOrden(int idOrden) {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT d.idLinea, "
                + "CASE WHEN d.idProducto IS NOT NULL THEN 'PRODUCTO' ELSE 'COMBO' END AS tipo, "
                + "COALESCE(d.idProducto, d.idCombo) AS idItem, "
                + "COALESCE(p.nombre, c.combo) AS nombre, "
                + "d.precioUnitario AS precio, "
                + "d.cantidad AS cantidad, "
                + "(d.precioUnitario * d.cantidad) AS subtotal, "
                + "d.modified_by "
                + "FROM detalleOrden d "
                + "LEFT JOIN producto p ON d.idProducto = p.idProducto "
                + "LEFT JOIN combo c ON d.idCombo = c.idCombo "
                + "WHERE d.idOrden = ? "
                + "ORDER BY d.idLinea";

        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idOrden);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String modifiedBy = rs.getString("modified_by");
                    lista.add(new Object[]{
                        rs.getInt("idLinea"),
                        rs.getString("tipo"),
                        rs.getInt("idItem"),
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getInt("cantidad"),
                        rs.getDouble("subtotal"),
                        modifiedBy != null ? modifiedBy : ""
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void eliminarDetallePorOrden(Connection con, int idOrden) throws SQLException {
        String sql = "DELETE FROM detalleOrden WHERE idOrden = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idOrden);
            ps.executeUpdate();
        }
    }

}
