package zona_fit.presentacion;

import zona_fit.datos.ClienteDAO;
import zona_fit.datos.IClienteDAO;
import zona_fit.dominio.Cliente;

import java.util.Scanner;

public class ZonaFitApp {
    public static void main(String[] args) {
        zonaFitApp();
    }

    private static void zonaFitApp() {
        var salir = false;
        var consola = new Scanner(System.in);

//        Se crea un objeto de la clase clienteDAO
        IClienteDAO clienteDao = new ClienteDAO();

        while (!salir){
            try {
                var opcion = mostrarMenu(consola);
                salir = ejecutarOpciones(consola, opcion, clienteDao);
            }catch (Exception e){
                System.out.println("Error al ejecutar opciones: " + e.getMessage());
            }
            System.out.println();
        }
    }

    private static int mostrarMenu(Scanner consola){
        System.out.print("""
                ----------------------
                *** Zona Fit (GYM) ***
                ----------------------
                1. Listar clientes
                2. Buscar cliente
                3. Agregar cliente
                4. Modificar cliente
                5. Eliminar cliente
                6. Salir
                Elije una opcion:\s""");
        return Integer.parseInt(consola.nextLine());
    }

    private static boolean ejecutarOpciones(Scanner consola, int opcion, IClienteDAO clienteDAO){
        var salir = false;
        switch (opcion){
            case 1 -> {
                System.out.println("""
                        ----------------------
                        ---Listar clientes ---
                        ----------------------""");
                var clientes = clienteDAO.listarClientes();
                clientes.forEach(System.out::println);
            }
            case 2 -> {
                System.out.println("""
                        -----------------------
                        --- Buscar cliente ----
                        -----------------------""");
                System.out.print("Introduce el id del cliente a buscar: ");

                var idCliente = Integer.parseInt(consola.nextLine());
                var cliente = new Cliente(idCliente);
                var encontrado = clienteDAO.buscarClientePorId(cliente);
                if (encontrado)
                    System.out.println("Cliente encontrado: " + cliente);
                else
                    System.out.println("Cliente no encontrado: " + cliente);
            }
            case 3 -> {
                System.out.println("""
                        -----------------------
                        --- Agregar cliente ---
                        -----------------------""");
                System.out.print("Nombre: ");
                var nombre = consola.nextLine();
                System.out.print("Apellido: ");
                var apellido = consola.nextLine();
                System.out.print("Membresia: ");
                var membresia = Integer.parseInt(consola.nextLine());

//                Se crea el objeto cliente (sin id)
                var cliente = new Cliente(nombre, apellido, membresia);
                var agregado = clienteDAO.agregarCliente(cliente);
                if (agregado)
                    System.out.println("Cliente agregado: " + cliente);
                else
                    System.out.println("Cliente no agregado: " + cliente);
            }
            case 4 -> {
                System.out.println("""
                        -------------------------
                        --- Modificar cliente ---
                        -------------------------""");
                System.out.print("Id cliente: ");
                var idCliente = Integer.parseInt(consola.nextLine());
                System.out.print("Nombre: ");
                var nombre = consola.nextLine();
                System.out.print("Apellido: ");
                var apellido = consola.nextLine();
                System.out.print("Membresia: ");
                var membresia = Integer.parseInt(consola.nextLine());

//                Se crea el objeto a modificar
                var cliente = new Cliente(idCliente, nombre, apellido, membresia);
                var modificado = clienteDAO.modificarCliente(cliente);
                if (modificado)
                    System.out.println("Cliente modificado: " + cliente);
                else
                    System.out.println("Cliente no modificado: " + cliente);
            }
            case 5 -> {
                System.out.println("""
                        ------------------------
                        --- Eliminar cliente ---
                        ------------------------""");
                System.out.println("Id cliente");
                var idCliente = Integer.parseInt(consola.nextLine());

//                Se crea el objeto a eliminar
                var cliente = new Cliente(idCliente);
                var eliminado = clienteDAO.eliminarCliente(cliente);
                if (eliminado)
                    System.out.println("Cliente eliminado: " + cliente);
                else
                    System.out.println("Cliente no eliminado: " + cliente);
            }
            case 6 -> {
                System.out.println("""
                        ----------------------
                        --- Salida exitosa ---
                        ----------------------
                        Hasta pronto!""");
                salir = true;
            }
            default -> System.out.println("Opción inválida: " + opcion);
        }
        return salir;
    }
}
