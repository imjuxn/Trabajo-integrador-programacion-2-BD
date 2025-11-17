# SeguroVehicular — Trabajo Integrador Programación 2

**Dominio:** sistema de gestión de seguros vehiculares (Clientes, Vehículos, Seguros)

## Contenido del repositorio
- `Base de datos/tablas.sql` — script para crear la base de datos y tablas.
- `Base de datos/datos.sql` — datos de prueba (inserts).
- `Integración programación-BD/` — código fuente Java (packages: config, entities, dao, dao.impl, service, main).
- `UML/` — diagrama UML (PlantUML y PNG).
- `informe/Informe_Trabajo_Integrador.pdf` — informe final (6–8 páginas).
- `video/demostracion.mp4` — video de 10–15 minutos.
- `docs/` — capturas de pantalla usadas en el informe/video.

## Requisitos
- Java JDK 21 (o 11)  
- Maven (opcional) o compilar con javac  
- MySQL 8.x (Workbench)  
- Conexión local (localhost)

## Credenciales de prueba (usar en `config/DatabaseConnection.java`)
- Host: `127.0.0.1`  
- Puerto: `3306`  
- Base de datos: `ProgramacionIntegracion`  
- Usuario: `root`  
- Contraseña: `Juan12345`  

## Cómo crear la base de datos
1. Abrir MySQL Workbench o cliente MySQL.
2. Ejecutar `tablas.sql` para crear la base y las tablas:
```sql
SOURCE tablas.sql;
