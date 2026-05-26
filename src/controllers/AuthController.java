package controllers;

import models.User;
import services.AuthService;
import utils.InputHelper;
import utils.Printer;

/**
 * Controlador de autenticación.
 * Solo se encarga de mostrar menús y capturar datos del usuario.
 * La lógica real está en AuthService.
 */
public class AuthController {

    private AuthService authService;

    // ── Constructor ────────────────────────────────────────────────────────────

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // ── Menú principal de autenticación ───────────────────────────────────────

    /**
     * Muestra el menú de bienvenida y retorna el usuario que hizo login,
     * o null si el usuario eligió salir.
     *
     * Usamos un autómata simple de estados:
     *   INICIO → (login | registro | salir)
     */
    public User menuAutenticacion() {
        while (true) {
            Printer.bannerPrincipal();
            System.out.println("   Bienvenido a NexusGames");
            System.out.println();
            System.out.println("   [1] Iniciar sesión");
            System.out.println("   [2] Registrarse");
            System.out.println("   [0] Salir");
            System.out.println();

            int opcion = InputHelper.leerEntero("   Elige una opción: ");

            switch (opcion) {
                case 1:
                    User u = menuLogin();
                    if (u != null) return u;
                    break;
                case 2:
                    menuRegistro();
                    break;
                case 0:
                    System.out.println("\n   ¡Hasta pronto! Gracias por usar NexusGames.");
                    return null;
                default:
                    Printer.aviso("Opción no válida. Intenta de nuevo.");
                    InputHelper.pausar();
            }
        }
    }

    // ── Login ──────────────────────────────────────────────────────────────────

    private User menuLogin() {
        Printer.titulo(" INICIAR SESIÓN");
        String username = InputHelper.leerTexto("   Usuario: ");
        String password = InputHelper.leerTexto("   Contraseña: ");

        User usuario = authService.login(username, password);

        if (usuario != null) {
            Printer.exito("¡Bienvenido de vuelta, " + usuario.getUsername() + "!");
            InputHelper.pausar();
            return usuario;
        } else {
            Printer.error("Usuario o contraseña incorrectos.");
            InputHelper.pausar();
            return null;
        }
    }

    // ── Registro ───────────────────────────────────────────────────────────────

    private void menuRegistro() {
        Printer.titulo(" CREAR CUENTA");
        String username = InputHelper.leerTexto("   Nuevo usuario: ");
        String password = InputHelper.leerTexto("   Contraseña (mín. 4 caracteres): ");

        String resultado = authService.registrar(username, password);

        // El resultado tiene formato "OK:mensaje" o "ERROR:mensaje"
        String[] partes = resultado.split(":", 2);
        if ("OK".equals(partes[0])) {
            Printer.exito(partes[1]);
        } else {
            Printer.error(partes[1]);
        }
        InputHelper.pausar();
    }
}
