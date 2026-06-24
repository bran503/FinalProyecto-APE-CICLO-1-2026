package disenos;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;

/**
 * Panel con bordes redondeados y soporte para bordes y sombras
 * @author Duran
 */
public class PanelRedondeado extends JPanel {
    
    private int borderRadius = 20;
    private int borderWidth = 0;
    private Color borderColor = new Color(60, 60, 60);
    private boolean mostrarSombra = false;
    private int sombraTamaño = 10;
    private Color sombraColor = new Color(0, 0, 0, 50);

    public PanelRedondeado() {
        setOpaque(false);
    }

    // --- GETTERS Y SETTERS PARA EL DISEÑADOR ---

    public int getBorderRadius() { return borderRadius; }
    public void setBorderRadius(int borderRadius) { 
        this.borderRadius = borderRadius; 
        repaint(); 
    }

    public int getBorderWidth() { return borderWidth; }
    public void setBorderWidth(int borderWidth) { 
        this.borderWidth = borderWidth; 
        repaint(); 
    }

    public Color getBorderColor() { return borderColor; }
    public void setBorderColor(Color borderColor) { 
        this.borderColor = borderColor; 
        repaint(); 
    }

    public boolean isMostrarSombra() { return mostrarSombra; }
    public void setMostrarSombra(boolean mostrarSombra) { 
        this.mostrarSombra = mostrarSombra; 
        repaint(); 
    }

    public int getSombraTamaño() { return sombraTamaño; }
    public void setSombraTamaño(int sombraTamaño) { 
        this.sombraTamaño = sombraTamaño; 
        repaint(); 
    }

    public Color getSombraColor() { return sombraColor; }
    public void setSombraColor(Color sombraColor) { 
        this.sombraColor = sombraColor; 
        repaint(); 
    }

    // --- PINTADO DEL PANEL ---

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 1. Dibujar sombra (si está activada)
        if (mostrarSombra && sombraTamaño > 0) {
            g2.setColor(sombraColor);
            for (int i = 0; i < sombraTamaño; i++) {
                g2.fill(new RoundRectangle2D.Float(
                    i, i, 
                    getWidth() - (i * 2), getHeight() - (i * 2), 
                    borderRadius, borderRadius
                ));
            }
        }
        
        // 2. Dibujar fondo redondeado
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), borderRadius, borderRadius));
        
        // 3. Dibujar borde (si tiene grosor)
        if (borderWidth > 0) {
            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(borderWidth));
            g2.draw(new RoundRectangle2D.Float(
                borderWidth / 2, borderWidth / 2, 
                getWidth() - borderWidth, getHeight() - borderWidth, 
                borderRadius, borderRadius
            ));
        }
        
        g2.dispose();
        super.paintComponent(g);
    }
}