

import controllers.AdminController;
import controllers.AuthController;
import controllers.LibraryController;
import controllers.StoreController;
import models.User;
import services.AuthService;
import services.GameService;
import services.PurchaseService;
import services.WalletService;
import utils.FileManager;
import utils.InputHelper;
import utils.Printer;


public class App {

    public static void main(String[] args) {

        // ── 1. Inicializar persistencia ────────────────────────────────────────
        FileManager.inicializar();

        // ── 2. Crear servicios ─────────────────────────────────────────────────
        AuthService     authService     = new AuthService();
        GameService     gameService     = new GameService();
        WalletService   walletService   = new WalletService(authService);
        PurchaseService purchaseService = new PurchaseService(walletService, gameService);

        // ── 3. Crear controladores ─────────────────────────────────────────────
        AuthController    authController    = new AuthController(authService);
        StoreController   storeController   = new StoreController(gameService, purchaseService);
        LibraryController libraryController = new LibraryController(purchaseService, walletService);
        AdminController   adminController   = new AdminController(gameService, authService, purchaseService);

        // ── 4. Flujo principal ─────────────────────────────────────────────────
        boolean ejecutando = true;

        while (ejecutando) {

            // Mostrar menú de login/registro y esperar a que el usuario inicie sesión
            User usuarioActual = authController.menuAutenticacion();

            // Si retorna null, el usuario eligió "Salir"
            if (usuarioActual == null) {
                ejecutando = false;
                continue;
            }

            // Redirigir según el rol del usuario (autómata de roles)
            if (usuarioActual.esAdmin()) {
                menuAdmin(usuarioActual, storeController, libraryController,
                          adminController);
            } else {
                menuUsuario(usuarioActual, storeController, libraryController);
            }
        }

        System.out.println("\n  ═══ NexusGames cerrado. ¡Hasta la próxima! ═══\n");
    }

    // ── Menú del usuario normal ────────────────────────────────────────────────

    /**
     * Autómata principal para usuarios con rol USER.
     * Se mantiene en bucle hasta que el usuario cierre sesión.
     */
    private static void menuUsuario(User usuario,
                                    StoreController   storeCtrl,
                                    LibraryController libraryCtrl) {
        boolean sesionActiva = true;

        while (sesionActiva) {
            Printer.titulo("NEXUSGAMES — Hola, " + usuario.getUsername() +
                           "  |  Saldo: $" + String.format("%.2f", usuario.getSaldo()));
            System.out.println("   [1] Tienda");
            System.out.println("   [2] Mi Biblioteca");
            System.out.println("   [0] Cerrar sesión");
            System.out.println();

            int opcion = InputHelper.leerEntero("   Elige una opción: ");

            switch (opcion) {
                case 1: storeCtrl.mostrarMenuTienda(usuario);       break;
                case 2: libraryCtrl.mostrarMenuBiblioteca(usuario);  break;
                case 0:
                    Printer.info("Sesión cerrada. ¡Hasta pronto!");
                    InputHelper.pausar();
                    sesionActiva = false;
                    break;
                default:
                    Printer.aviso("Opción no válida.");
                    InputHelper.pausar();
            }
        }
    }

    // ── Menú del administrador ─────────────────────────────────────────────────

    /**
     * Autómata principal para usuarios con rol ADMIN.
     * Tiene acceso a la tienda, biblioteca Y al panel admin.
     */
    private static void menuAdmin(User admin,
                                  StoreController   storeCtrl,
                                  LibraryController libraryCtrl,
                                  AdminController   adminCtrl) {
        boolean sesionActiva = true;

        while (sesionActiva) {
            Printer.titulo("👑 NEXUSGAMES ADMIN — " + admin.getUsername());
            System.out.println("   [1] Tienda");
            System.out.println("   [2] Mi Biblioteca");
            System.out.println("   [3] Panel de Administración");
            System.out.println("   [0] Cerrar sesión");
            System.out.println();

            int opcion = InputHelper.leerEntero("   Elige una opción: ");

            switch (opcion) {
                case 1: storeCtrl.mostrarMenuTienda(admin);          break;
                case 2: libraryCtrl.mostrarMenuBiblioteca(admin);    break;
                case 3: adminCtrl.mostrarMenuAdmin(admin);           break;
                case 0:
                    Printer.info("Sesión admin cerrada.");
                    InputHelper.pausar();
                    sesionActiva = false;
                    break;
                default:
                    Printer.aviso("Opción no válida.");
                    InputHelper.pausar();
            }
        }
    }
}
