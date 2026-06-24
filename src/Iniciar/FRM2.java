package Iniciar;

import Clases.PermisoDAO;
import Clases.Sesion;
import Clases.Usuario;
import Fpaneles.PnlAgregarusuarios;
import Fpaneles.PnlCombos;
import Fpaneles.PnlOrdenes;
import Fpaneles.PnlPermisos;
import Fpaneles.PnlProductos;
import java.awt.CardLayout;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class FRM2 extends disenos.JFrameRedondeado {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FRM2.class.getName());

    public FRM2() {
        initComponents();
        setBackground(new Color(18, 18, 18));
        jPanel2.setBackground(new Color(18,18,18));
        setBorderRadius(30);
        setLocationRelativeTo(null);

        Usuario user = Sesion.getUsuarioActual();
        boolean esAdmin = user != null && "Administrador".equalsIgnoreCase(user.getRol());
        ArrayList<String> permisosUsuario = new ArrayList<>();
        if (user != null) {
            PermisoDAO permisoDAO = new PermisoDAO();
            permisosUsuario = permisoDAO.obtenerPermisosPorRol(user.getIdRol());
        }

        jPanel1.setLayout(new CardLayout());

        jPanel1.add(new PnlOrdenes(), "ORDENES");
        jPanel1.add(new PnlCombos(), "COMBOS");
        jPanel1.add(new PnlProductos(), "PRODUCTOS");
        jPanel1.add(new PnlAgregarusuarios(), "USUARIOS");
        jPanel1.add(new PnlPermisos(), "PERMISOS");

        if (!esAdmin && !permisosUsuario.contains("ACCESO_COMBOS")) {
            btncombos.setVisible(false);
        }
        if (!esAdmin && !permisosUsuario.contains("ACCESO_PRODUCTOS")) {
            btnproductos.setVisible(false);
        }
        if (!esAdmin && !permisosUsuario.contains("ACCESO_USUARIOS")) {
            btnusuarios.setVisible(false);
        }
        if (!esAdmin) {
            btnroles.setVisible(false);
        }

        btnordenes.addActionListener(e -> mostrarPanel("ORDENES"));
        btnproductos.addActionListener(e -> mostrarPanel("PRODUCTOS"));
        btncombos.addActionListener(e -> mostrarPanel("COMBOS"));
        btnusuarios.addActionListener(e -> mostrarPanel("USUARIOS"));
        btnroles.addActionListener(e -> mostrarPanel("PERMISOS"));
        btncerrarseion.addActionListener(e -> cerrarSesion());

        mostrarPanel("ORDENES");
    }

    private void mostrarPanel(String nombre) {
        CardLayout cl = (CardLayout) jPanel1.getLayout();
        cl.show(jPanel1, nombre);
    }

    private void cerrarSesion() {
        int resp = JOptionPane.showConfirmDialog(this,
                "¿Desea cerrar sesión?", "Cerrar sesión",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (resp == JOptionPane.YES_OPTION) {
            Sesion.cerrarSesion();
            Login login = new Login();
            login.setVisible(true);
            dispose();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnordenes = new disenos.BotonTransparente();
        btnproductos = new disenos.BotonTransparente();
        btncombos = new disenos.BotonTransparente();
        btnusuarios = new disenos.BotonTransparente();
        btnroles = new disenos.BotonTransparente();
        btncerrarseion = new disenos.BotonTransparente();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 871, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        btnordenes.setText("ORDENES");

        btnproductos.setText("PRODUCTOS");

        btncombos.setText("COMBOS");

        btnusuarios.setText("USUARIOS");

        btnroles.setText("ROLES");

        btncerrarseion.setText("CERRAR SESION");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnordenes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnproductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btncombos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnroles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnusuarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btncerrarseion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addComponent(btnordenes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(btnproductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btncombos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(btnusuarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnroles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 199, Short.MAX_VALUE)
                .addComponent(btncerrarseion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new FRM2().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private disenos.BotonTransparente btncerrarseion;
    private disenos.BotonTransparente btncombos;
    private disenos.BotonTransparente btnordenes;
    private disenos.BotonTransparente btnproductos;
    private disenos.BotonTransparente btnroles;
    private disenos.BotonTransparente btnusuarios;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
