package Clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {
    
        public List<Categoria> listarCategorias() {
        List<Categoria> lista = new ArrayList<>();

        String sql = "SELECT idCategoria, categoria FROM categoria ORDER BY categoria";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setIdCategoria(rs.getInt("idCategoria"));
                categoria.setCategoria(rs.getString("categoria"));
                lista.add(categoria);
            }

        } catch (Exception e) {
            System.out.println("Error al listar las cateogorías: " + e.getMessage());
        }

        return lista;
    }

}
