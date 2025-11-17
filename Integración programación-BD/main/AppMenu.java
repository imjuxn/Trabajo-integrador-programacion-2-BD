package main;

import entities.Vehiculo;
import service.VehiculoService;
import dao.VehiculoDao;
import dao.SeguroVehicularDao;
import dao.impl.SeguroVehicularDaoImpl;
import dao.impl.VehiculoDaoImpl;

import java.util.Optional;
import java.util.Scanner;

/**
 *
 * @author Nilus Global
 */
public class AppMenu {
    private VehiculoService vehiculoService;
    private Scanner sc = new Scanner(System.in);

    public AppMenu() {
    VehiculoDao vehiculoDao = new VehiculoDaoImpl();  
    SeguroVehicularDao seguroDao = new SeguroVehicularDaoImpl();

    vehiculoService = new VehiculoService(vehiculoDao, seguroDao);
}

    public void iniciar() {
        int opcion = -1;
        do {
            mostrarMenu();
            opcion = leerEntero("Opción: ");

            switch (opcion) {
                case 1 -> crearVehiculo();
                case 2 -> listarVehiculos();
                case 3 -> buscarPorPatente();
                case 4 -> eliminarVehiculo();
                case 5 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 5);
    }

    private void mostrarMenu() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1) Crear Vehículo");
        System.out.println("2) Listar Vehículos");
        System.out.println("3) Buscar Vehículo por patente");
        System.out.println("4) Eliminar Vehículo (lógico)");
        System.out.println("5) Salir");
    }

    private void crearVehiculo() {
        try {
            System.out.println("\n--- Crear vehículo ---");

            String patente = leerTexto("Patente: ").toUpperCase();
            String marca   = leerTexto("Marca: ");
            String modelo  = leerTexto("Modelo: ");
            int anio       = leerEntero("Año: ");
            String tipo    = leerTexto("Tipo [auto/moto/camion]: ").toLowerCase();
            String color   = leerTexto("Color: ");
            long idCliente = leerEntero("ID_Cliente: ");

            Vehiculo v = new Vehiculo();
            v.setPatente(patente);
            v.setMarca(marca);
            v.setModelo(modelo);
            v.setAnio(anio);
            v.setTipo(tipo);
            v.setColor(color);
            v.setIdCliente(idCliente);

            vehiculoService.crear(v);
            System.out.println("Vehículo creado con éxito.");

        } catch (Exception e) {
            System.out.println("❌ Error al crear: " + e.getMessage());
        }
    }

    private void listarVehiculos() {
        try {
            System.out.println("\n--- Lista ---");
            vehiculoService.getAll().forEach(v ->
                System.out.println(v.getId() + " - " + v.getPatente())
            );
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void buscarPorPatente() {
        String pat = leerTexto("Patente: ").toUpperCase();
        try {
            Optional<Vehiculo> opt = vehiculoService.buscarPorPatente(pat);
            if (opt.isEmpty()) System.out.println("No encontrado.");
            else System.out.println(opt.get());
        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private void eliminarVehiculo() {
        long id = leerEntero("ID a eliminar: ");
        try {
            boolean ok = vehiculoService.eliminar(id);
            System.out.println(ok ? "Eliminado lógicamente." : "No se encontró el ID.");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private String leerTexto(String msg) {
        System.out.print(msg);
        return sc.nextLine();
    }

    private int leerEntero(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Ingrese un número válido.");
            }
        }
    }
}
