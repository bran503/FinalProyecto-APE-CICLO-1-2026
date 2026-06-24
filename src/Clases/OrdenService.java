package Clases;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class OrdenService {

    private DetalleOrdenDAO detalleDAO = new DetalleOrdenDAO();
    private OrdenDAO ordenDAO = new OrdenDAO();

public int procesarOrden(List<DetalleOrden> detalle) {

    Connection con = null;

    try {
        con = Conexion.conectar();
        con.setAutoCommit(false);

        //Calcular total
        double total = 0;
        for (DetalleOrden d : detalle) {
            total = total + (d.getCantidad() * d.getPrecio());
        }

        //Obtener usuario actual de la sesión
        Usuario usuarioActual = Sesion.getUsuarioActual();
        if (usuarioActual == null) {
            throw new RuntimeException("No hay usuario autenticado");
        }
        int idUsuario = usuarioActual.getIdUsuario();

        //Insertar orden
        int idOrden = ordenDAO.insertarOrden(con, total, idUsuario);

        //Insertar detalle considerando el idOrden devuelto desde insertarOrden
        for (DetalleOrden d : detalle) {
            detalleDAO.insertarDetalle(con, idOrden, d);
        }

        con.commit(); //confirmar transacción
        
        return idOrden;

    } catch (Exception e) {
        try {
            if (con != null){ 
                con.rollback();
            }
        } catch (Exception ex) {
             ex.printStackTrace();
        }

        throw new RuntimeException(e);

    } finally { //cerremos conexión ya sea si fue exitoso o no
        try {
            if (con != null){
                con.close();
            }
        } catch (Exception e) {}
    }
}

public boolean actualizarOrdenExistente(int idOrden, List<DetalleOrden> detalle) {
    Connection con = null;

    try {
        con = Conexion.conectar();
        con.setAutoCommit(false);

        double total = 0;
        for (DetalleOrden d : detalle) {
            total = total + (d.getCantidad() * d.getPrecio());
        }

        if (!ordenDAO.actualizarOrden(con, idOrden, total)) {
            throw new RuntimeException("No se pudo actualizar la orden");
        }

        detalleDAO.eliminarDetallePorOrden(con, idOrden);
        for (DetalleOrden d : detalle) {
            detalleDAO.insertarDetalle(con, idOrden, d);
        }

        con.commit();
        return true;

    } catch (Exception e) {
        try {
            if (con != null) {
                con.rollback();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        throw new RuntimeException(e);
    } finally {
        try {
            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
        }
    }
}

public boolean eliminarOrdenExistente(int idOrden) {
    Connection con = null;

    try {
        con = Conexion.conectar();
        con.setAutoCommit(false);

        detalleDAO.eliminarDetallePorOrden(con, idOrden);
        if (!ordenDAO.eliminarOrden(con, idOrden)) {
            throw new RuntimeException("No se pudo eliminar la orden");
        }

        con.commit();
        return true;

    } catch (Exception e) {
        try {
            if (con != null) {
                con.rollback();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        throw new RuntimeException(e);
    } finally {
        try {
            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
        }
    }
}
}