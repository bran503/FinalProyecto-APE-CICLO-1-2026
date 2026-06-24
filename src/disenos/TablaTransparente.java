/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package disenos;

/**
 *
 * @author Duran
 */
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TablaTransparente extends JTable {

    private Color colorTexto = new Color(50, 50, 50);
    private Color colorSeleccion = new Color(0, 120, 215, 80); // Azul semitransparente
    private boolean mostrarLineas = false;
    private Color colorLineas = new Color(220, 220, 220);

    public TablaTransparente() {
        // 1. Hacer la tabla y el header transparentes
        setOpaque(false);
        getTableHeader().setOpaque(false);
        getTableHeader().setReorderingAllowed(false); // Evita que muevan las columnas
        
        // 2. Configurar el renderizador de celdas
        setDefaultRenderer(Object.class, new RenderizadorTransparente());
        
        // 3. Configuraciones visuales
        setShowGrid(mostrarLineas);
        setGridColor(colorLineas);
        setSelectionBackground(colorSeleccion);
        setForeground(colorTexto);
        setRowHeight(35); // Altura de fila moderna
    }

    // --- PROPIEDADES PARA EL DISEÑADOR ---

    public Color getColorTexto() { return colorTexto; }
    public void setColorTexto(Color colorTexto) { 
        this.colorTexto = colorTexto; 
        setForeground(colorTexto);
        repaint();
    }

    public Color getColorSeleccion() { return colorSeleccion; }
    public void setColorSeleccion(Color colorSeleccion) { 
        this.colorSeleccion = colorSeleccion; 
        setSelectionBackground(colorSeleccion);
    }

    public boolean isMostrarLineas() { return mostrarLineas; }
    public void setMostrarLineas(boolean mostrarLineas) { 
        this.mostrarLineas = mostrarLineas; 
        setShowGrid(mostrarLineas);
        repaint();
    }

    // --- RENDERIZADOR INTERNO ---
    
    private class RenderizadorTransparente extends DefaultTableCellRenderer {
        public RenderizadorTransparente() {
            setOpaque(false);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            // Lógica de transparencia y selección
            if (isSelected) {
                setBackground(colorSeleccion);
                setOpaque(true); // Se vuelve opaco solo para mostrar el color de selección
            } else {
                setOpaque(false); // Transparente si no está seleccionada
            }
            
            setForeground(colorTexto);
            setFont(table.getFont());
            return c;
        }
    }
}
