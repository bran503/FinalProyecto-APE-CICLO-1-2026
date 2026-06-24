package Clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ProductoDAO {

    public boolean agregar(Producto producto) {
        String sql = "INSERT INTO producto(idProducto, nombre, descripcion, precio, idCategoria) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, producto.getIdProducto());
            ps.setString(2, producto.getNombre());
            ps.setString(3, producto.getDescripcion());
            ps.setDouble(4, producto.getPrecio());
            ps.setInt(5, producto.getCategoria().getIdCategoria());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al agregar: " + e.getMessage());
            return false;
        }
    }

    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT producto.*,categoria.categoria FROM producto INNER JOIN categoria ON producto.idCategoria = categoria.idCategoria";
        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Categoria categoria = new Categoria(
                        rs.getInt("idCategoria"),
                        rs.getString("categoria")
                );

                Producto producto = new Producto();
                producto.setIdProducto(rs.getInt("idProducto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setCategoria(categoria);
                lista.add(producto);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return lista;
    }

    public List<Producto> buscarProductos(String texto) {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT producto.*, categoria.categoria "
                + "FROM producto "
                + "INNER JOIN categoria ON producto.idCategoria = categoria.idCategoria "
                + "WHERE nombre LIKE ?";

        try (Connection con = Conexion.conectar(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + texto + "%"); 
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Producto p = new Producto();
                    p.setIdProducto(rs.getInt("idProducto"));
                    p.setNombre(rs.getString("nombre"));
                    p.setDescripcion(rs.getString("descripcion"));
                    p.setPrecio(rs.getDouble("precio"));
                    
                    Categoria c = new Categoria();
                    c.setIdCategoria(rs.getInt("idCategoria"));
                    c.setCategoria(rs.getString("categoria"));
                    
                    p.setCategoria(c);
                    lista.add(p);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return lista;
    }

    public boolean eliminar(String idProducto) {
        String sql = "DELETE FROM producto WHERE idProducto=?";
        try (Connection con = Conexion.conectar(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, idProducto);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    public boolean actualizarProducto(Producto p) {
        String sql = "UPDATE producto SET nombre=?, descripcion=?, precio=?, idCategoria=? WHERE idProducto=?";
        try (Connection con = Conexion.conectar(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            ps.setDouble(3, p.getPrecio());
            ps.setInt(4, p.getCategoria().getIdCategoria());
            ps.setInt(5, p.getIdProducto());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    public int obtenerIdProducto() {
        int id = 1; // valor por defecto si no hay registros
        String sql = "SELECT MAX(idProducto) FROM Producto";
        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                id = rs.getInt(1) + 1;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return id;
    }

    public List<Producto> listarPorCategoria(int idCategoria) {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT idProducto, nombre, precio FROM producto WHERE idCategoria = ?";
        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCategoria);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getInt("idProducto"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));
                lista.add(p);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return lista;
    }

}
