CREATE DATABASE ProgramacionIntegracion;
USE ProgramacionIntegracion;

DROP TABLE IF EXISTS SeguroVehicular;
DROP TABLE IF EXISTS Vehiculo;
DROP TABLE IF EXISTS Cliente;

-- CREACIÓN DE TABLAS

-- CLIENTE
CREATE TABLE Cliente (
    ID_Cliente INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(50) NOT NULL,
    Apellido VARCHAR(50) NOT NULL,
    DNI CHAR(8) NOT NULL UNIQUE,
    Email VARCHAR(100) NOT NULL UNIQUE,
    Direccion VARCHAR(100) NOT NULL,
    eliminado BOOLEAN NOT NULL DEFAULT FALSE
);

-- VEHICULO
CREATE TABLE Vehiculo (
    ID_Vehiculo INT AUTO_INCREMENT PRIMARY KEY,
    Patente VARCHAR(10) NOT NULL UNIQUE,
    Marca VARCHAR(30) NOT NULL,
    Modelo VARCHAR(30) NOT NULL,
    Anio INT NOT NULL CHECK (Anio >= 1988 AND Anio <= 2025),
    Tipo ENUM('auto', 'moto', 'camion') NOT NULL,
    Color VARCHAR(20) NOT NULL,
    ID_Cliente INT NOT NULL,
    eliminado BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (ID_Cliente) REFERENCES Cliente(ID_Cliente)
);

-- SEGURO VEHICULAR
-- Tu tabla corregida
CREATE TABLE SeguroVehicular (
  ID_Seguro        INT AUTO_INCREMENT PRIMARY KEY,
  ID_Vehiculo      INT NOT NULL,
  Fecha_Inicio     DATE NOT NULL,
  Fecha_Fin        DATE NOT NULL,
  Tipo_Seguro      ENUM('total','terceros','contra robo','responsabilidad civil') NOT NULL,
  Monto_Asegurado  DECIMAL(12,2) NOT NULL,
  Estado           ENUM('vigente','vencido','cancelado') NOT NULL,
  eliminado        BOOLEAN NOT NULL DEFAULT FALSE,

  -- 1 a 1
  CONSTRAINT uq_segurovehicular_vehiculo UNIQUE (ID_Vehiculo),

  -- FK
  CONSTRAINT fk_segurovehicular_vehiculo
    FOREIGN KEY (ID_Vehiculo) REFERENCES Vehiculo(ID_Vehiculo),

  -- CHECKs a nivel de tabla (válidos en 8.4)
  CONSTRAINT chk_fechas_seguro CHECK (Fecha_Fin > Fecha_Inicio),
  CONSTRAINT chk_monto_seguro  CHECK (Monto_Asegurado > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
