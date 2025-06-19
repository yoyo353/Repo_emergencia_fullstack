-- Datos iniciales para la tabla inventario
-- Este archivo se ejecuta automáticamente al iniciar la aplicación

INSERT INTO inventario (nombre, descripcion, precio, stock, categoria, sku, imagen_url, activo) VALUES
('Laptop Dell XPS 13', 'Laptop ultradelgada con Intel Core i7, 16GB RAM, 512GB SSD', 1299.99, 15, 'Electrónica', 'DELL-XPS-13', 'https://ejemplo.com/dell-xps-13.jpg', true),
('Mouse Logitech MX Master 3', 'Mouse inalámbrico ergonómico con scroll electromagnético', 99.99, 50, 'Accesorios', 'LOG-MX3', 'https://ejemplo.com/mx-master-3.jpg', true),
('Teclado Mecánico Keychron K2', 'Teclado mecánico inalámbrico RGB, switches Gateron', 89.99, 30, 'Accesorios', 'KEY-K2', 'https://ejemplo.com/keychron-k2.jpg', true),
('Monitor LG 27" 4K', 'Monitor IPS 4K HDR, 27 pulgadas, 60Hz', 449.99, 20, 'Electrónica', 'LG-27-4K', 'https://ejemplo.com/lg-27-4k.jpg', true),
('Webcam Logitech C920', 'Webcam HD 1080p con micrófono integrado', 79.99, 40, 'Accesorios', 'LOG-C920', 'https://ejemplo.com/c920.jpg', true),
('MacBook Pro 14"', 'MacBook Pro M2 Pro, 16GB RAM, 512GB SSD', 1999.99, 10, 'Electrónica', 'APPLE-MBP-14', 'https://ejemplo.com/macbook-pro-14.jpg', true),
('iPad Air', 'iPad Air 5ta generación, 64GB WiFi', 599.99, 25, 'Electrónica', 'APPLE-IPAD-AIR', 'https://ejemplo.com/ipad-air.jpg', true),
('Samsung Galaxy S23', 'Smartphone Samsung Galaxy S23, 256GB', 999.99, 35, 'Electrónica', 'SAMSUNG-S23', 'https://ejemplo.com/galaxy-s23.jpg', true),
('Audífonos Sony WH-1000XM4', 'Audífonos inalámbricos con cancelación de ruido', 349.99, 30, 'Audio', 'SONY-WH1000XM4', 'https://ejemplo.com/sony-wh1000xm4.jpg', true),
('SSD Samsung 980 Pro 1TB', 'SSD NVMe PCIe 4.0, 1TB', 129.99, 60, 'Almacenamiento', 'SAMSUNG-980PRO-1TB', 'https://ejemplo.com/980-pro.jpg', true),
('Memoria RAM Corsair 32GB', 'Kit 2x16GB DDR4 3200MHz', 149.99, 40, 'Componentes', 'CORSAIR-32GB-DDR4', 'https://ejemplo.com/corsair-ram.jpg', true),
('Silla Gamer Secretlab', 'Silla ergonómica para gaming', 449.99, 15, 'Muebles', 'SECRETLAB-TITAN', 'https://ejemplo.com/secretlab.jpg', true),
('Micrófono Blue Yeti', 'Micrófono USB profesional', 99.99, 25, 'Audio', 'BLUE-YETI', 'https://ejemplo.com/blue-yeti.jpg', true),
('Hub USB-C 7 en 1', 'Hub USB-C con HDMI, USB 3.0, SD', 49.99, 80, 'Accesorios', 'HUB-USBC-7IN1', 'https://ejemplo.com/usb-hub.jpg', true),
('Cable HDMI 2.1', 'Cable HDMI 2.1 de 2 metros, 8K', 19.99, 100, 'Cables', 'HDMI-21-2M', 'https://ejemplo.com/hdmi-cable.jpg', true);