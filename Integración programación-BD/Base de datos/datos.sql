-- Creación de datos de cliente
INSERT INTO Cliente (Nombre, Apellido, DNI, Email, Direccion)
SELECT
    CONCAT('Nombre', n),
    CONCAT('Apellido', n),
    LPAD(n, 8, '0'),
    CONCAT('cliente', n, '@mail.com'),
    CONCAT('Direccion ', n)
FROM (
    SELECT ROW_NUMBER() OVER () AS n
    FROM information_schema.columns
    LIMIT 100
) AS t;

-- Creación de datos de vehículo

SET @client_count = (SELECT COUNT(*) FROM Cliente);

INSERT INTO Vehiculo (Patente, Marca, Modelo, Anio, Tipo, Color, ID_Cliente)
SELECT
    CONCAT('PAT', LPAD(n, 6, '0')),
    ELT(FLOOR(1 + RAND()*5), 'Fiat', 'Ford', 'Renault', 'Chevrolet', 'Toyota'),
    ELT(FLOOR(1 + RAND()*5), 'Sedan', 'SUV', 'Hatch', 'PickUp', 'Coupe'),
    FLOOR(1988 + RAND()*(2025 - 1988)),
    ELT(FLOOR(1 + RAND()*3), 'auto', 'moto', 'camion'),
    ELT(FLOOR(1 + RAND()*5), 'Rojo', 'Azul', 'Verde', 'Negro', 'Blanco'),
    ((n - 1) % @client_count) + 1
FROM (
    SELECT ROW_NUMBER() OVER () AS n
    FROM information_schema.columns
    LIMIT 150
) x;

-- Creación de datos de seguro

INSERT INTO SeguroVehicular 
    (ID_Vehiculo, Fecha_Inicio, Fecha_Fin, Tipo_Seguro, Monto_Asegurado, Estado)
SELECT
    v.ID_Vehiculo,
    fecha_ini,
    DATE_ADD(fecha_ini, INTERVAL FLOOR(60 + RAND()*365) DAY),
    ELT(FLOOR(1 + RAND()*4), 'total', 'terceros', 'contra robo', 'responsabilidad civil'),
    FLOOR(300000 + RAND()*700000),
    ELT(FLOOR(1 + RAND()*3), 'vigente', 'vencido', 'cancelado')
FROM (
    SELECT
        ID_Vehiculo,
        DATE_ADD('2021-01-01', INTERVAL FLOOR(RAND()*1095) DAY) AS fecha_ini
    FROM Vehiculo
    LIMIT 150
) v;
