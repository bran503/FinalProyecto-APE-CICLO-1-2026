/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Fpaneles;

import Clases.ComboDAO;
import Clases.Detalle;
import Clases.DetalleDAO;
import Clases.Producto;
import Clases.ProductoDAO;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PnlCombos extends disenos.PanelRedondeado {

    private ComboDAO comboDAO = new ComboDAO();
    private DetalleDAO detalleDAO = new DetalleDAO();
    private ProductoDAO productoDAO = new ProductoDAO();
    private boolean modoCombo = false;
    private List<Detalle> detalleActual = new ArrayList<>();

    public PnlCombos() {
        initComponents();
        setBackground(new Color(18, 18, 18));
        setBorderRadius(30);
        jScrollPane1.setOpaque(false);
        jScrollPane1.getViewport().setOpaque(false);
        jScrollPane1.getViewport().setBackground(new Color(0, 0, 0, 0));
        jScrollPane2.setOpaque(false);
        jScrollPane2.getViewport().setOpaque(false);
        jScrollPane2.getViewport().setBackground(new Color(0, 0, 0, 0));
        inicializar();
    }

    private void inicializar() {
        botonTransparente1.addActionListener(e -> nuevoCombo());
        btnguardarcombo.addActionListener(e -> guardarCombo());
        btnactualizarcombo.addActionListener(e -> actualizarCombo());
        btneliminarcombo.addActionListener(e -> eliminarCombo());
        btnlimpiarcombo.addActionListener(e -> limpiar());
        btnbuscarcombo.addActionListener(e -> buscarCombo());

        tblproductos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (modoCombo && e.getClickCount() == 1) {
                    cargarComboSeleccionado();
                } else if (!modoCombo && e.getClickCount() == 2) {
                    agregarProductoAlDetalle();
                }
            }
        });

        tbldetalle.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    eliminarProductoDelDetalle();
                }
            }
        });

        txtbuscombo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                buscar();
            }
        });

        txtnombrecombo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetterOrDigit(c) && c != ' ' && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
            }
        });

        txtpreciocombo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '.' && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
            }
        });

        lblbuscar.setText("BUSCAR PRODUCTO");
        listarProductos();
    }

    private void nuevoCombo() {
        int id = comboDAO.obtenerIdCombo();
        txtidcombo.setText(String.valueOf(id));
        txtnombrecombo.setText("");
        txtpreciocombo.setText("");
        txttotal.setText("");
        detalleActual.clear();
        mostrarDetalleActual();
        modoCombo = false;
        lblbuscar.setText("BUSCAR PRODUCTO");
        listarProductos();
        txtnombrecombo.requestFocus();
    }

    private void guardarCombo() {
        if (txtidcombo.getText().isEmpty() || txtnombrecombo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete los campos obligatorios (ID y Nombre)");
            return;
        }
        try {
            int idCombo = Integer.parseInt(txtidcombo.getText());
            String nombre = txtnombrecombo.getText();
            double precio = Double.parseDouble(txtpreciocombo.getText());

            int resultado = comboDAO.guardarCombo(idCombo, nombre, precio);
            if (resultado > 0) {
                if (!detalleActual.isEmpty()) {
                    detalleDAO.eliminarDetallePorCombo(idCombo);
                    detalleDAO.guardarDetalle(idCombo, detalleActual);
                }
                JOptionPane.showMessageDialog(this, "Combo guardado correctamente");
                modoCombo = true;
                lblbuscar.setText("BUSCAR COMBO");
                listarCombos();
                limpiarCampos();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un número válido");
        }
    }

    private void actualizarCombo() {
        if (txtidcombo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un combo de la tabla");
            return;
        }
        try {
            int idCombo = Integer.parseInt(txtidcombo.getText());
            String nombre = txtnombrecombo.getText();
            double precio = Double.parseDouble(txtpreciocombo.getText());

            int resultado = comboDAO.actualizarCombo(idCombo, nombre, precio);
            if (resultado > 0) {
                detalleDAO.eliminarDetallePorCombo(idCombo);
                if (!detalleActual.isEmpty()) {
                    detalleDAO.guardarDetalle(idCombo, detalleActual);
                }
                JOptionPane.showMessageDialog(this, "Combo actualizado correctamente");
                listarCombos();
                limpiarCampos();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un número válido");
        }
    }

    private void eliminarCombo() {
        int fila = tblproductos.getSelectedRow();
        if (fila < 0 || !modoCombo) {
            JOptionPane.showMessageDialog(this, "Seleccione un combo de la tabla");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar el combo?", "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int idCombo = Integer.parseInt(tblproductos.getValueAt(fila, 0).toString());
            detalleDAO.eliminarDetallePorCombo(idCombo);
            comboDAO.eliminarCombo(idCombo);
            listarCombos();
            limpiarCampos();
        }
    }

    private void buscarCombo() {
        modoCombo = true;
        lblbuscar.setText("BUSCAR COMBO");
        txtbuscombo.setText("");
        listarCombos();
    }

    private void limpiar() {
        limpiarCampos();
        modoCombo = false;
        lblbuscar.setText("BUSCAR PRODUCTO");
        listarProductos();
    }

    private void listarCombos() {
        modoCombo = true;
        List<Object[]> combos = comboDAO.listarCombos();
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("ID");
        model.addColumn("Combo");
        model.addColumn("Precio");
        for (Object[] fila : combos) {
            model.addRow(fila);
        }
        tblproductos.setModel(model);
    }

    private void listarProductos() {
        modoCombo = false;
        List<Producto> productos = productoDAO.listar();
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Precio");
        for (Producto p : productos) {
            model.addRow(new Object[]{p.getIdProducto(), p.getNombre(), p.getPrecio()});
        }
        tblproductos.setModel(model);
    }

    private void buscar() {
        String texto = txtbuscombo.getText().trim();
        if (texto.isEmpty()) {
            if (modoCombo) {
                listarCombos();
            } else {
                listarProductos();
            }
            return;
        }
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        if (modoCombo) {
            model.addColumn("ID");
            model.addColumn("Combo");
            model.addColumn("Precio");
            List<Object[]> combos = comboDAO.buscarCombos(texto);
            for (Object[] fila : combos) {
                model.addRow(fila);
            }
        } else {
            model.addColumn("ID");
            model.addColumn("Nombre");
            model.addColumn("Precio");
            List<Producto> productos = productoDAO.buscarProductos(texto);
            for (Producto p : productos) {
                model.addRow(new Object[]{p.getIdProducto(), p.getNombre(), p.getPrecio()});
            }
        }
        tblproductos.setModel(model);
    }

    private void cargarComboSeleccionado() {
        int fila = tblproductos.getSelectedRow();
        if (fila < 0) return;

        int idCombo = Integer.parseInt(tblproductos.getValueAt(fila, 0).toString());
        String nombre = tblproductos.getValueAt(fila, 1).toString();

        txtidcombo.setText(String.valueOf(idCombo));
        txtnombrecombo.setText(nombre);
        txtpreciocombo.setText(tblproductos.getValueAt(fila, 2).toString());

        cargarDetalleCombo(idCombo);

        modoCombo = false;
        lblbuscar.setText("BUSCAR PRODUCTO");
        listarProductos();
    }

    private void cargarDetalleCombo(int idCombo) {
        detalleActual = detalleDAO.listarPorCombo(idCombo);
        mostrarDetalleActual();
    }

    private void mostrarDetalleActual() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1;
            }
        };
        model.addColumn("Producto");
        model.addColumn("Cantidad");
        model.addColumn("Precio");
        model.addColumn("Subtotal");

        double total = 0;
        for (Detalle d : detalleActual) {
            model.addRow(new Object[]{
                d.getProducto().getNombre(),
                d.getCantidad(),
                d.getProducto().getPrecio(),
                d.getSubtotal()
            });
            total += d.getSubtotal();
        }

        model.addTableModelListener(e -> {
            int row = e.getFirstRow();
            int col = e.getColumn();
            if (col == 1 && row >= 0 && row < detalleActual.size()) {
                try {
                    int nuevaCant = Integer.parseInt(model.getValueAt(row, col).toString());
                    if (nuevaCant > 0) {
                        detalleActual.get(row).setCantidad(nuevaCant);
                        model.setValueAt(detalleActual.get(row).getSubtotal(), row, 3);
                        double nuevoTotal = 0;
                        for (Detalle d : detalleActual) {
                            nuevoTotal += d.getSubtotal();
                        }
                        txttotal.setText(String.format("%.2f", nuevoTotal));
                    }
                } catch (NumberFormatException ex) {
                    model.setValueAt(detalleActual.get(row).getCantidad(), row, 1);
                }
            }
        });

        tbldetalle.setModel(model);
        txttotal.setText(String.format("%.2f", total));
    }

    private void agregarProductoAlDetalle() {
        int fila = tblproductos.getSelectedRow();
        if (fila < 0) return;

        int idProducto = Integer.parseInt(tblproductos.getValueAt(fila, 0).toString());
        String nombre = tblproductos.getValueAt(fila, 1).toString();
        double precio = Double.parseDouble(tblproductos.getValueAt(fila, 2).toString());

        for (Detalle d : detalleActual) {
            if (d.getProducto().getIdProducto() == idProducto) {
                d.setCantidad(d.getCantidad() + 1);
                mostrarDetalleActual();
                return;
            }
        }

        Producto p = new Producto();
        p.setIdProducto(idProducto);
        p.setNombre(nombre);
        p.setPrecio(precio);

        Detalle d = new Detalle();
        d.setProducto(p);
        d.setCantidad(1);

        detalleActual.add(d);
        mostrarDetalleActual();
    }

    private void eliminarProductoDelDetalle() {
        int fila = tbldetalle.getSelectedRow();
        if (fila >= 0 && fila < detalleActual.size()) {
            detalleActual.remove(fila);
            mostrarDetalleActual();
        }
    }

    private void limpiarCampos() {
        txtidcombo.setText("");
        txtnombrecombo.setText("");
        txtpreciocombo.setText("");
        txttotal.setText("");
        txtbuscombo.setText("");
        detalleActual.clear();
        mostrarDetalleActual();
        tblproductos.clearSelection();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tbldetalle = new disenos.TablaTransparente();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblproductos = new disenos.TablaTransparente();
        txtidcombo = new disenos.TextFieldRedondeado();
        txtnombrecombo = new disenos.TextFieldRedondeado();
        txtbuscombo = new disenos.TextFieldRedondeado();
        btnguardarcombo = new disenos.BotonTransparente();
        btnactualizarcombo = new disenos.BotonTransparente();
        btneliminarcombo = new disenos.BotonTransparente();
        btnlimpiarcombo = new disenos.BotonTransparente();
        txttotal = new disenos.TextFieldRedondeado();
        txtpreciocombo = new disenos.TextFieldRedondeado();
        lblid = new javax.swing.JLabel();
        lblnombre = new javax.swing.JLabel();
        lblbuscar = new javax.swing.JLabel();
        lblbuscarcombo = new javax.swing.JLabel();
        btnbuscarcombo = new disenos.BotonTransparente();
        lbltotal = new javax.swing.JLabel();
        lblprecio = new javax.swing.JLabel();
        botonTransparente1 = new disenos.BotonTransparente();

        tbldetalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbldetalle.setColorTexto(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(tbldetalle);

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
        jScrollPane2.setViewportView(tblproductos);

        txtidcombo.setEditable(false);

        btnguardarcombo.setText("GUARDAR");

        btnactualizarcombo.setText("ACTUALIZAR");

        btneliminarcombo.setText("ELIMINAR");

        btnlimpiarcombo.setText("LIMPIAR");

        txttotal.setEditable(false);

        lblid.setBackground(new java.awt.Color(255, 255, 255));
        lblid.setForeground(new java.awt.Color(255, 255, 255));
        lblid.setText("ID");

        lblnombre.setBackground(new java.awt.Color(255, 255, 255));
        lblnombre.setForeground(new java.awt.Color(255, 255, 255));
        lblnombre.setText("NOMBRE");

        lblbuscar.setBackground(new java.awt.Color(255, 255, 255));
        lblbuscar.setForeground(new java.awt.Color(255, 255, 255));
        lblbuscar.setText("BUSCAR PRODUCTO");

        lblbuscarcombo.setBackground(new java.awt.Color(255, 255, 255));
        lblbuscarcombo.setForeground(new java.awt.Color(255, 255, 255));
        lblbuscarcombo.setText("BUSCAR COMBO");

        btnbuscarcombo.setText("ACEPTAR");

        lbltotal.setBackground(new java.awt.Color(255, 255, 255));
        lbltotal.setForeground(new java.awt.Color(255, 255, 255));
        lbltotal.setText("TOTAL");

        lblprecio.setBackground(new java.awt.Color(255, 255, 255));
        lblprecio.setForeground(new java.awt.Color(255, 255, 255));
        lblprecio.setText("PRECIO");

        botonTransparente1.setText("NUEVO");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 740, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnlimpiarcombo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btneliminarcombo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnactualizarcombo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnguardarcombo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtidcombo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtbuscombo, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                            .addComponent(txtnombrecombo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblbuscar)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblnombre)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblprecio))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblid)
                                        .addGap(18, 18, 18)
                                        .addComponent(botonTransparente1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lbltotal)))
                                .addGap(22, 22, 22)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txttotal, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                                    .addComponent(txtpreciocombo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblbuscarcombo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnbuscarcombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtidcombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txttotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblid)
                    .addComponent(lbltotal)
                    .addComponent(botonTransparente1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtnombrecombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtpreciocombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblnombre)
                    .addComponent(lblprecio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtbuscombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblbuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblbuscarcombo)
                    .addComponent(btnbuscarcombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(btnguardarcombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnactualizarcombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btneliminarcombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnlimpiarcombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private disenos.BotonTransparente botonTransparente1;
    private disenos.BotonTransparente btnactualizarcombo;
    private disenos.BotonTransparente btnbuscarcombo;
    private disenos.BotonTransparente btneliminarcombo;
    private disenos.BotonTransparente btnguardarcombo;
    private disenos.BotonTransparente btnlimpiarcombo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblbuscar;
    private javax.swing.JLabel lblbuscarcombo;
    private javax.swing.JLabel lblid;
    private javax.swing.JLabel lblnombre;
    private javax.swing.JLabel lblprecio;
    private javax.swing.JLabel lbltotal;
    private disenos.TablaTransparente tbldetalle;
    private disenos.TablaTransparente tblproductos;
    private disenos.TextFieldRedondeado txtbuscombo;
    private disenos.TextFieldRedondeado txtidcombo;
    private disenos.TextFieldRedondeado txtnombrecombo;
    private disenos.TextFieldRedondeado txtpreciocombo;
    private disenos.TextFieldRedondeado txttotal;
    // End of variables declaration//GEN-END:variables
}
