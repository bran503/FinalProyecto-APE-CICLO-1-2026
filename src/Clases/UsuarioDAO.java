package Clases;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
public class UsuarioDAO {

    public Usuario login(String user, String password) {
        String sql = """
            SELECT u.idUsuario, u.nombre, u.apellido, u.usuario,
                   u.idRol, r.rol
            FROM usuario u
            INNER JOIN rol r ON u.idRol = r.idRol
            WHERE u.usuario = ?
            AND u.contrasenia = SHA2(?, 256)
        """;

        try (
                Connection con = Conexion.conectar();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, user);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("idUsuario"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setApellido(rs.getString("apellido"));
                    usuario.setUsuario(rs.getString("usuario"));
                    usuario.setIdRol(rs.getInt("idRol"));
                    usuario.setRol(rs.getString("rol"));
                    return usuario;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        String sql = """
            SELECT u.idUsuario, u.nombre, u.apellido, u.usuario,
                   u.idRol, r.rol
            FROM usuario u
            INNER JOIN rol r ON u.idRol = r.idRol
            WHERE u.idRol != 1
            ORDER BY u.idUsuario
        """;

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("idUsuario"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setUsuario(rs.getString("usuario"));
                u.setIdRol(rs.getInt("idRol"));
                u.setRol(rs.getString("rol"));
                lista.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Usuario> listarAdministradores() {
        List<Usuario> lista = new ArrayList<>();
        String sql = """
            SELECT u.idUsuario, u.nombre, u.apellido, u.usuario,
                   u.idRol, r.rol
            FROM usuario u
            INNER JOIN rol r ON u.idRol = r.idRol
            WHERE u.idRol = 1
            ORDER BY u.idUsuario
        """;

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("idUsuario"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setUsuario(rs.getString("usuario"));
                u.setIdRol(rs.getInt("idRol"));
                u.setRol(rs.getString("rol"));
                lista.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Usuario> buscarUsuarios(String texto) {
        List<Usuario> lista = new ArrayList<>();
        String sql = """
            SELECT u.idUsuario, u.nombre, u.apellido, u.usuario,
                   u.idRol, r.rol
            FROM usuario u
            INNER JOIN rol r ON u.idRol = r.idRol
            WHERE (u.nombre LIKE ? OR u.apellido LIKE ? OR u.usuario LIKE ? OR r.rol LIKE ?)
            AND u.idRol != 1
            ORDER BY u.idUsuario
        """;

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String patron = "%" + texto + "%";
            ps.setString(1, patron);
            ps.setString(2, patron);
            ps.setString(3, patron);
            ps.setString(4, patron);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Usuario u = new Usuario();
                    u.setIdUsuario(rs.getInt("idUsuario"));
                    u.setNombre(rs.getString("nombre"));
                    u.setApellido(rs.getString("apellido"));
                    u.setUsuario(rs.getString("usuario"));
                    u.setIdRol(rs.getInt("idRol"));
                    u.setRol(rs.getString("rol"));
                    lista.add(u);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean agregarUsuario(Usuario u) {
        String sql = "INSERT INTO usuario (nombre, apellido, usuario, contrasenia, idRol) VALUES (?, ?, ?, SHA2(?, 256), ?)";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getUsuario());
            ps.setString(4, u.getContrasenia());
            ps.setInt(5, u.getIdRol());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean actualizarUsuario(Usuario u) {
        String sql;
        if (u.getContrasenia() != null && !u.getContrasenia().isEmpty()) {
            sql = "UPDATE usuario SET nombre = ?, apellido = ?, usuario = ?, contrasenia = SHA2(?, 256), idRol = ? WHERE idUsuario = ?";
        } else {
            sql = "UPDATE usuario SET nombre = ?, apellido = ?, usuario = ?, idRol = ? WHERE idUsuario = ?";
        }

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getUsuario());
            if (u.getContrasenia() != null && !u.getContrasenia().isEmpty()) {
                ps.setString(4, u.getContrasenia());
                ps.setInt(5, u.getIdRol());
                ps.setInt(6, u.getIdUsuario());
            } else {
                ps.setInt(4, u.getIdRol());
                ps.setInt(5, u.getIdUsuario());
            }

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean eliminarUsuario(int idUsuario) {
        String sql = "DELETE FROM usuario WHERE idUsuario = ?";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
