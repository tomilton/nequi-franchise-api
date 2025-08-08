
SELECT 
    f.id AS franchiseId,
    f.name AS franchiseName,
    s.id AS sucursalId,
    s.name AS nombreSucursal,
    p.id AS productoId,
    p.name AS nombreProducto,
    p.stock
FROM franchise f
JOIN sucursal s ON s.franchise_id = f.id
JOIN (
    SELECT 
        p1.sucursal_id,
        p1.id,
        p1.name,
        p1.stock
    FROM product p1
    WHERE p1.stock = (
        SELECT MAX(p2.stock)
        FROM product p2
        WHERE p2.sucursal_id = p1.sucursal_id
    )
) p ON s.id = p.sucursal_id
WHERE f.id = 1;


-- Insertar franquicias
INSERT INTO franchise (id, name) VALUES (1, 'Franquicia Norte');
INSERT INTO franchise (id, name) VALUES (2, 'Franquicia Sur');

-- Insertar sucursales
INSERT INTO sucursal (id, franchise_id, name) VALUES (1, 1, 'Sucursal Medellín');
INSERT INTO sucursal (id, franchise_id, name) VALUES (2, 1, 'Sucursal Bogotá');
INSERT INTO sucursal (id, franchise_id, name) VALUES (3, 2, 'Sucursal Cali');

-- Insertar productos para Sucursal Medellín
INSERT INTO product (id, sucursal_id, name, stock) VALUES (1, 1, 'Coca Cola', 120);
INSERT INTO product (id, sucursal_id, name, stock) VALUES (2, 1, 'Pepsi', 150);
INSERT INTO product (id, sucursal_id, name, stock) VALUES (3, 1, 'Agua', 80);

-- Insertar productos para Sucursal Bogotá
INSERT INTO product (id, sucursal_id, name, stock) VALUES (4, 2, 'Coca Cola', 200);
INSERT INTO product (id, sucursal_id, name, stock) VALUES (5, 2, 'Pepsi', 180);

-- Insertar productos para Sucursal Cali (otra franquicia)
INSERT INTO product (id, sucursal_id, name, stock) VALUES (6, 3, 'Coca Cola', 90);
INSERT INTO product (id, sucursal_id, name, stock) VALUES (7, 3, 'Pepsi', 95);


