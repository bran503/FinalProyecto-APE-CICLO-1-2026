package disenos;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class BotonTransparente extends JButton {
    
    private Color colorTexto = new Color(255, 255, 255); // Color normal (Blanco por defecto)
    private Color colorTextoHover = new Color(200, 200, 200); // Color al pasar el mouse
    private Color colorDeshabilitado = new Color(100, 100, 100); // Gris cuando está deshabilitado

    public BotonTransparente() {
        // 1. Quitar el fondo y el borde por defecto
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);
        
        // 2. Configurar el cursor y el color inicial
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setForeground(colorTexto);
        
        // 3. Agregar efecto Hover
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (isEnabled()) {
                    setForeground(colorTextoHover);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (isEnabled()) {
                    setForeground(colorTexto);
                }
            }
        });
        
        // 4. Escuchar cambios en el estado enabled/disabled
        addPropertyChangeListener("enabled", evt -> {
            if (isEnabled()) {
                setForeground(colorTexto);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            } else {
                setForeground(colorDeshabilitado);
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }

    // Getters y Setters para el diseñador
    public Color getColorTexto() { return colorTexto; }
    public void setColorTexto(Color colorTexto) { 
        this.colorTexto = colorTexto; 
        if (isEnabled()) setForeground(colorTexto);
    }

    public Color getColorTextoHover() { return colorTextoHover; }
    public void setColorTextoHover(Color colorTextoHover) { 
        this.colorTextoHover = colorTextoHover; 
    }

    public Color getColorDeshabilitado() { return colorDeshabilitado; }
    public void setColorDeshabilitado(Color colorDeshabilitado) { 
        this.colorDeshabilitado = colorDeshabilitado;
        if (!isEnabled()) setForeground(colorDeshabilitado);
    }
}