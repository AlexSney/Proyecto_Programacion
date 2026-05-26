package services;

import models.User;
import utils.FileManager;

import java.util.ArrayList;

/**
 * Servicio de autenticación.
 * Contiene TODA la lógica de negocio relacionada con usuarios:
 * registro, login, búsqueda y validaciones.
 *
 * Los controladores llaman a este servicio; nunca acceden directamente
 * al ArrayList de usuarios ni al FileManager.
 */
public class AuthService {

    // Lista en memoria — se carga desde el archivo al iniciar
    private ArrayList<User> usuarios;

    // ── Constructor ────────────────────────────────────────────────────────────

    public AuthService() {
        // Al crear el servicio, cargamos los datos desde el TXT
        this.usuarios = FileManager.cargarUsuarios();

        // Si no hay ningún usuario, creamos el admin por defecto
        if (usuarios.isEmpty()) {
            User admin = new User("admin", "admin123", 9999.0, "ADMIN");
            usuarios.add(admin);
            FileManager.guardarUsuarios(usuarios);
        }
    }

    // ── Registro ───────────────────────────────────────────────────────────────

    /**
     * Registra un nuevo usuario.
     *
     * @return mensaje de resultado (éxito o descripción del error)
     */
    public String registrar(String username, String password) {
        // Validación 1: campos vacíos
        if (username.isEmpty() || password.isEmpty()) {
            return "ERROR:Los campos no pueden estar vacíos.";
        }
        // Validación 2: longitud mínima de contraseña
        if (password.length() < 4) {
            return "ERROR:La contraseña debe tener al menos 4 caracteres.";
        }
        // Validación 3: usuario duplicado (usamos método de búsqueda)
        if (buscarPorUsername(username) != null) {
            return "ERROR:El usuario '" + username + "' ya existe.";
        }

        // Todo correcto: creamos y guardamos
        User nuevo = new User(username, password);
        usuarios.add(nuevo);
        FileManager.guardarUsuarios(usuarios);
        return "OK:Usuario registrado exitosamente.";
    }

    // ── Login ──────────────────────────────────────────────────────────────────

    /**
     * Intenta hacer login.
     *
     * @return el objeto User si las credenciales son correctas, null si no.
     */
    public User login(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) return null;

        User u = buscarPorUsername(username);
        if (u != null && u.getPassword().equals(password)) {
            return u;
        }
        return null;
    }

    // ── Búsqueda ───────────────────────────────────────────────────────────────

    /**
     * Recorre la lista buscando por username (sin distinción mayús/minús).
     * Ejemplo de recorrido simple con for-each.
     */
    public User buscarPorUsername(String username) {
        for (User u : usuarios) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return u;
            }
        }
        return null;
    }

    // ── Getters para el admin ──────────────────────────────────────────────────

    public ArrayList<User> getTodosLosUsuarios() {
        return usuarios;
    }

    /** Actualiza el saldo de un usuario y guarda en el archivo. */
    public void actualizarYGuardar() {
        FileManager.guardarUsuarios(usuarios);
    }
}
