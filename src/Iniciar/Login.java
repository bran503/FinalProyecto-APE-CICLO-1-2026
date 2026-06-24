package Iniciar;

import Clases.Sesion;
import Clases.Usuario;
import Clases.UsuarioDAO;
import javax.swing.JOptionPane;

/**
 *
 * @author Duran
 */
public class Login extends disenos.JFrameRedondeado {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Login.class.getName());

    public Login() {
        initComponents();
        setBorderRadius(30); // Ajusta el radio (30 = muy redondeado)
        setLocationRelativeTo(null);
        this.setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textFieldRedondeado2 = new disenos.TextFieldRedondeado();
        jLabel1 = new javax.swing.JLabel();
        lblusuario = new javax.swing.JLabel();
        lblcontrasena = new javax.swing.JLabel();
        txtusuario = new disenos.TextFieldRedondeado();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtcontrasena = new disenos.PasswordFieldRedondeado();
        jLabel4 = new javax.swing.JLabel();
        botonTransparente1 = new disenos.BotonTransparente();
        botonTransparente2 = new disenos.BotonTransparente();

        textFieldRedondeado2.setText("textFieldRedondeado2");

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblusuario.setBackground(new java.awt.Color(255, 255, 255));
        lblusuario.setForeground(new java.awt.Color(255, 255, 255));
        lblusuario.setText("USUARIO");

        lblcontrasena.setBackground(new java.awt.Color(255, 255, 255));
        lblcontrasena.setForeground(new java.awt.Color(255, 255, 255));
        lblcontrasena.setText("CONTRASEÑA");

        txtusuario.setBorderColor(new java.awt.Color(255, 204, 0));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 3, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("SANTO ANTOJO");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/medias/download.gif"))); // NOI18N
        jLabel3.setText("jLabel3");

        txtcontrasena.setBorderColor(new java.awt.Color(255, 204, 0));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Segoe Script", 1, 10)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("POWER BY DM24001");

        botonTransparente1.setText("SALIR");
        botonTransparente1.setFont(new java.awt.Font("Bookman Old Style", 3, 12)); // NOI18N
        botonTransparente1.addActionListener(this::botonTransparente1ActionPerformed);

        botonTransparente2.setText("INICIAR SESION");
        botonTransparente2.addActionListener(this::botonTransparente2ActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(95, 95, 95)
                                .addComponent(jLabel2))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(137, 137, 137)
                                .addComponent(jLabel4))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(68, 68, 68)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblusuario)
                                    .addComponent(lblcontrasena))
                                .addGap(41, 41, 41)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtusuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtcontrasena, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(botonTransparente2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(106, 106, 106)))
                        .addGap(0, 27, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(botonTransparente1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botonTransparente1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblusuario)
                    .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblcontrasena)
                    .addComponent(txtcontrasena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(botonTransparente2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonTransparente1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonTransparente1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_botonTransparente1ActionPerformed

    private void botonTransparente2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonTransparente2ActionPerformed
        iniciarSesion(evt);
    }//GEN-LAST:event_botonTransparente2ActionPerformed

    private void iniciarSesion(java.awt.event.ActionEvent evt) {
        String user = txtusuario.getText().trim();
        String pass = txtcontrasena.getText().trim();

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese usuario y contraseña", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();
        Usuario usuario = dao.login(user, pass);

        if (usuario != null) {
            Sesion.iniciarSesion(usuario);
            JOptionPane.showMessageDialog(this,
                    "Bienvenido " + usuario.getNombre() + " " + usuario.getApellido() + " - " + usuario.getRol(),
                    "Inicio de sesión", JOptionPane.INFORMATION_MESSAGE);

            FRM2 principal = new FRM2();
            principal.setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
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
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Login().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private disenos.BotonTransparente botonTransparente1;
    private disenos.BotonTransparente botonTransparente2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lblcontrasena;
    private javax.swing.JLabel lblusuario;
    private disenos.TextFieldRedondeado textFieldRedondeado2;
    private disenos.PasswordFieldRedondeado txtcontrasena;
    private disenos.TextFieldRedondeado txtusuario;
    // End of variables declaration//GEN-END:variables
}
