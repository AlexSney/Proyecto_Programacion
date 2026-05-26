package Interfas;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import saldo.Saldo;

public class Controlador {
    private Map<String, String> usuarios;
    private Scanner scanner;
    private String usuarioLogeado;

    Saldo saldo = new Saldo();

    
    public Controlador() {
        usuarios = new HashMap<>();
        scanner = new Scanner(System.in);
        usuarioLogeado = null;
/**
     *Aqui van a crear sus usuarios y contraseñas para que puedan iniciar sesión, pueden agregar los que quieran, pero al menos deben agregar uno para poder probar el sistema de login. 
     */
        usuarios.put("Jhoel", "1234");
    }
    
    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=================================");
            System.out.println("       SISTEMA DE ACCESO         ");
            System.out.println("=================================");
            System.out.println("1. Iniciar Sesión (Login)");
            System.out.println("2. Registrarse");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            String opcion = scanner.nextLine();
            switch (opcion) {
                case "1":
                    login();
                    break;
                case "2":
                    registrar();
                    break;
                case "3":
                    salir = true;
                    System.out.println("\nPrograma finalizado correctamente.");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private void login() {
        System.out.println("\n--- INICIO DE SESIÓN ---");
        System.out.print("Usuario: ");
        String user = scanner.nextLine();
        System.out.print("Contraseña: ");
        String pass = scanner.nextLine();

        if (usuarios.containsKey(user) && usuarios.get(user).equals(pass)) {
            usuarioLogeado = user;
            System.out.println("\n¡Inicio de sesión exitoso! Bienvenido, " + usuarioLogeado + ".");
            
            
            menuLogeado();
            
        } else {
            System.out.println("\nError: Usuario o contraseña incorrectos.");
        }
    }

    private void registrar() {
        System.out.println("\n--- REGISTRO DE NUEVO USUARIO ---");
        System.out.print("Ingrese nuevo usuario: ");
        String user = scanner.nextLine();

        if (usuarios.containsKey(user)) {
            System.out.println("Error: El usuario ya existe.");
            return;
        }
        System.out.print("Ingrese contraseña: ");
        String pass = scanner.nextLine();

        usuarios.put(user, pass);
        System.out.println("\n¡Registro completado con éxito! Ya puede iniciar sesión.");
    }
    
    private void menuLogeado() {
        int opcionS = 0;
        boolean cerrarSesion = false;
        while (!cerrarSesion) {
            System.out.println("\n---------------------------------");
            System.out.println("  SESIÓN ACTIVA: " + usuarioLogeado.toUpperCase());
            System.out.println("---------------------------------");
            System.out.println("1. Mi Perfil (Ver Datos)");
            System.out.println("2. Cerrar Sesión");
            System.out.print("Seleccione: ");

            String opcion = scanner.nextLine();
            if (opcion.equals("1")) {
                System.out.println("\nPerfil actual: " + usuarioLogeado);
                System.out.println("Seleccione que desea hacer:");
                System.out.println("1. Consultar saldo actual");
                System.out.println("2. Agregar saldo");
                System.out.println("3. Regresar");
                opcionS = saldo.ingresarInt();
                switch (opcionS) {
                    case 1:
                        saldo.mostrarSaldo();
                        break;
                    case 2:
                        System.out.println("Cuanto saldo desea añadir: ");
                        double newSaldo = scanner.nextDouble();
                        saldo.modificarSaldo(newSaldo, true);
                        break;
                    default:
                        break;
                }

            } else if (opcion.equals("2")) {
                System.out.println("\nCerrando sesión de " + usuarioLogeado + "...");
                usuarioLogeado = null;
                cerrarSesion = true;
            } else {
                System.out.println("Opción no válida.");
            }
        }
    }
}