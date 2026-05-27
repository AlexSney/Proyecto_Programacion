package models;

/**
 * Modelo que representa a un usuario de la plataforma NexusGames.
 * Contiene los datos básicos de autenticación, saldo y rol.
 */
public class User {

    // ── Atributos ──────────────────────────────────────────────────────────────
    private String username;
    private String password;
    private double saldo;
    private String rol;          // "USER" o "ADMIN"

    // ── Constructores ──────────────────────────────────────────────────────────

    /** Constructor completo (usado al cargar desde archivo) */
    public User(String username, String password, double saldo, String rol) {
        this.username = username;
        this.password = password;
        this.saldo    = saldo;
        this.rol      = rol;
    }

    /** Constructor básico para registro (saldo inicial = 0, rol = USER) */
    public User(String username, String password) {
        this(username, password, 0.0, "USER");
    }

    // ── Getters y Setters ──────────────────────────────────────────────────────

    public String getUsername()             { return username; }
    public void   setUsername(String u)     { this.username = u; }

    public String getPassword()             { return password; }
    public void   setPassword(String p)     { this.password = p; }

    public double getSaldo()                { return saldo; }
    public void   setSaldo(double s)        { this.saldo = s; }

    public String getRol()                  { return rol; }
    public void   setRol(String r)          { this.rol = r; }

    // ── Métodos de utilidad ────────────────────────────────────────────────────

    public boolean esAdmin() {
        return "ADMIN".equalsIgnoreCase(this.rol);
    }

    /**
     * Convierte el usuario a una línea de texto para guardarlo en el archivo.
     * Formato: username|password|saldo|rol
     */
    public String toFileString() {
        return username + "|" + password + "|" + saldo + "|" + rol;
    }

    @Override
    public String toString() {
        return "[" + rol + "] " + username + " — Saldo: $" + String.format("%.2f", saldo);
    }

    private String ultimoLogin;  // ✅ nuevo atributo

    // en los getters/setters agrega:
    public String getUltimoLogin()          { return ultimoLogin; }
    public void   setUltimoLogin(String f)  { this.ultimoLogin = f; }
}
