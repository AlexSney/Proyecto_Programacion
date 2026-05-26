package Interfas;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Controlador {
    private Map<String, String> usuarios;
    private Scanner scanner;
    private String usuarioLogeado;

    
    public Controlador() {
        usuarios = new HashMap<>();
        scanner = new Scanner(System.in);
        usuarioLogeado = null;
/**
     *Aqui van a crear sus usuarios y contraseñas para que puedan iniciar sesión, pueden agregar los que quieran, pero al menos deben agregar uno para poder probar el sistema de login. 
     */
        usuarios.put("Elpapu", "1234");
    }

    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=================================");
            System.out.println("           TUgamesTI         ");
            System.out.println("=================================");
            System.out.println("1. Iniciar Sesión (Login)");
            System.out.println("2. Registrarse");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            String opcion = scanner.nextLine();
            switch (opcion) {
                case "1":
                Animaciones animacion = new Animaciones();
                animacion.mostrarCarga(100);
                    login();
                    break;
                case "2":
                Animaciones animacion2 = new Animaciones();
                animacion2.mostrarCarga(100);
                    registrar();
                    break;
                case "3":
                Animaciones animacion3 = new Animaciones();
                animacion3.mostrarCarga(100);
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