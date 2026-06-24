package Fpaneles;

import Clases.Categoria;
import Clases.CategoriaDAO;
import Clases.Combo;
import Clases.ComboDAO;
import Clases.DetalleOrden;
import Clases.DetalleOrdenDAO;
import Clases.Item;
import Clases.OrdenDAO;
import Clases.OrdenService;
import Clases.Producto;
import Clases.ProductoDAO;
import Clases.Sesion;
import Clases.TicketPDF;
import Clases.Usuario;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PnlOrdenes extends disenos.PanelRedondeado {

    private OrdenService ordenService = new OrdenService();
    private CategoriaDAO categoriaDAO = new CategoriaDAO();
    private ProductoDAO productoDAO = new ProductoDAO();
    private ComboDAO comboDAO = new ComboDAO();
    private OrdenDAO ordenDAO = new OrdenDAO();
    private DetalleOrdenDAO detalleOrdenDAO = new DetalleOrdenDAO();
    private List<DetalleOrden> detalleActual = new ArrayList<>();
    private List<Categoria> categorias;
    private List<Item> items = new ArrayList<>();
    private boolean modoHistorial = false;
    private int currentIdOrden = 0;
    private boolean esSoloVista = false;

    public PnlOrdenes() {
        initComponents();
        setBackground(new Color(18, 18, 18));
        setBorderRadius(30);
        jScrollPane1.setOpaque(false);
        jScrollPane1.getViewport().setOpaque(false);
        jScrollPane1.getViewport().setBackground(new Color(0, 0, 0, 0));
        inicializar();
    }

    private void inicializar() {
        btnagregar.addActionListener(this::btnagregarActionPerformed);
        btnptocesar.addActionListener(this::btnptocesarActionPerformed);
        btnlimpiar.addActionListener(this::btnlimpiarActionPerformed);
        btnmostrarticket.addActionListener(this::btnmostrarticketActionPerformed);
        btnverorden.addActionListener(this::btnverordenActionPerformed);
        btnhistorial.addActionListener(this::btnhistorialActionPerformed);
        btnactualizarestado.addActionListener(this::btnactualizarestadoActionPerformed);
        cmbcategorias.addActionListener(e -> cargarItemsSegunCategoria());

        textFieldRedondeado6.setVisible(false);
        cmbordenestado.setVisible(false);
        btnactualizarestado.setVisible(false);
        cmbordenestado.removeAllItems();

        tblorden.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (modoHistorial && tblorden.getColumnCount() == 5 && tblorden.getSelectedRow() >= 0 && "ID".equals(tblorden.getColumnName(0))) {
                    int fila = tblorden.getSelectedRow();
                    currentIdOrden = Integer.parseInt(tblorden.getValueAt(fila, 0).toString());
                    txtidorden.setText(String.valueOf(currentIdOrden));
                    txttotal.setText(tblorden.getValueAt(fila, 3).toString());
                    btnmostrarticket.setEnabled(true);
                } else if (modoHistorial) {
                    btnmostrarticket.setEnabled(false);
                }
            }
        });

        tblorden.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE && !modoHistorial && tblorden.getColumnCount() >= 5) {
                    int fila = tblorden.getSelectedRow();
                    if (fila >= 0 && fila < detalleActual.size()) {
                        detalleActual.remove(fila);
                        listarDetalleActual();
                    }
                }
            }
        });

        txtbuscarorden.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                buscarOrdenes();
            }
        });
        txtbuscarorden.setEnabled(false);
        spncantidad.setModel(new javax.swing.SpinnerNumberModel(1, 1, 100, 1));
        txtidorden.setEditable(false);
        txtfechahora.setEditable(false);
        txtusuario.setEditable(false);

        Usuario user = Sesion.getUsuarioActual();
        if (user != null) {
            txtusuario.setText(user.getUsuario() + " - " + user.getRol());
        }
        txtfechahora.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

        listarCategorias();
        nuevoPedido();

        esSoloVista = user != null && ("Cocinero".equalsIgnoreCase(user.getRol()) || "Supervisor".equalsIgnoreCase(user.getRol()));
        if (esSoloVista) {
            aplicarRestriccionesVista();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblorden = new disenos.TablaTransparente();
        txtidorden = new disenos.TextFieldRedondeado();
        txtfechahora = new disenos.TextFieldRedondeado();
        txtusuario = new disenos.TextFieldRedondeado();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtbuscarorden = new disenos.TextFieldRedondeado();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cmbcategorias = new javax.swing.JComboBox<>();
        cmbittems = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        spncantidad = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        txttotal = new disenos.TextFieldRedondeado();
        jLabel8 = new javax.swing.JLabel();
        textFieldRedondeado6 = new disenos.TextFieldRedondeado();
        cmbordenestado = new javax.swing.JComboBox<>();
        btnactualizarestado = new disenos.BotonTransparente();
        btnagregar = new disenos.BotonTransparente();
        btnptocesar = new disenos.BotonTransparente();
        btnlimpiar = new disenos.BotonTransparente();
        btnmostrarticket = new disenos.BotonTransparente();
        btnverorden = new disenos.BotonTransparente();
        btnhistorial = new disenos.BotonTransparente();

        tblorden.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblorden.setColorTexto(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(tblorden);

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ID ORDEN");

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("FECHA:HORA");

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("USUARIO");

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("BUSCAR ORDEN ID:FECHA");

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("CATEGORIA");

        cmbcategorias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbittems.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("PRODUCTO COMBO");

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("CANTIDAD");

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("TOTAL");

        textFieldRedondeado6.setText("ESTADO ORDEN");

        cmbordenestado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnactualizarestado.setText("ACTUALIZAR");
        btnactualizarestado.setColorDeshabilitado(new java.awt.Color(255, 0, 0));

        btnagregar.setText("AGREGAR");
        btnagregar.setColorDeshabilitado(new java.awt.Color(255, 0, 0));

        btnptocesar.setText("PROCESAR");
        btnptocesar.setColorDeshabilitado(new java.awt.Color(255, 0, 0));

        btnlimpiar.setText("LIMPIAr");

        btnmostrarticket.setText("MOSTRAR TICKET");
        btnmostrarticket.setColorDeshabilitado(new java.awt.Color(255, 0, 0));

        btnverorden.setText("VER ORDEN");
        btnverorden.setColorDeshabilitado(new java.awt.Color(255, 0, 0));

        btnhistorial.setText("HISTORIAL");
        btnhistorial.setColorDeshabilitado(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtbuscarorden, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(txtidorden, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(txtfechahora, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtusuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbcategorias, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbittems, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spncantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txttotal, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(textFieldRedondeado6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbordenestado, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnactualizarestado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 372, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnagregar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnptocesar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnlimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnmostrarticket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(105, 105, 105)
                        .addComponent(btnverorden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnhistorial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtidorden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtfechahora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtbuscarorden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbcategorias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbittems, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spncantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txttotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFieldRedondeado6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbordenestado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnactualizarestado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnverorden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnhistorial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnagregar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnptocesar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnlimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnmostrarticket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnagregarActionPerformed(java.awt.event.ActionEvent evt) {
        if (cmbittems.getSelectedItem() == null) {
            return;
        }
        String selectedName = cmbittems.getSelectedItem().toString();
        Item item = null;
        for (Item it : items) {
            if (it.getNombre().equals(selectedName)) {
                item = it;
                break;
            }
        }
        if (item == null) return;
        int cantidad = (int) spncantidad.getValue();
        if (cantidad <= 0) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0");
            return;
        }
        for (DetalleOrden d : detalleActual) {
            if (d.getTipo().equals(item.getTipo()) && d.getId() == item.getId()) {
                d.setCantidad(d.getCantidad() + cantidad);
                listarDetalleActual();
                return;
            }
        }
        DetalleOrden d = new DetalleOrden(item.getTipo(), item.getId(), cantidad, item.getPrecio());
        d.setNombre(item.getNombre());
        detalleActual.add(d);
        listarDetalleActual();
    }

    private void btnptocesarActionPerformed(java.awt.event.ActionEvent evt) {
        if (detalleActual.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Agregue al menos un producto a la orden");
            return;
        }
        try {
            if (currentIdOrden == 0) {
                int idOrden = ordenService.procesarOrden(detalleActual);
                JOptionPane.showMessageDialog(this, "Orden #" + idOrden + " procesada correctamente");
                TicketPDF ticket = new TicketPDF();
                ticket.generarTicket(idOrden);
            } else {
                ordenService.actualizarOrdenExistente(currentIdOrden, detalleActual);
                JOptionPane.showMessageDialog(this, "Orden #" + currentIdOrden + " actualizada correctamente");
            }
            nuevoPedido();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al procesar la orden: " + e.getMessage());
        }
    }

    private void btnlimpiarActionPerformed(java.awt.event.ActionEvent evt) {
        nuevoPedido();
    }

    private void btnmostrarticketActionPerformed(java.awt.event.ActionEvent evt) {
        if (currentIdOrden == 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una orden");
            return;
        }
        String fecha = ordenDAO.getFechaFormateada(currentIdOrden);
        if (fecha == null) {
            JOptionPane.showMessageDialog(this, "No se encontró la fecha de la orden");
            return;
        }
        String ruta = "documentos" + File.separator + fecha + File.separator + "ticket_" + currentIdOrden + ".pdf";
        File archivo = new File(ruta);
        if (archivo.exists()) {
            try {
                Desktop.getDesktop().open(archivo);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al abrir el ticket");
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró el ticket PDF para la orden #" + currentIdOrden);
        }
    }

    private void btnverordenActionPerformed(java.awt.event.ActionEvent evt) {
        if (currentIdOrden == 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una orden en la tabla");
            return;
        }
        detalleActual.clear();
        List<Object[]> detalle = detalleOrdenDAO.listarDetallePorOrden(currentIdOrden);
        for (Object[] row : detalle) {
            DetalleOrden d = new DetalleOrden(
                    row[1].toString(),
                    Integer.parseInt(row[2].toString()),
                    Integer.parseInt(row[5].toString()),
                    Double.parseDouble(row[4].toString())
            );
            d.setNombre(row[3].toString());
            if (row[7] != null && !row[7].toString().isEmpty()) {
                d.setModifiedBy(row[7].toString());
            }
            detalleActual.add(d);
        }
        listarDetalleActual();
        btnagregar.setEnabled(false);
        btnptocesar.setEnabled(false);
        btnmostrarticket.setEnabled(true);
        cmbcategorias.setEnabled(false);
        cmbittems.setEnabled(false);
        spncantidad.setEnabled(false);

        if (!esSoloVista) {
            textFieldRedondeado6.setVisible(true);
            cmbordenestado.setVisible(true);
            btnactualizarestado.setVisible(true);
            cmbordenestado.removeAllItems();
            Usuario user = Sesion.getUsuarioActual();
            if (user != null && "MESERO".equalsIgnoreCase(user.getRol())) {
                cmbordenestado.addItem("despachado");
            } else {
                cmbordenestado.addItem("despachado");
                cmbordenestado.addItem("anulado");
            }
            String estadoActual = ordenDAO.getEstadoOrden(currentIdOrden);
            if (estadoActual != null) {
                cmbordenestado.setSelectedItem(estadoActual);
            }
        }
    }

    private void btnhistorialActionPerformed(java.awt.event.ActionEvent evt) {
        if (modoHistorial) {
            nuevoPedido();
            return;
        }
        modoHistorial = true;
        txtbuscarorden.setEnabled(true);
        txtbuscarorden.requestFocus();
        btnagregar.setEnabled(false);
        btnptocesar.setEnabled(false);
        btnmostrarticket.setEnabled(false);
        btnverorden.setVisible(true);
        listarOrdenes();
    }

    private void btnactualizarestadoActionPerformed(java.awt.event.ActionEvent evt) {
        if (esSoloVista) {
            return;
        }
        if (currentIdOrden == 0) {
            JOptionPane.showMessageDialog(this, "No hay una orden seleccionada");
            return;
        }
        String nuevoEstado = (String) cmbordenestado.getSelectedItem();
        if (nuevoEstado == null || nuevoEstado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un estado");
            return;
        }
        if ("anulado".equalsIgnoreCase(nuevoEstado)) {
            String fechaOrden = ordenDAO.getFechaFormateada(currentIdOrden);
            String hoy = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            if (!hoy.equals(fechaOrden)) {
                JOptionPane.showMessageDialog(this, "No se puede anular una orden de una fecha anterior");
                return;
            }
        }
        boolean actualizado = ordenDAO.actualizarEstado(currentIdOrden, nuevoEstado);
        if (actualizado) {
            JOptionPane.showMessageDialog(this, "Estado actualizado a: " + nuevoEstado);
            textFieldRedondeado6.setVisible(false);
            cmbordenestado.setVisible(false);
            btnactualizarestado.setVisible(false);
            listarOrdenes();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar el estado");
        }
    }

    private void listarCategorias() {
        categorias = categoriaDAO.listarCategorias();
        cmbcategorias.removeAllItems();
        for (Categoria c : categorias) {
            cmbcategorias.addItem(c.getCategoria());
        }
        cmbcategorias.addItem("COMBOS");
        if (cmbcategorias.getItemCount() > 0) {
            cargarItemsSegunCategoria();
        }
    }

    private void cargarItemsSegunCategoria() {
        cmbittems.removeAllItems();
        items.clear();
        int index = cmbcategorias.getSelectedIndex();
        if (index < 0 || categorias == null) {
            return;
        }

        if (index == categorias.size()) {
            List<Combo> combos = comboDAO.listar();
            for (Combo c : combos) {
                items.add(new Item(c.getIdCombo(), c.getCombo(), c.getPrecio(), "COMBO"));
                cmbittems.addItem(c.getCombo());
            }
        } else if (index < categorias.size()) {
            int idCategoria = categorias.get(index).getIdCategoria();
            List<Producto> productos = productoDAO.listarPorCategoria(idCategoria);
            for (Producto p : productos) {
                items.add(new Item(p.getIdProducto(), p.getNombre(), p.getPrecio(), "PRODUCTO"));
                cmbittems.addItem(p.getNombre());
            }
        }

        if (cmbittems.getItemCount() > 0) {
            cmbittems.setSelectedIndex(0);
        }
    }

    private void listarDetalleActual() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("Tipo");
        model.addColumn("Nombre");
        model.addColumn("Precio");
        model.addColumn("Cantidad");
        model.addColumn("Subtotal");

        double total = 0;
        for (DetalleOrden d : detalleActual) {
            model.addRow(new Object[]{
                d.getTipo(),
                d.getNombre(),
                d.getPrecio(),
                d.getCantidad(),
                d.getSubTotal()
            });
            total += d.getSubTotal();
        }
        tblorden.setModel(model);
        txttotal.setText(String.format("%.2f", total));
    }

    private void listarOrdenes() {
        List<Object[]> ordenes = ordenDAO.listarOrdenes();
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("ID");
        model.addColumn("Fecha");
        model.addColumn("Usuario");
        model.addColumn("Total");
        model.addColumn("Estado");
        for (Object[] fila : ordenes) {
            model.addRow(fila);
        }
        tblorden.setModel(model);
    }

    private void buscarOrdenes() {
        String texto = txtbuscarorden.getText().trim();
        if (texto.isEmpty()) {
            if (modoHistorial) {
                listarOrdenes();
            }
            return;
        }
        if (!modoHistorial) {
            return;
        }
        List<Object[]> ordenes = ordenDAO.buscarOrdenes(texto);
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("ID");
        model.addColumn("Fecha");
        model.addColumn("Usuario");
        model.addColumn("Total");
        model.addColumn("Estado");
        for (Object[] fila : ordenes) {
            model.addRow(fila);
        }
        tblorden.setModel(model);
    }

    private void nuevoPedido() {
        modoHistorial = false;
        currentIdOrden = 0;
        btnptocesar.setText("PROCESAR");
        btnlimpiar.setText("LIMPIAR");
        btnverorden.setVisible(false);
        textFieldRedondeado6.setVisible(false);
        cmbordenestado.setVisible(false);
        btnactualizarestado.setVisible(false);
        btnagregar.setEnabled(true);
        btnptocesar.setEnabled(true);
        btnmostrarticket.setEnabled(false);
        cmbcategorias.setEnabled(true);
        cmbittems.setEnabled(true);
        spncantidad.setEnabled(true);
        txtbuscarorden.setEnabled(false);
        txtidorden.setText("");
        txttotal.setText("");
        txtbuscarorden.setText("");
        detalleActual.clear();
        listarDetalleActual();
        txtfechahora.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        if (cmbcategorias.getItemCount() > 0) {
            cmbcategorias.setSelectedIndex(0);
        }
        if (cmbittems.getItemCount() > 0) {
            cmbittems.setSelectedIndex(0);
        }
        spncantidad.setValue(1);
        if (esSoloVista) {
            aplicarRestriccionesVista();
        }
    }

    private void aplicarRestriccionesVista() {
        btnagregar.setEnabled(false);
        btnptocesar.setEnabled(false);
        btnlimpiar.setEnabled(false);
        spncantidad.setEnabled(false);
        cmbcategorias.setEnabled(false);
        cmbittems.setEnabled(false);
        btnactualizarestado.setVisible(false);
        textFieldRedondeado6.setVisible(false);
        cmbordenestado.setVisible(false);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private disenos.BotonTransparente btnactualizarestado;
    private disenos.BotonTransparente btnagregar;
    private disenos.BotonTransparente btnhistorial;
    private disenos.BotonTransparente btnlimpiar;
    private disenos.BotonTransparente btnmostrarticket;
    private disenos.BotonTransparente btnptocesar;
    private disenos.BotonTransparente btnverorden;
    private javax.swing.JComboBox<String> cmbcategorias;
    private javax.swing.JComboBox<String> cmbittems;
    private javax.swing.JComboBox<String> cmbordenestado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner spncantidad;
    private disenos.TablaTransparente tblorden;
    private disenos.TextFieldRedondeado textFieldRedondeado6;
    private disenos.TextFieldRedondeado txtbuscarorden;
    private disenos.TextFieldRedondeado txtfechahora;
    private disenos.TextFieldRedondeado txtidorden;
    private disenos.TextFieldRedondeado txttotal;
    private disenos.TextFieldRedondeado txtusuario;
    // End of variables declaration//GEN-END:variables
}
