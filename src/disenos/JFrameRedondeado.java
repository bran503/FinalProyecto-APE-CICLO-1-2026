package disenos;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JFrame;
/**
 *
 * @author Duran
 */
public class JFrameRedondeado extends JFrame {
    
    private int borderRadius = 30;

    public JFrameRedondeado() {
        // Quitar decoraciones del sistema (barra de título, bordes)
        setUndecorated(true);
        // Fondo transparente para que se vea el redondeo
        setBackground(new Color(0, 0, 0,250));
    }

    public int getBorderRadius() {
        return borderRadius;
    }

    public void setBorderRadius(int borderRadius) {
        this.borderRadius = borderRadius;
        // Aplicar la forma redondeada a la ventana
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), borderRadius, borderRadius));
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        // Actualizar la forma cuando cambia el tamaño
        if (borderRadius > 0) {
            setShape(new RoundRectangle2D.Double(0, 0, width, height, borderRadius, borderRadius));
        }
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        // Actualizar la forma cuando cambian las dimensiones
        if (borderRadius > 0) {
            setShape(new RoundRectangle2D.Double(0, 0, width, height, borderRadius, borderRadius));
        }
    }
}
