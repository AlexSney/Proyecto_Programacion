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
import utils.AnimationHelper; 

public class App {

    /**
     * @parami metodo main
     * @parami descripcion punto de inicio del sistema NexusGames
     * @parami flujo inicializa sistema y controla ejecucion principal
     */
    public static void main(String[] args) {

        // animacion inicio
        AnimationHelper.barraCarga("Iniciando NexusGames");

        FileManager.inicializar();

        AuthService     authService     = new AuthService();
        GameService     gameService     = new GameService();
        WalletService   walletService   = new WalletService(authService);
        PurchaseService purchaseService = new PurchaseService(walletService, gameService);

        AuthController    authController    = new AuthController(authService);
        StoreController   storeController   = new StoreController(gameService, purchaseService);
        LibraryController libraryController = new LibraryController(purchaseService, walletService);
        AdminController   adminController   = new AdminController(gameService, authService, purchaseService);

        boolean ejecutando = true;

        while (ejecutando) {

            User usuarioActual = authController.menuAutenticacion();

            if (usuarioActual == null) {
                ejecutando = false;
                continue;
            }

            // animacion despues de login
            AnimationHelper.puntosCarga("Cargando datos del usuario");

            if (usuarioActual.esAdmin()) {
                AnimationHelper.barraCarga("Accediendo como administrador");
                menuAdmin(usuarioActual, storeController, libraryController,
                          adminController);
            } else {
                AnimationHelper.barraCarga("Accediendo como usuario");
                menuUsuario(usuarioActual, storeController, libraryController);
            }
        }

        AnimationHelper.puntosCarga("Cerrando sistema");
        System.out.println("\n  ═══ NexusGames cerrado. Hasta la proxima! ═══\n");
    }

    /**
     * @parami metodo menuUsuario
     * @parami descripcion menu principal para usuarios normales
     * @parami flujo tienda biblioteca cerrar sesion
     */
    private static void menuUsuario(User usuario,
                                    StoreController   storeCtrl,
                                    LibraryController libraryCtrl) {
        boolean sesionActiva = true;

        while (sesionActiva) {
            Printer.titulo("🎮 NEXUSGAMES — Hola, " + usuario.getUsername() +
                           "  |  Saldo: $" + String.format("%.2f", usuario.getSaldo()));
            System.out.println("   [1] Tienda");
            System.out.println("   [2] Mi Biblioteca");
            System.out.println("   [0] Cerrar sesión");
            System.out.println();

            int opcion = InputHelper.leerEntero("   Elige una opción: ");

            switch (opcion) {
                case 1:
                    AnimationHelper.puntosCarga("Abriendo tienda");
                    storeCtrl.mostrarMenuTienda(usuario);
                    break;
                case 2:
                    AnimationHelper.puntosCarga("Cargando biblioteca");
                    libraryCtrl.mostrarMenuBiblioteca(usuario);
                    break;
                case 0:
                    AnimationHelper.puntosCarga("Cerrando sesion");
                    Printer.info("Sesion cerrada. Hasta pronto!");
                    InputHelper.pausar();
                    sesionActiva = false;
                    break;
                default:
                    Printer.aviso("Opcion no valida.");
                    InputHelper.pausar();
            }
        }
    }

    /**
     * @parami metodo menuAdmin
     * @parami descripcion menu para administradores
     * @parami flujo tienda biblioteca panel admin cerrar sesion
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
                case 1:
                    AnimationHelper.puntosCarga("Abriendo tienda");
                    storeCtrl.mostrarMenuTienda(admin);
                    break;
                case 2:
                    AnimationHelper.puntosCarga("Cargando biblioteca");
                    libraryCtrl.mostrarMenuBiblioteca(admin);
                    break;
                case 3:
                    AnimationHelper.puntosCarga("Entrando al panel admin");
                    adminCtrl.mostrarMenuAdmin(admin);
                    break;
                case 0:
                    AnimationHelper.puntosCarga("Cerrando sesion admin");
                    Printer.info("Sesion admin cerrada.");
                    InputHelper.pausar();
                    sesionActiva = false;
                    break;
                default:
                    Printer.aviso("Opcion no valida.");
                    InputHelper.pausar();
            }
        }
    }
}