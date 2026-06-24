package Clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.sql.ResultSet;

public class PermisoDAO {

    public ArrayList<String> obtenerPermisosPorRol(int idRol) {

        ArrayList<String> permisos = new ArrayList<>();

        String sql = """
            SELECT p.permiso
            FROM permiso p
            INNER JOIN rolPermiso rp
                ON p.idPermiso = rp.idPermiso
            WHERE rp.idRol = ?
        """;

        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idRol);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                permisos.add(rs.getString("permiso"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return permisos;
    }

    public boolean guardarPermisosPorRol(int idRol, ArrayList<String> permisos) {
        String deleteSql = "DELETE FROM rolPermiso WHERE idRol = ?";
        String insertSql = "INSERT INTO rolPermiso (idRol, idPermiso) VALUES (?, "
                + "(SELECT idPermiso FROM permiso WHERE permiso = ?))";

        try (Connection con = Conexion.conectar()) {
            con.setAutoCommit(false);

            try (PreparedStatement psDelete = con.prepareStatement(deleteSql)) {
                psDelete.setInt(1, idRol);
                psDelete.executeUpdate();
            }

            try (PreparedStatement psInsert = con.prepareStatement(insertSql)) {
                for (String permiso : permisos) {
                    psInsert.setInt(1, idRol);
                    psInsert.setString(2, permiso);
                    psInsert.executeUpdate();
                }
            }

            con.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
