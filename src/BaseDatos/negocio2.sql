-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 24-06-2026 a las 15:17:57
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `negocio2`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE `categoria` (
  `idCategoria` int(11) NOT NULL,
  `categoria` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`idCategoria`, `categoria`) VALUES
(1, 'Entradas'),
(2, 'Platillos fuertes'),
(3, 'Bebidas'),
(4, 'Postres'),
(5, 'Extras');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `combo`
--

CREATE TABLE `combo` (
  `idCombo` int(11) NOT NULL,
  `combo` varchar(150) NOT NULL,
  `precioCombo` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `combo`
--

INSERT INTO `combo` (`idCombo`, `combo`, `precioCombo`) VALUES
(1, 'EMPAREJADOS', 12.00),
(2, 'COMBO PARA 2', 0.25),
(3, 'COMBO GAMER', 15.00),
(4, 'NOVA LEGEDS', 20.00),
(5, 'COMBO AMIGOS', 22.00),
(7, 'LOS ULTIMOS JEDI', 21.99),
(10, 'COMBO FAMILIAR', 30.99),
(11, 'COMBO MEGA MIX', 115.00),
(12, 'Mezcla total', 23.00),
(13, 'Combo Premiun', 10.00),
(14, 'Ataco city', 13.99),
(15, 'Combo para compartir', 15.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle`
--

CREATE TABLE `detalle` (
  `idCombo` int(11) NOT NULL,
  `idProducto` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `detalle`
--

INSERT INTO `detalle` (`idCombo`, `idProducto`, `cantidad`) VALUES
(1, 2, 2),
(1, 5, 2),
(1, 7, 3),
(1, 11, 1),
(2, 5, 1),
(3, 2, 2),
(3, 4, 2),
(3, 5, 1),
(3, 8, 1),
(4, 1, 1),
(4, 2, 1),
(4, 3, 1),
(4, 6, 1),
(4, 8, 1),
(4, 9, 1),
(5, 2, 1),
(5, 3, 1),
(5, 4, 1),
(5, 5, 1),
(5, 6, 2),
(5, 7, 1),
(5, 8, 1),
(7, 1, 1),
(7, 2, 2),
(7, 3, 1),
(7, 5, 1),
(7, 6, 1),
(7, 8, 2),
(7, 9, 1),
(10, 1, 2),
(10, 2, 1),
(10, 3, 1),
(10, 4, 1),
(10, 5, 2),
(10, 6, 1),
(10, 7, 2),
(10, 9, 2),
(11, 1, 1),
(11, 2, 5),
(11, 3, 5),
(11, 4, 1),
(11, 6, 2),
(11, 7, 6),
(11, 8, 5),
(11, 9, 10),
(11, 12, 1),
(12, 3, 1),
(12, 4, 1),
(12, 5, 1),
(12, 6, 1),
(12, 7, 1),
(12, 8, 1),
(12, 9, 1),
(12, 10, 1),
(13, 2, 1),
(13, 5, 1),
(13, 8, 2),
(13, 12, 1),
(13, 13, 1),
(14, 1, 1),
(14, 4, 1),
(14, 7, 1),
(14, 10, 1),
(14, 11, 1),
(14, 12, 1),
(15, 10, 2),
(15, 11, 2),
(15, 12, 2),
(15, 13, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalleorden`
--

CREATE TABLE `detalleorden` (
  `idOrden` int(11) NOT NULL,
  `idLinea` int(11) NOT NULL,
  `idProducto` int(11) DEFAULT NULL,
  `idCombo` int(11) DEFAULT NULL,
  `cantidad` int(11) NOT NULL,
  `precioUnitario` decimal(10,2) NOT NULL,
  `modified_by` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `detalleorden`
--

INSERT INTO `detalleorden` (`idOrden`, `idLinea`, `idProducto`, `idCombo`, `cantidad`, `precioUnitario`, `modified_by`) VALUES
(35, 1, NULL, 1, 1, 12.00, NULL),
(46, 1, NULL, 1, 1, 12.00, NULL),
(46, 2, NULL, 2, 1, 0.25, NULL),
(46, 3, NULL, 3, 1, 15.00, NULL),
(47, 1, NULL, 1, 1, 12.00, NULL),
(47, 2, NULL, 4, 1, 20.00, NULL),
(48, 1, NULL, 1, 1, 12.00, NULL),
(48, 2, 7, NULL, 1, 2.00, NULL),
(49, 1, 10, NULL, 1, 5.00, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `orden`
--

CREATE TABLE `orden` (
  `idOrden` int(11) NOT NULL,
  `fechaHora` datetime DEFAULT current_timestamp(),
  `idUsuario` int(11) NOT NULL,
  `total` decimal(10,2) DEFAULT 0.00,
  `estado` varchar(20) NOT NULL DEFAULT 'pendiente',
  `mesa` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `orden`
--

INSERT INTO `orden` (`idOrden`, `fechaHora`, `idUsuario`, `total`, `estado`, `mesa`) VALUES
(35, '2026-06-04 14:11:49', 19, 12.00, 'despachado', 0),
(46, '2026-06-20 14:36:26', 17, 27.25, 'despachado', 0),
(47, '2026-06-22 17:29:49', 17, 32.00, 'anulado', 0),
(48, '2026-06-23 11:06:20', 24, 14.00, 'despachado', 0),
(49, '2026-06-23 11:06:45', 24, 5.00, 'anulado', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `permiso`
--

CREATE TABLE `permiso` (
  `idPermiso` int(11) NOT NULL,
  `permiso` varchar(50) NOT NULL,
  `descripcion` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `permiso`
--

INSERT INTO `permiso` (`idPermiso`, `permiso`, `descripcion`) VALUES
(1, 'ACCESO_ORDENES', 'Acceder al panel de órdenes'),
(2, 'ACCESO_COMBOS', 'Acceder al panel de combos'),
(3, 'ACCESO_PRODUCTOS', 'Acceder al panel de productos'),
(4, 'ACCESO_USUARIOS', 'Acceder al panel de gestión de usuarios');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `idProducto` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `descripcion` varchar(150) NOT NULL,
  `precio` decimal(10,2) NOT NULL,
  `idCategoria` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`idProducto`, `nombre`, `descripcion`, `precio`, `idCategoria`) VALUES
(1, 'Guacamole tradicional', 'Aguacate fresco preparado con tomate, cebolla, cilantro y limón', 3.00, 1),
(2, 'Tacos al pastor', 'Orden de 3 tacos de cerdo adobado con piña, cebolla y cilantro', 4.50, 2),
(3, 'Horchata artesanal', 'Bebida de arroz con canela', 2.50, 3),
(4, 'Flan napolitano', 'Postre de leche con caramelo', 2.50, 4),
(5, 'Salsa habanera', 'Salsa picante', 1.00, 5),
(6, 'La tortuga', 'Pan con carne y queso', 5.00, 2),
(7, 'Agua de tamarindo', 'Bebida natural', 2.00, 3),
(8, 'Agua de jamaica', 'Bebida natural', 2.00, 3),
(9, 'Tacos de canasta', 'Tacos con papa', 5.00, 2),
(10, 'Taquisa mix', 'Tacos de eleccion al gusto', 5.00, 5),
(11, 'Palitos mix', 'palitos de papa con sazon', 2.50, 5),
(12, 'Pupusas Salvadoreñas', 'Tortillas con relleno de queso y acomplamientos', 0.35, 5),
(13, 'Arepas colombianas', 'tortillas con queso derritiido', 1.50, 5),
(14, 'chocolate', 'bebida base de cacao', 1.00, 3),
(16, 'Taquisa mix v2', 'orden de tacos al azar', 5.25, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol`
--

CREATE TABLE `rol` (
  `idRol` int(11) NOT NULL,
  `rol` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `rol`
--

INSERT INTO `rol` (`idRol`, `rol`) VALUES
(1, 'ADMINISTRADOR'),
(3, 'COCINERO'),
(4, 'MESERO'),
(2, 'SUPERVISOR');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rolpermiso`
--

CREATE TABLE `rolpermiso` (
  `idRol` int(11) NOT NULL,
  `idPermiso` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `rolpermiso`
--

INSERT INTO `rolpermiso` (`idRol`, `idPermiso`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(2, 1),
(3, 1),
(3, 3),
(4, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `idUsuario` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `apellido` varchar(100) NOT NULL,
  `usuario` varchar(50) NOT NULL,
  `contrasenia` varchar(255) NOT NULL,
  `idRol` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`idUsuario`, `nombre`, `apellido`, `usuario`, `contrasenia`, `idRol`) VALUES
(1, 'José', 'Colón', 'jrcolon', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 1),
(17, 'Brandon', 'Montano', 'dm24001', '20f3765880a5c269b747e1e906054a4b4a3a991259f1e16b5dde4742cec2319a', 1),
(19, 'Diego', 'Pérez', 'Diego503', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 4),
(21, 'Milton', 'Lopez', 'Milo', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 3),
(22, 'Griselda', 'Lopez', 'grilo', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 2),
(23, 'mario', 'lopez', 'mario', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 4),
(24, 'maria', 'perez', 'mare', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 4);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`idCategoria`);

--
-- Indices de la tabla `combo`
--
ALTER TABLE `combo`
  ADD PRIMARY KEY (`idCombo`);

--
-- Indices de la tabla `detalle`
--
ALTER TABLE `detalle`
  ADD PRIMARY KEY (`idCombo`,`idProducto`),
  ADD KEY `detalle_ibfk_1` (`idProducto`);

--
-- Indices de la tabla `detalleorden`
--
ALTER TABLE `detalleorden`
  ADD PRIMARY KEY (`idOrden`,`idLinea`),
  ADD UNIQUE KEY `idOrden_prod` (`idOrden`,`idProducto`),
  ADD UNIQUE KEY `idOrden_combo` (`idOrden`,`idCombo`),
  ADD KEY `idProducto` (`idProducto`),
  ADD KEY `idCombo` (`idCombo`);

--
-- Indices de la tabla `orden`
--
ALTER TABLE `orden`
  ADD PRIMARY KEY (`idOrden`),
  ADD KEY `idUsuario` (`idUsuario`);

--
-- Indices de la tabla `permiso`
--
ALTER TABLE `permiso`
  ADD PRIMARY KEY (`idPermiso`),
  ADD UNIQUE KEY `permiso` (`permiso`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`idProducto`),
  ADD KEY `producto_ibfk_1` (`idCategoria`);

--
-- Indices de la tabla `rol`
--
ALTER TABLE `rol`
  ADD PRIMARY KEY (`idRol`),
  ADD UNIQUE KEY `rol` (`rol`);

--
-- Indices de la tabla `rolpermiso`
--
ALTER TABLE `rolpermiso`
  ADD PRIMARY KEY (`idRol`,`idPermiso`),
  ADD KEY `idPermiso` (`idPermiso`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`idUsuario`),
  ADD UNIQUE KEY `usuario` (`usuario`),
  ADD KEY `idRol` (`idRol`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `idCategoria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `combo`
--
ALTER TABLE `combo`
  MODIFY `idCombo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de la tabla `orden`
--
ALTER TABLE `orden`
  MODIFY `idOrden` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=50;

--
-- AUTO_INCREMENT de la tabla `permiso`
--
ALTER TABLE `permiso`
  MODIFY `idPermiso` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `idProducto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT de la tabla `rol`
--
ALTER TABLE `rol`
  MODIFY `idRol` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `idUsuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detalle`
--
ALTER TABLE `detalle`
  ADD CONSTRAINT `detalle_ibfk_1` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`),
  ADD CONSTRAINT `detalle_ibfk_2` FOREIGN KEY (`idCombo`) REFERENCES `combo` (`idCombo`);

--
-- Filtros para la tabla `detalleorden`
--
ALTER TABLE `detalleorden`
  ADD CONSTRAINT `detalleorden_ibfk_1` FOREIGN KEY (`idOrden`) REFERENCES `orden` (`idOrden`) ON DELETE CASCADE,
  ADD CONSTRAINT `detalleorden_ibfk_2` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`),
  ADD CONSTRAINT `detalleorden_ibfk_3` FOREIGN KEY (`idCombo`) REFERENCES `combo` (`idCombo`);

--
-- Filtros para la tabla `orden`
--
ALTER TABLE `orden`
  ADD CONSTRAINT `orden_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`);

--
-- Filtros para la tabla `producto`
--
ALTER TABLE `producto`
  ADD CONSTRAINT `producto_ibfk_1` FOREIGN KEY (`idCategoria`) REFERENCES `categoria` (`idCategoria`);

--
-- Filtros para la tabla `rolpermiso`
--
ALTER TABLE `rolpermiso`
  ADD CONSTRAINT `rolPermiso_ibfk_1` FOREIGN KEY (`idRol`) REFERENCES `rol` (`idRol`) ON DELETE CASCADE,
  ADD CONSTRAINT `rolPermiso_ibfk_2` FOREIGN KEY (`idPermiso`) REFERENCES `permiso` (`idPermiso`) ON DELETE CASCADE;

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`idRol`) REFERENCES `rol` (`idRol`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
