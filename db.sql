-- Tabla para Franquicias
CREATE TABLE franchise (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(100) NOT NULL,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           INDEX idx_franchise_name (name)
) ENGINE=InnoDB;

-- Tabla para Sucursales
CREATE TABLE sucursal (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        franchise_id INT NOT NULL,
                        name VARCHAR(100) NOT NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        FOREIGN KEY (franchise_id) REFERENCES franchise(id) ON DELETE CASCADE,
                        INDEX idx_sucursal_name (name),
                        INDEX idx_franchise_sucursal (franchise_id, name)
) ENGINE=InnoDB;

-- Tabla para Productos
CREATE TABLE product (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         sucursal_id INT NOT NULL,
                         name VARCHAR(100) NOT NULL,
                         stock INT NOT NULL DEFAULT 0 CHECK (stock >= 0),
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         FOREIGN KEY (sucursal_id) REFERENCES sucursal(id) ON DELETE CASCADE,
                         INDEX idx_product_name (name),
                         INDEX idx_product_stock (stock),
                         INDEX idx_sucursal_product (sucursal_id, name)
) ENGINE=InnoDB;