-- Crear base de datos para el microservicio de carrito
CREATE DATABASE IF NOT EXISTS carrito_db;
USE carrito_db;

-- Tabla de carrito
CREATE TABLE IF NOT EXISTS carrito (
    id BIGINT NOT NULL AUTO_INCREMENT,
    usuario_id BIGINT NOT NULL,
    total DECIMAL(10,2) DEFAULT 0.00,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX idx_usuario_id (usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla de items del carrito
CREATE TABLE IF NOT EXISTS item_carrito (
    id BIGINT NOT NULL AUTO_INCREMENT,
    carrito_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,
    nombre_producto VARCHAR(255) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    cantidad INT NOT NULL DEFAULT 1,
    subtotal DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (carrito_id) REFERENCES carrito(id) ON DELETE CASCADE,
    INDEX idx_producto_id (producto_id),
    INDEX idx_carrito_id (carrito_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Crear base de datos para usuarios (si no existe)
CREATE DATABASE IF NOT EXISTS usuarios_db;
USE usuarios_db;

-- Tabla de usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    telefono VARCHAR(20),
    direccion VARCHAR(255),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    rol VARCHAR(50) DEFAULT 'CLIENTE',
    PRIMARY KEY (id),
    UNIQUE KEY unique_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Crear base de datos para inventario (si no existe)
CREATE DATABASE IF NOT EXISTS inventario_db;
USE inventario_db;

-- Tabla de productos/inventario
CREATE TABLE IF NOT EXISTS inventario (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    categoria VARCHAR(100),
    sku VARCHAR(100) UNIQUE,
    imagen_url VARCHAR(500),
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY unique_sku (sku),
    INDEX idx_categoria (categoria),
    INDEX idx_nombre (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Insertar datos de prueba en usuarios
INSERT INTO usuarios_db.usuarios (nombre, apellido, email, password, telefono, rol) VALUES
('Admin', 'Sistema', 'admin@tienda.com', '$2a$10$DOMDxjYyfZ/e7RcBfUpzqeaCs8pLgcizuiQWXPkU35nOhZlFcE9MS', '123456789', 'ADMIN'),
('Juan', 'Pérez', 'juan.perez@email.com', '$2a$10$DOMDxjYyfZ/e7RcBfUpzqeaCs8pLgcizuiQWXPkU35nOhZlFcE9MS', '987654321', 'CLIENTE'),
('María', 'González', 'maria.gonzalez@email.com', '$2a$10$DOMDxjYyfZ/e7RcBfUpzqeaCs8pLgcizuiQWXPkU35nOhZlFcE9MS', '456789123', 'CLIENTE');

-- Insertar datos de prueba en inventario
INSERT INTO inventario_db.inventario (nombre, descripcion, precio, stock, categoria, sku) VALUES
('Laptop Dell XPS 13', 'Laptop ultradelgada con Intel Core i7', 1299.99, 15, 'Electrónica', 'DELL-XPS-13'),
('Mouse Logitech MX Master 3', 'Mouse inalámbrico ergonómico', 99.99, 50, 'Accesorios', 'LOG-MX3'),
('Teclado Mecánico Keychron K2', 'Teclado mecánico inalámbrico RGB', 89.99, 30, 'Accesorios', 'KEY-K2'),
('Monitor LG 27" 4K', 'Monitor IPS 4K HDR', 449.99, 20, 'Electrónica', 'LG-27-4K'),
('Webcam Logitech C920', 'Webcam HD 1080p', 79.99, 40, 'Accesorios', 'LOG-C920');