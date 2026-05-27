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
    // ── Perfil, cambiar contraseña y eliminar cuenta ───────────────────────────

    public void verPerfil(User usuario) {
    Printer.titulo("👤 MI PERFIL");
    System.out.println("   Usuario      : " + usuario.getUsername());
    System.out.println("   Rol          : " + usuario.getRol());
    System.out.println("   Saldo        : $" + String.format("%.2f", usuario.getSaldo()));
    // ✅ nuevo
    String fecha = usuario.getUltimoLogin() != null ? usuario.getUltimoLogin() : "No disponible";
    System.out.println("   Último login : " + fecha);
    InputHelper.pausar();
    }

    public boolean cambiarContrasena(User usuario) {
    Printer.titulo(" CAMBIAR CONTRASEÑA");
    String actual = InputHelper.leerTexto("   Contraseña actual: ");

    if (!usuario.getPassword().equals(actual)) {
        Printer.error("Contraseña incorrecta.");
        InputHelper.pausar();
        return false;
    }

    String nueva    = InputHelper.leerTexto("   Nueva contraseña: ");
    String confirma = InputHelper.leerTexto("   Confirmar contraseña: ");

    if (!nueva.equals(confirma)) {
        Printer.error("Las contraseñas no coinciden.");
        InputHelper.pausar();
        return false;
    }

    if (nueva.equals(actual)) {
        Printer.error("La nueva contraseña debe ser diferente a la actual.");
        InputHelper.pausar();
        return false;
    }

    usuario.setPassword(nueva);
    authService.actualizarYGuardar();
    Printer.exito("Contraseña cambiada exitosamente.");
    InputHelper.pausar();
    return true;
    }

    public boolean eliminarCuenta(User usuario) {
    Printer.titulo(" ELIMINAR CUENTA");
    Printer.aviso("Esta acción es irreversible.");
    String pass = InputHelper.leerTexto("   Ingrese su contraseña para confirmar: ");

    if (!usuario.getPassword().equals(pass)) {
        Printer.error("Contraseña incorrecta. Cuenta no eliminada.");
        InputHelper.pausar();
        return false;
    }

    String confirma = InputHelper.leerTexto("   ¿Está seguro? (si/no): ");
    if (!confirma.equalsIgnoreCase("si")) {
        Printer.aviso("Eliminación cancelada.");
        InputHelper.pausar();
        return false;
    }

    authService.eliminarUsuario(usuario);
    Printer.exito("Cuenta eliminada. Hasta luego.");
    InputHelper.pausar();
    return true;
    }
}
