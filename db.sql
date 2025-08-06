-- Tabla para Franquicias
CREATE TABLE franchise (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(100) NOT NULL,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           INDEX idx_franchise_name (name)
) ENGINE=InnoDB;

-- Tabla para Sucursales
CREATE TABLE branch (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        franchise_id INT NOT NULL,
                        name VARCHAR(100) NOT NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        FOREIGN KEY (franchise_id) REFERENCES franchise(id) ON DELETE CASCADE,
                        INDEX idx_branch_name (name),
                        INDEX idx_franchise_branch (franchise_id, name)
) ENGINE=InnoDB;

-- Tabla para Productos
CREATE TABLE product (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         branch_id INT NOT NULL,
                         name VARCHAR(100) NOT NULL,
                         stock INT NOT NULL DEFAULT 0 CHECK (stock >= 0),
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         FOREIGN KEY (branch_id) REFERENCES branch(id) ON DELETE CASCADE,
                         INDEX idx_product_name (name),
                         INDEX idx_product_stock (stock),
                         INDEX idx_branch_product (branch_id, name)
) ENGINE=InnoDB;