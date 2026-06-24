# Restaurante - SANTO ANTOJO

Sistema de gestión de restaurante de escritorio desarrollado en Java Swing. Permite tomar pedidos, gestionar productos y combos, administrar usuarios y roles, y generar tickets en PDF.

## Tecnologías

- **Lenguaje:** Java 25
- **GUI:** Java Swing (NetBeans GUI Builder)
- **Build:** Apache Ant
- **Base de datos:** MariaDB
- **PDF:** OpenPDF 2.0.3
- **JDBC:** MariaDB Connector 3.1.4

## Funcionalidades

- **Autenticación:** Inicio de sesión con contraseñas hasheadas (SHA-256)
- **Pedidos:** Creación, actualización, historial y cambio de estado (procesado, despachado, anulado)
- **Productos:** CRUD de productos clasificados por categorías
- **Combos:** Creación de combos con múltiples productos y cantidades
- **Usuarios:** CRUD de usuarios con asignación de roles
- **Permisos:** Control de acceso por rol a los diferentes módulos
- **Tickets PDF:** Generación automática de comprobantes con OpenPDF
- **Panel lateral:** Navegación con restricciones según permisos del usuario

## Requisitos

- Java 25+
- Apache Ant
- MariaDB con base de datos `negocio2`

## Configuración de base de datos

- **Host:** `localhost:3306`
- **Base de datos:** `negocio2`
- **Usuario:** `root`
- **Contraseña:** *(vacía)*

La conexión se configura en `src/Clases/Conexion.java`.

## Ejecución

```bash
ant jar
java -jar dist/Restaurante2.jar
```

## Estructura del proyecto

```
src/
  Iniciar/          -- Login y ventana principal
  Clases/           -- Modelos, DAOs, servicios y utilidades
  Fpaneles/         -- Paneles del dashboard (pedidos, productos, etc.)
  disenos/          -- Componentes Swing personalizados
  medias/           -- Recursos gráficos
documentos/         -- Tickets PDF generados por fecha
```

## Entrada principal

`Iniciar.Login.java` contiene el método `main()` que inicia la aplicación.
