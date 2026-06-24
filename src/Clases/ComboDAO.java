package Clases;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class ComboDAO {

    public int obtenerIdCombo() {

        int id = 1; // valor por defecto si no hay registros

        String sql = "SELECT MAX(idCombo) FROM combo";

        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {

                id = rs.getInt(1) + 1;

            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, e);

        }

        return id;

    }

    public int guardarCombo(int idCombo, String combo, double precioCombo) {
        String sql = "INSERT INTO combo (idCombo, combo, precioCombo) VALUES (?, ?, ?)";
        try (
                Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql);) {
            ps.setInt(1, idCombo);
            ps.setString(2, combo);
            ps.setDouble(3, precioCombo);
            ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return 0; //si falló, devuelve cero, sabemos que el idCombo inicia en 1
        }
        return idCombo;
    }

    public List<Object[]> listarCombos() {
        List<Object[]> lista = new ArrayList<>();

        String sql = "SELECT idCombo, combo, precioCombo FROM combo";

        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getInt("idCombo"),
                    rs.getString("combo"),
                    rs.getDouble("precioCombo")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Object[]> buscarCombos(String texto) {
        List<Object[]> lista = new ArrayList<>();

        String sql = "SELECT idCombo, combo, precioCombo FROM combo WHERE combo LIKE ?";

        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + texto + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Object[]{
                        rs.getInt("idCombo"),
                        rs.getString("combo"),
                        rs.getDouble("precioCombo")
                    });
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public int actualizarCombo(int idCombo, String combo, double precioCombo) {
        String sql = "UPDATE combo SET combo = ?, precioCombo = ? WHERE idCombo = ?";

        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, combo);
            ps.setDouble(2, precioCombo);
            ps.setInt(3, idCombo);

            return ps.executeUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return 0;
        }
    }

    public void eliminarCombo(int idCombo) {
        String sql = "DELETE FROM combo WHERE idCombo = ?";

        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCombo);
            ps.executeUpdate();

            javax.swing.JOptionPane.showMessageDialog(null, "Combo eliminado correctamente");

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error al eliminar combo: " + e);
        }
    }

    public List<Combo> listar() {
        List<Combo> lista = new ArrayList<>();
        String sql = "SELECT * FROM combo";
        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Combo combo = new Combo();
                combo.setIdCombo(rs.getInt("idCombo"));
                combo.setCombo(rs.getString("combo"));
                combo.setPrecio(rs.getDouble("precioCombo"));
                lista.add(combo);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return lista;
    }
}
