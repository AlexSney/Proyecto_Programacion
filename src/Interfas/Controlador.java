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

            System.out.println("\n===== MENU =====");

            System.out.println("1. Ver catalogo");
            System.out.println("2. Comprar juego");
            System.out.println("3. Ver saldo");
            System.out.println("4. Ver biblioteca");
            System.out.println("5. Salir");

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

                    System.out.println(
                            "Opcion invalida.");
            }
        }
    }
}