/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Fpaneles;

import Clases.Rol;
import Clases.RolDAO;
import Clases.Usuario;
import Clases.UsuarioDAO;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class PnlAgregarusuarios extends disenos.PanelRedondeado {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private RolDAO rolDAO = new RolDAO();
    private List<Rol> roles;

    public PnlAgregarusuarios() {
        initComponents();
        setBackground(new Color(18, 18, 18));
        setBorderRadius(30);
        jScrollPane1.setOpaque(false);
        jScrollPane1.getViewport().setOpaque(false);
        jScrollPane1.getViewport().setBackground(new Color(0, 0, 0, 0));
        inicializar();
    }

    private void inicializar() {
        txtidusuario.setEditable(false);
        cargarRoles();
        listarUsuarios();
        limpiarFormulario();

        tablaTransparente1.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaTransparente1.getSelectedRow() >= 0) {
                cargarUsuarioSeleccionado();
            }
        });

        txtbuscarusuario.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                buscarUsuarios();
            }
        });

        txtnombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetter(c) && c != ' ' && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
            }
        });

        txtapellido.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetter(c) && c != ' ' && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
            }
        });

        txtusuario.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetterOrDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
            }
        });

        btnguardar.addActionListener(this::btnguardarActionPerformed);
        btneliminar.addActionListener(this::btneliminarActionPerformed);
    }

    private void cargarRoles() {
        roles = rolDAO.listarRoles();
        jComboBox1.removeAllItems();
        for (Rol r : roles) {
            if ("Administrador".equalsIgnoreCase(r.getRol())) {
                continue;
            }
            jComboBox1.addItem(r.getRol());
        }
    }

    private void listarUsuarios() {
        cargarAdministradores();
        List<Usuario> lista = usuarioDAO.listarUsuarios();
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Apellido");
        model.addColumn("Usuario");
        model.addColumn("Rol");

        for (Usuario u : lista) {
            model.addRow(new Object[]{
                u.getIdUsuario(), u.getNombre(), u.getApellido(), u.getUsuario(), u.getRol()
            });
        }
        tablaTransparente1.setModel(model);
    }

    private void cargarAdministradores() {
        List<Usuario> admins = usuarioDAO.listarAdministradores();
        javax.swing.DefaultListModel<String> model = new javax.swing.DefaultListModel<>();
        for (Usuario u : admins) {
            model.addElement(u.getNombre() + " " + u.getApellido() + " (" + u.getRol() + ")");
        }
        jtamdinistradores.setModel(model);
    }

    private void buscarUsuarios() {
        String texto = txtbuscarusuario.getText().trim();
        if (texto.isEmpty()) {
            listarUsuarios();
            return;
        }
        List<Usuario> lista = usuarioDAO.buscarUsuarios(texto);
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Apellido");
        model.addColumn("Usuario");
        model.addColumn("Rol");

        for (Usuario u : lista) {
            model.addRow(new Object[]{
                u.getIdUsuario(), u.getNombre(), u.getApellido(), u.getUsuario(), u.getRol()
            });
        }
        tablaTransparente1.setModel(model);
    }

    private void cargarUsuarioSeleccionado() {
        int fila = tablaTransparente1.getSelectedRow();
        if (fila < 0) {
            return;
        }
        txtidusuario.setText(tablaTransparente1.getValueAt(fila, 0).toString());
        txtnombre.setText(tablaTransparente1.getValueAt(fila, 1).toString());
        txtapellido.setText(tablaTransparente1.getValueAt(fila, 2).toString());
        txtusuario.setText(tablaTransparente1.getValueAt(fila, 3).toString());
        txtcontrasena.setText("");
        txtidusuario.setEnabled(false);

        String rolUsuario = tablaTransparente1.getValueAt(fila, 4).toString();
        for (int i = 0; i < jComboBox1.getItemCount(); i++) {
            if (jComboBox1.getItemAt(i).equals(rolUsuario)) {
                jComboBox1.setSelectedIndex(i);
                break;
            }
        }

        btnguardar.setEnabled(false);
        btnactualizar.setEnabled(true);
        btneliminar.setEnabled(true);
    }

    private void limpiarFormulario() {
        txtidusuario.setText("");
        txtnombre.setText("");
        txtapellido.setText("");
        txtusuario.setText("");
        txtcontrasena.setText("");
        if (jComboBox1.getItemCount() > 0) {
            jComboBox1.setSelectedIndex(0);
        }
        txtidusuario.setEnabled(true);
        btnguardar.setEnabled(true);
        btnactualizar.setEnabled(false);
        btneliminar.setEnabled(false);
        tablaTransparente1.clearSelection();
    }

    private boolean validarCampos() {
        if (txtnombre.getText().trim().isEmpty()
                || txtapellido.getText().trim().isEmpty()
                || txtusuario.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos obligatorios");
            return false;
        }
        if (btnguardar.isEnabled() && txtcontrasena.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "Ingrese una contraseña");
            return false;
        }
        return true;
    }

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {
        if (!validarCampos()) {
            return;
        }
        Usuario u = new Usuario();
        u.setNombre(txtnombre.getText().trim());
        u.setApellido(txtapellido.getText().trim());
        u.setUsuario(txtusuario.getText().trim());
        u.setContrasenia(new String(txtcontrasena.getPassword()).trim());

        String selectedRol = (String) jComboBox1.getSelectedItem();
        for (Rol r : roles) {
            if (r.getRol().equals(selectedRol)) {
                u.setIdRol(r.getIdRol());
                break;
            }
        }

        if (usuarioDAO.agregarUsuario(u)) {
            JOptionPane.showMessageDialog(this, "Usuario guardado correctamente");
            limpiarFormulario();
            listarUsuarios();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar el usuario");
        }
    }

    private void btneliminarActionPerformed(java.awt.event.ActionEvent evt) {
        if (txtidusuario.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario de la tabla");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar el usuario?", "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(txtidusuario.getText().trim());
            try {
                if (usuarioDAO.eliminarUsuario(id)) {
                    JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente");
                    limpiarFormulario();
                    listarUsuarios();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el usuario");
                }
            } catch (Exception ex) {
                if (ex.getCause() instanceof SQLIntegrityConstraintViolationException) {
                    JOptionPane.showMessageDialog(this,
                            "No se puede eliminar el usuario porque tiene órdenes registradas");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage());
                }
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaTransparente1 = new disenos.TablaTransparente();
        txtidusuario = new disenos.TextFieldRedondeado();
        txtbuscarusuario = new disenos.TextFieldRedondeado();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtamdinistradores = new javax.swing.JList<>();
        txtnombre = new disenos.TextFieldRedondeado();
        txtapellido = new disenos.TextFieldRedondeado();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtusuario = new disenos.TextFieldRedondeado();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtcontrasena = new disenos.PasswordFieldRedondeado();
        btnguardar = new disenos.BotonTransparente();
        btnlimpiar = new disenos.BotonTransparente();
        btnactualizar = new disenos.BotonTransparente();
        btneliminar = new disenos.BotonTransparente();

        tablaTransparente1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tablaTransparente1.setColorTexto(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(tablaTransparente1);

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("BUSCAR USUARIO");

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("ID USUARIO");

        jScrollPane2.setViewportView(jtamdinistradores);

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("NOMBRE");

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("APELLIDO");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("ROL");

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("USUARIO");

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("CONTRASEÑA");

        txtcontrasena.addActionListener(this::txtcontrasenaActionPerformed);

        btnguardar.setText("GUARDAR");
        btnguardar.setColorDeshabilitado(new java.awt.Color(255, 0, 0));

        btnlimpiar.setText("LIMPIAR");
        btnlimpiar.addActionListener(this::btnlimpiarActionPerformed);

        btnactualizar.setText("ACTUALIZAR");
        btnactualizar.addActionListener(this::btnactualizarActionPerformed);

        btneliminar.setText("ELIMINAR");
        btneliminar.addActionListener(this::btneliminarActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtbuscarusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtidusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtusuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtcontrasena, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtapellido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtnombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox1, 0, 455, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnlimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btneliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnguardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(48, 48, 48)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtidusuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtbuscarusuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(btnguardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtapellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btneliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel6)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtcontrasena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addComponent(btnlimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnactualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnactualizarActionPerformed
        if (txtidusuario.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario de la tabla");
            return;
        }
        if (txtnombre.getText().trim().isEmpty()
                || txtapellido.getText().trim().isEmpty()
                || txtusuario.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos obligatorios");
            return;
        }
        Usuario u = new Usuario();
        u.setIdUsuario(Integer.parseInt(txtidusuario.getText().trim()));
        u.setNombre(txtnombre.getText().trim());
        u.setApellido(txtapellido.getText().trim());
        u.setUsuario(txtusuario.getText().trim());
        String pass = new String(txtcontrasena.getPassword()).trim();
        if (!pass.isEmpty()) {
            u.setContrasenia(pass);
        }
        String selectedRol = (String) jComboBox1.getSelectedItem();
        for (Rol r : roles) {
            if (r.getRol().equals(selectedRol)) {
                u.setIdRol(r.getIdRol());
                break;
            }
        }
        if (usuarioDAO.actualizarUsuario(u)) {
            JOptionPane.showMessageDialog(this, "Usuario actualizado correctamente");
            limpiarFormulario();
            listarUsuarios();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar el usuario");
        }
    }//GEN-LAST:event_btnactualizarActionPerformed

    private void btnlimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlimpiarActionPerformed
        limpiarFormulario();
    }//GEN-LAST:event_btnlimpiarActionPerformed

    private void txtcontrasenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcontrasenaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcontrasenaActionPerformed

    private void btneliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btneliminarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private disenos.BotonTransparente btnactualizar;
    private disenos.BotonTransparente btneliminar;
    private disenos.BotonTransparente btnguardar;
    private disenos.BotonTransparente btnlimpiar;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> jtamdinistradores;
    private disenos.TablaTransparente tablaTransparente1;
    private disenos.TextFieldRedondeado txtapellido;
    private disenos.TextFieldRedondeado txtbuscarusuario;
    private disenos.PasswordFieldRedondeado txtcontrasena;
    private disenos.TextFieldRedondeado txtidusuario;
    private disenos.TextFieldRedondeado txtnombre;
    private disenos.TextFieldRedondeado txtusuario;
    // End of variables declaration//GEN-END:variables
}
