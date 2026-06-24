package Clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RolDAO {

    public List<Rol> listarRoles() {
        List<Rol> lista = new ArrayList<>();
        String sql = "SELECT idRol, rol FROM rol ORDER BY rol";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Rol(rs.getInt("idRol"), rs.getString("rol")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
