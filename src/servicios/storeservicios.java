package servicios;

import CatalogoDeJuegosModels.Juegos;
import CatalogoDeJuegosModels.User;
import data.dataJuegos;

public class storeservicios {

    // Mostrar catalogo
    public void mostrarCatalogo() {

        System.out.println("\n===== CATALOGO =====");

        for (Juegos game : data.dataJuegos.games) {

            System.out.println(game);
        }
    }

    // Buscar por nombre
    public Juegos buscarJuego(String nombre) {

        for (Juegos game : dataJuegos.games) {

            if (game.getNombre()
                    .equalsIgnoreCase(nombre)) {

                return game;
            }
        }

        return null;
    }

    // Comprar juego
    public void comprarJuego(User user,
                              String nombreJuego) {

        Juegos game = buscarJuego(nombreJuego);

        if (game == null) {

            System.out.println("Juego no encontrado.");
            return;
        }

        // VALIDAR STOCK
        if (game.getStock() <= 0) {

            System.out.println("Sin stock.");
            return;
        }

        // VALIDAR SALDO
        if (user.getSaldo() < game.getPrecio()) {

            System.out.println("Saldo insuficiente.");
            return;
        }

        // VALIDAR DUPLICADOS
        for (Juegos g : user.getBiblioteca()) {

            if (g.getNombre()
                    .equalsIgnoreCase(nombreJuego)) {

                System.out.println(
                        "Ya tienes este juego.");

                return;
            }
        }

        // HACER COMPRA
        user.descontarSaldo(game.getPrecio());

        user.getBiblioteca().add(game);

        game.setStock(game.getStock() - 1);

        System.out.println(
                "Compra realizada correctamente.");
    }
}
