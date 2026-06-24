package Fpaneles;

import Clases.Categoria;
import Clases.CategoriaDAO;
import Clases.Producto;
import Clases.ProductoDAO;
import java.awt.Color;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PnlProductos extends disenos.PanelRedondeado {

    private ProductoDAO productoDAO = new ProductoDAO();
    private CategoriaDAO categoriaDAO = new CategoriaDAO();
    private List<Categoria> categorias;

    public PnlProductos() {
        initComponents();
        setBackground(new Color(18, 18, 18));
        setBorderRadius(30);
        jScrollPane1.setOpaque(false);
        jScrollPane1.getViewport().setOpaque(false);
        jScrollPane1.getViewport().setBackground(new Color(0, 0, 0, 0));

        btnguardar.addActionListener(e -> guardarProducto());
        btnactualizar.addActionListener(e -> actualizarProducto());
        btneliminar.addActionListener(e -> eliminarProducto());
        btnlimpiar.addActionListener(e -> limpiar());
        btnnuevo.addActionListener(e -> nuevoProducto());

        tblproductos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarCamposDesdeTabla();
            }
        });

        txtbuscarproducto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                buscarProductos();
            }
        });

        txtnombreproducto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetterOrDigit(c) && c != ' ' && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
            }
        });

        txtdescripcion.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetterOrDigit(c) && c != ' ' && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
            }
        });

        txtprecio.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '.' && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
            }
        });

        listarCategorias();
        listarProductos();
    }

    private void nuevoProducto() {
        int id = productoDAO.obtenerIdProducto();
        txtxidnuevo.setText(String.valueOf(id));
        txtnombreproducto.requestFocus();
    }

    private void guardarProducto() {
        if (txtxidnuevo.getText().isEmpty() || txtnombreproducto.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete los campos obligatorios (ID y Nombre)");
            return;
        }
        try {
            Producto p = new Producto();
            p.setIdProducto(Integer.parseInt(txtxidnuevo.getText()));
            p.setNombre(txtnombreproducto.getText());
            p.setDescripcion(txtdescripcion.getText());
            p.setPrecio(Double.parseDouble(txtprecio.getText()));

            Categoria c = categorias.get(cmbcategoria.getSelectedIndex());
            p.setCategoria(c);

            if (productoDAO.agregar(p)) {
                JOptionPane.showMessageDialog(this, "Producto guardado correctamente");
                listarProductos();
                limpiarCampos();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un número válido");
        }
    }

    private void actualizarProducto() {
        if (txtxidnuevo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto de la tabla");
            return;
        }
        try {
            Producto p = new Producto();
            p.setIdProducto(Integer.parseInt(txtxidnuevo.getText()));
            p.setNombre(txtnombreproducto.getText());
            p.setDescripcion(txtdescripcion.getText());
            p.setPrecio(Double.parseDouble(txtprecio.getText()));

            Categoria c = categorias.get(cmbcategoria.getSelectedIndex());
            p.setCategoria(c);

            if (productoDAO.actualizarProducto(p)) {
                JOptionPane.showMessageDialog(this, "Producto actualizado correctamente");
                listarProductos();
                limpiarCampos();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un número válido");
        }
    }

    private void eliminarProducto() {
        int fila = tblproductos.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto de la tabla");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar el producto?", "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String id = tblproductos.getValueAt(fila, 0).toString();
            if (productoDAO.eliminar(id)) {
                JOptionPane.showMessageDialog(this, "Producto eliminado correctamente");
                listarProductos();
                limpiarCampos();
            }
        }
    }

    private void limpiar() {
        limpiarCampos();
    }

    private void listarCategorias() {
        categorias = categoriaDAO.listarCategorias();
        cmbcategoria.removeAllItems();
        for (Categoria c : categorias) {
            cmbcategoria.addItem(c.getCategoria());
        }
    }

    private void listarProductos() {
        List<Producto> productos = productoDAO.listar();
        mostrarProductos(productos);
    }

    private void mostrarProductos(List<Producto> productos) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Descripción");
        model.addColumn("Precio");
        model.addColumn("Categoría");

        for (Producto p : productos) {
            model.addRow(new Object[]{
                p.getIdProducto(),
                p.getNombre(),
                p.getDescripcion(),
                p.getPrecio(),
                p.getCategoria() != null ? p.getCategoria().getCategoria() : ""
            });
        }
        tblproductos.setModel(model);
    }

    private void buscarProductos() {
        String texto = txtbuscarproducto.getText().trim();
        if (texto.isEmpty()) {
            listarProductos();
            return;
        }
        List<Producto> productos = productoDAO.buscarProductos(texto);
        mostrarProductos(productos);
    }

    private void cargarCamposDesdeTabla() {
        int fila = tblproductos.getSelectedRow();
        if (fila < 0) return;

        txtxidnuevo.setText(tblproductos.getValueAt(fila, 0).toString());
        txtnombreproducto.setText(tblproductos.getValueAt(fila, 1).toString());
        txtdescripcion.setText(tblproductos.getValueAt(fila, 2) != null ? tblproductos.getValueAt(fila, 2).toString() : "");
        txtprecio.setText(tblproductos.getValueAt(fila, 3).toString());

        String cat = tblproductos.getValueAt(fila, 4).toString();
        for (int i = 0; i < categorias.size(); i++) {
            if (categorias.get(i).getCategoria().equals(cat)) {
                cmbcategoria.setSelectedIndex(i);
                break;
            }
        }
    }

    private void limpiarCampos() {
        txtxidnuevo.setText("");
        txtnombreproducto.setText("");
        txtdescripcion.setText("");
        txtprecio.setText("");
        txtbuscarproducto.setText("");
        if (!categorias.isEmpty()) {
            cmbcategoria.setSelectedIndex(0);
        }
        tblproductos.clearSelection();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblproductos = new disenos.TablaTransparente();
        txtxidnuevo = new disenos.TextFieldRedondeado();
        txtdescripcion = new disenos.TextFieldRedondeado();
        txtprecio = new disenos.TextFieldRedondeado();
        cmbcategoria = new javax.swing.JComboBox<>();
        txtbuscarproducto = new disenos.TextFieldRedondeado();
        btnguardar = new disenos.BotonTransparente();
        btnactualizar = new disenos.BotonTransparente();
        btneliminar = new disenos.BotonTransparente();
        btnlimpiar = new disenos.BotonTransparente();
        txtnombreproducto = new disenos.TextFieldRedondeado();
        btnnuevo = new disenos.BotonTransparente();
        lblnombre = new javax.swing.JLabel();
        lbldescripcion = new javax.swing.JLabel();
        lblprecio = new javax.swing.JLabel();
        lblbuscarproducto = new javax.swing.JLabel();
        lblcategoria = new javax.swing.JLabel();

        tblproductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblproductos.setColorTexto(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(tblproductos);

        txtxidnuevo.setEditable(false);

        cmbcategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnguardar.setText("GUARDAR");

        btnactualizar.setText("ACTUALIZAR");

        btneliminar.setText("ELIMINAR");

        btnlimpiar.setText("LIMPIAR");

        btnnuevo.setText("NUEVO");

        lblnombre.setBackground(new java.awt.Color(255, 255, 255));
        lblnombre.setForeground(new java.awt.Color(255, 255, 255));
        lblnombre.setText("NOMBRE");

        lbldescripcion.setBackground(new java.awt.Color(255, 255, 255));
        lbldescripcion.setForeground(new java.awt.Color(255, 255, 255));
        lbldescripcion.setText("DESCRIPCION");

        lblprecio.setBackground(new java.awt.Color(255, 255, 255));
        lblprecio.setForeground(new java.awt.Color(255, 255, 255));
        lblprecio.setText("PRECIO");

        lblbuscarproducto.setBackground(new java.awt.Color(255, 255, 255));
        lblbuscarproducto.setForeground(new java.awt.Color(255, 255, 255));
        lblbuscarproducto.setText("BUSCAR PRODUCTO");

        lblcategoria.setBackground(new java.awt.Color(255, 255, 255));
        lblcategoria.setForeground(new java.awt.Color(255, 255, 255));
        lblcategoria.setText("CATEGORIA");

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
                            .addComponent(cmbcategoria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtprecio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtbuscarproducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtnombreproducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtdescripcion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtxidnuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(26, 26, 26)
                                        .addComponent(btnnuevo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnguardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btneliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnlimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 268, Short.MAX_VALUE)))
                        .addGap(64, 64, 64)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblnombre)
                            .addComponent(lbldescripcion)
                            .addComponent(lblprecio)
                            .addComponent(lblbuscarproducto)
                            .addComponent(lblcategoria))
                        .addGap(58, 58, 58)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtxidnuevo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnnuevo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtnombreproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblnombre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtdescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbldescripcion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtprecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblprecio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbcategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblcategoria))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtbuscarproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblbuscarproducto))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnguardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btneliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnlimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private disenos.BotonTransparente btnactualizar;
    private disenos.BotonTransparente btneliminar;
    private disenos.BotonTransparente btnguardar;
    private disenos.BotonTransparente btnlimpiar;
    private disenos.BotonTransparente btnnuevo;
    private javax.swing.JComboBox<String> cmbcategoria;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblbuscarproducto;
    private javax.swing.JLabel lblcategoria;
    private javax.swing.JLabel lbldescripcion;
    private javax.swing.JLabel lblnombre;
    private javax.swing.JLabel lblprecio;
    private disenos.TablaTransparente tblproductos;
    private disenos.TextFieldRedondeado txtbuscarproducto;
    private disenos.TextFieldRedondeado txtdescripcion;
    private disenos.TextFieldRedondeado txtnombreproducto;
    private disenos.TextFieldRedondeado txtprecio;
    private disenos.TextFieldRedondeado txtxidnuevo;
    // End of variables declaration//GEN-END:variables
}
