package disenos;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicTextFieldUI;
/**
 *
 * @author Duran
 */
public class TextFieldRedondeado extends JTextField {
    
    private int borderRadius = 20;
    private int borderWidth = 2;
    private Color borderColor = Color.BLACK;

    public TextFieldRedondeado() {
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        setUI(new BasicTextFieldUI());
    }

    public int getBorderRadius() { return borderRadius; }
    public void setBorderRadius(int borderRadius) { 
        this.borderRadius = borderRadius; repaint(); 
    }

    public int getBorderWidth() { return borderWidth; }
    public void setBorderWidth(int borderWidth) { 
        this.borderWidth = borderWidth; repaint(); 
    }

    public Color getBorderColor() { return borderColor; }
    public void setBorderColor(Color borderColor) { 
        this.borderColor = borderColor; repaint(); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), borderRadius, borderRadius));
        
        if (borderWidth > 0) {
            g2.setColor(borderColor);
            g2.setStroke(new java.awt.BasicStroke(borderWidth));
            g2.draw(new RoundRectangle2D.Float(
                borderWidth/2, borderWidth/2, 
                getWidth() - borderWidth, getHeight() - borderWidth, 
                borderRadius, borderRadius
            ));
        }
        
        g2.dispose();
        super.paintComponent(g);
    }
}
