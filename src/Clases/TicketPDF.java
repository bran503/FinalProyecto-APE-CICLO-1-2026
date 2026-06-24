package Clases;

import Clases.Conexion;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TicketPDF {

    public void generarTicket(int idOrden) {

        String sqlOrden = "SELECT * FROM orden WHERE idOrden = ?";
        String sqlDetalle = """
            SELECT 
                COALESCE(p.nombre, c.combo) AS nombre,
                d.cantidad,
                d.precioUnitario,
                (d.cantidad * d.precioUnitario) AS subtotal
            FROM detalleOrden d
            LEFT JOIN producto p ON d.idProducto = p.idProducto
            LEFT JOIN combo c ON d.idCombo = c.idCombo
            WHERE d.idOrden = ?
            ORDER BY d.idLinea
        """;

        try (
            Connection con = Conexion.conectar();
            PreparedStatement psOrden = con.prepareStatement(sqlOrden);
            PreparedStatement psDet = con.prepareStatement(sqlDetalle);
        ) {

            psOrden.setInt(1, idOrden);
            ResultSet rsOrden = psOrden.executeQuery();

            String fechaHora = "";
            double totalOrden = 0;

            if (rsOrden.next()) {
                totalOrden = rsOrden.getDouble("total");

                Timestamp ts = rsOrden.getTimestamp("fechaHora");
                if (ts != null) {
                    DateTimeFormatter formato =
                            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                    fechaHora = ts.toLocalDateTime().format(formato);
                }
            }

            // Carpeta
            String fechaCarpeta = LocalDate.now()
                    .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            File carpeta = new File("documentos" + File.separator + fechaCarpeta);
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            String ruta = carpeta.getAbsolutePath()
                    + File.separator
                    + "ticket_" + idOrden + ".pdf";

            // TAMAÑO TICKET 58mm aprox
            Rectangle ticket = new Rectangle(164f, 600f);

            Document doc = new Document(ticket, 10, 10, 10, 10); //margenes de 10 mm
            PdfWriter.getInstance(doc, new FileOutputStream(ruta)); //conecta documento con archivo físico
            doc.open(); //abrirlo para agregar cosas

            Font titulo = new Font(Font.COURIER, 10, Font.BOLD);
            Font normal = new Font(Font.COURIER, 8);
            Font bold = new Font(Font.COURIER, 8, Font.BOLD);

            // ENCABEZADO
            agregarLinea(doc, "SANTO ANTOJO", titulo, Element.ALIGN_CENTER);
            agregarLinea(doc, "Ahuachapan, El Salvador", normal, Element.ALIGN_CENTER);
            agregarLinea(doc, "Tel: 2200-9000", normal, Element.ALIGN_CENTER);

            agregarLinea(doc, "-----------------------------", normal, Element.ALIGN_LEFT);
            agregarLinea(doc, "ORDEN: " + idOrden, bold, Element.ALIGN_LEFT);
            agregarLinea(doc, "FECHA: " + fechaHora, normal, Element.ALIGN_LEFT);
            agregarLinea(doc, "-----------------------------", normal, Element.ALIGN_LEFT);

            // DETALLE
            psDet.setInt(1, idOrden);
            ResultSet rsDet = psDet.executeQuery();

            double totalCalculado = 0;

            while (rsDet.next()) {

                String nombre = rsDet.getString("nombre");
                int cantidad = rsDet.getInt("cantidad");
                double precio = rsDet.getDouble("precioUnitario");
                double subtotal = rsDet.getDouble("subtotal");

                totalCalculado = totalCalculado + subtotal;

                agregarLinea(doc, nombre, bold, Element.ALIGN_LEFT);

                String linea = cantidad + " x $" +
                        String.format("%.2f", precio) +
                        "   $" +
                        String.format("%.2f", subtotal);

                agregarLinea(doc, linea, normal, Element.ALIGN_LEFT);
            }

            agregarLinea(doc, "-----------------------------", normal, Element.ALIGN_LEFT);

            Paragraph total = new Paragraph(
                    "TOTAL: $" + String.format("%.2f", totalCalculado),
                    titulo
            );
            total.setAlignment(Element.ALIGN_RIGHT);
            doc.add(total);

            agregarLinea(doc, " ", normal, Element.ALIGN_LEFT);
            agregarLinea(doc, "Gracias por su compra", normal, Element.ALIGN_CENTER);

            doc.close();

            Desktop.getDesktop().open(new File(ruta));

            //System.out.println("Ticket generado: " + ruta);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void agregarLinea(Document doc, String texto, Font fuente, int alineacion)
            throws DocumentException {

        Paragraph p = new Paragraph(texto, fuente);
        p.setAlignment(alineacion);
        p.setSpacingAfter(2);
        doc.add(p);
    }
}