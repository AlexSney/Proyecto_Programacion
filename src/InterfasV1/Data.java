package InterfasV1;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Data {
    private Map<String, String> usuarios;
    private Map<String, String> ultimoLogin;
    private Scanner scanner;
    private String usuarioLogeado;

    public Data() {
        usuarios = new HashMap<>();
        scanner = new Scanner(System.in);
        usuarioLogeado = null;
        ultimoLogin = new HashMap<>();
        /**
         * Aqui van a crear sus usuarios y contraseñas para que puedan iniciar sesión,
         * pueden agregar los que quieran, pero al menos deben agregar uno para poder
         * probar el sistema de login.
         */
        usuarios.put("Elpapu", "1234");
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

            String ahora = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            ultimoLogin.put(user, ahora);

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
            System.out.println("2. Cambiar Contraseña");
            System.out.println("3. Eliminar Cuenta");
            System.out.println("4. Cerrar Sesión");
            System.out.print("Seleccione: ");

            String opcion = scanner.nextLine();
            switch (opcion) {
                case "1":
                    verPerfil();
                    break;
                case "2":
                    cambiarContrasena();
                    break;
                case "3":
                    //  solo cierra sesión si realmente eliminó la cuenta
                    if (eliminarCuenta()) {
                        cerrarSesion = true;
                    }
                    break;
                case "4":
                    System.out.println("\nCerrando sesión de " + usuarioLogeado + "...");
                    usuarioLogeado = null;
                    cerrarSesion = true;
                    System.out.println("Sesión cerrada correctamente.");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private void verPerfil() {
        System.out.println("\n-- MI PERFIL --");
        System.out.println("Usuario: " + usuarioLogeado);
        String fecha = ultimoLogin.getOrDefault(usuarioLogeado, "No Disponible");
        System.out.println("Último Login: " + fecha);
    }

    private void cambiarContrasena() {
        System.out.println("\n-- CAMBIAR CONTRASEÑA --");
        System.out.print("Ingrese su contraseña actual: ");
        String passActual = scanner.nextLine();

        if (!usuarios.get(usuarioLogeado).equals(passActual)) {
            System.out.println("Error: La contraseña actual es incorrecta.");
            return;
        }

        System.out.print("Ingrese su nueva contraseña: ");
        String passNueva = scanner.nextLine();
        System.out.print("Confirme su nueva contraseña: ");
        String passConfirm = scanner.nextLine();

        //  compara nueva con confirmación, no con la actual
        if (!passNueva.equals(passConfirm)) {
            System.out.println("Error: Las contraseñas no coinciden.");
            return;
        }

        if (passNueva.equals(passActual)) {
            System.out.println("Error: La nueva contraseña debe ser diferente a la actual.");
            return;
        }

        usuarios.put(usuarioLogeado, passNueva);
        System.out.println("\nContraseña cambiada exitosamente.");
    }

    private boolean eliminarCuenta() {
        System.out.println("\n-- ELIMINAR CUENTA --");
        System.out.println("⚠ Esta acción es irreversible.");
        System.out.print("Ingrese su contraseña para confirmar: ");
        String pass = scanner.nextLine();

        if (!usuarios.get(usuarioLogeado).equals(pass)) {
            System.out.println("Error: Contraseña incorrecta. Cuenta no eliminada.");
            return false;
        }

        System.out.print("¿Está seguro que desea eliminar su cuenta? (si/no): ");
        String confirmacion = scanner.nextLine();

        if (confirmacion.equalsIgnoreCase("si")) {
            usuarios.remove(usuarioLogeado);
            ultimoLogin.remove(usuarioLogeado);
            System.out.println("\nCuenta eliminada correctamente. Hasta luego.");
            usuarioLogeado = null;
            return true;
        } else {
            System.out.println("Eliminación de cuenta cancelada");
            return false;
        }
    }
}