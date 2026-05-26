package Interfas;

import CatalogoDeJuegosModels.User;
import java.util.Scanner;
import servicios.storeservicios;

public class Controlador {

    private Scanner scanner;

    private User usuario;

    private storeservicios tienda;

    public Controlador() {

        scanner = new Scanner(System.in);

        tienda = new storeservicios();

        usuario = new User(
                "Elpapu",
                "1234",
                100);
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

                    tienda.mostrarCatalogo();
                    break;

                case "2":

                    System.out.print(
                            "Nombre del juego: ");

                    String nombre =
                            scanner.nextLine();

                    tienda.comprarJuego(
                            usuario,
                            nombre);

                    break;

                case "3":

                    System.out.println(
                            "Saldo: $" +
                            usuario.getSaldo());

                    break;

                case "4":

                    System.out.println(
                            usuario.getBiblioteca());

                    break;

                case "5":

                    salir = true;
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