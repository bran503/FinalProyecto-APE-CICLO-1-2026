package Clases;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrdenDAO {
    
    public int insertarOrden(Connection con, double total, int idUsuario) throws SQLException {
        String sql = "INSERT INTO orden (total, idUsuario, estado) VALUES (?, ?, 'procesada')";

        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDouble(1, total);
            ps.setInt(2, idUsuario);
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                throw new SQLException("No se pudo obtener el ID de la orden generada");
            }
        }
    }

    public List<Object[]> listarOrdenes() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT o.idOrden, DATE_FORMAT(o.fechaHora, '%d/%m/%Y %H:%i') AS fecha, u.usuario, o.total, o.estado "
                + "FROM orden o "
                + "INNER JOIN usuario u ON o.idUsuario = u.idUsuario "
                + "ORDER BY o.idOrden DESC";

        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getInt("idOrden"),
                    rs.getString("fecha"),
                    rs.getString("usuario"),
                    rs.getDouble("total"),
                    rs.getString("estado")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Object[]> buscarOrdenes(String texto) {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT o.idOrden, DATE_FORMAT(o.fechaHora, '%d/%m/%Y %H:%i') AS fecha, u.usuario, o.total, o.estado "
                + "FROM orden o "
                + "INNER JOIN usuario u ON o.idUsuario = u.idUsuario "
                + "WHERE CAST(o.idOrden AS CHAR) LIKE ? "
                + "OR DATE_FORMAT(o.fechaHora, '%e/%c/%Y') LIKE ? "
                + "ORDER BY o.idOrden DESC";

        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + texto + "%");
            ps.setString(2, "%" + texto + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Object[]{
                        rs.getInt("idOrden"),
                        rs.getString("fecha"),
                        rs.getString("usuario"),
                        rs.getDouble("total"),
                        rs.getString("estado")
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }



    public boolean actualizarOrden(Connection con, int idOrden, double total) throws SQLException {
        String sql = "UPDATE orden SET total = ? WHERE idOrden = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, total);
            ps.setInt(2, idOrden);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminarOrden(Connection con, int idOrden) throws SQLException {
        String sql = "DELETE FROM orden WHERE idOrden = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idOrden);
            return ps.executeUpdate() > 0;
        }
    }

    public String getEstadoOrden(int idOrden) {
        String sql = "SELECT estado FROM orden WHERE idOrden = ?";
        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idOrden);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString("estado");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean actualizarEstado(int idOrden, String estado) {
        String sql = "UPDATE orden SET estado = ? WHERE idOrden = ?";
        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setInt(2, idOrden);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getFechaFormateada(int idOrden) {
        String sql = "SELECT DATE_FORMAT(fechaHora, '%d-%m-%Y') FROM orden WHERE idOrden = ?";
        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idOrden);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}