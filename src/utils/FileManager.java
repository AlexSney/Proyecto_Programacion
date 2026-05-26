package utils;

import models.Game;
import models.Purchase;
import models.User;

import java.io.*;
import java.util.ArrayList;

/**
 * Utilidad de persistencia: lee y escribe los archivos TXT del sistema.
 * Usa FileWriter, PrintWriter y BufferedReader tal como piden los requerimientos.
 *
 * CONVENCIÓN DE ARCHIVOS:
 *   users.txt     → username|password|saldo|rol
 *   games.txt     → id|nombre|precio|categoria|stock|clasificacion
 *   purchases.txt → username|gameId|gameName|precioCompra|fecha|favorito
 */
public class FileManager {

    // Rutas de los archivos (relativas al directorio de ejecución)
    private static final String RUTA_USERS     = "data/users.txt";
    private static final String RUTA_GAMES     = "data/games.txt";
    private static final String RUTA_PURCHASES = "data/purchases.txt";

    // ── Inicialización ─────────────────────────────────────────────────────────

    /**
     * Crea la carpeta "data/" y los archivos si no existen.
     * Se llama una sola vez al arrancar la app.
     */
    public static void inicializar() {
        File carpeta = new File("data");
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
        crearSiNoExiste(RUTA_USERS);
        crearSiNoExiste(RUTA_GAMES);
        crearSiNoExiste(RUTA_PURCHASES);
    }

    private static void crearSiNoExiste(String ruta) {
        File f = new File(ruta);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                System.out.println("[ERROR] No se pudo crear el archivo: " + ruta);
            }
        }
    }

    // ── USUARIOS ───────────────────────────────────────────────────────────────

    /** Carga todos los usuarios desde el archivo. */
    public static ArrayList<User> cargarUsuarios() {
        ArrayList<User> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_USERS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;
                String[] partes = linea.split("\\|");
                if (partes.length == 4) {
                    String username = partes[0];
                    String password = partes[1];
                    double saldo    = Double.parseDouble(partes[2]);
                    String rol      = partes[3];
                    lista.add(new User(username, password, saldo, rol));
                }
            }
        } catch (IOException e) {
            System.out.println("[ERROR] Leyendo usuarios: " + e.getMessage());
        }
        return lista;
    }

    /** Guarda TODOS los usuarios (sobreescribe el archivo). */
    public static void guardarUsuarios(ArrayList<User> usuarios) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(RUTA_USERS, false))) {
            for (User u : usuarios) {
                pw.println(u.toFileString());
            }
        } catch (IOException e) {
            System.out.println("[ERROR] Guardando usuarios: " + e.getMessage());
        }
    }

    // ── JUEGOS ─────────────────────────────────────────────────────────────────

    /** Carga todos los juegos desde el archivo. */
    public static ArrayList<Game> cargarJuegos() {
        ArrayList<Game> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_GAMES))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;
                String[] p = linea.split("\\|");
                if (p.length == 6) {
                    int    id            = Integer.parseInt(p[0]);
                    String nombre        = p[1];
                    double precio        = Double.parseDouble(p[2]);
                    String categoria     = p[3];
                    int    stock         = Integer.parseInt(p[4]);
                    String clasificacion = p[5];
                    lista.add(new Game(id, nombre, precio, categoria, stock, clasificacion));
                }
            }
        } catch (IOException e) {
            System.out.println("[ERROR] Leyendo juegos: " + e.getMessage());
        }
        return lista;
    }

    /** Guarda TODOS los juegos (sobreescribe el archivo). */
    public static void guardarJuegos(ArrayList<Game> juegos) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(RUTA_GAMES, false))) {
            for (Game g : juegos) {
                pw.println(g.toFileString());
            }
        } catch (IOException e) {
            System.out.println("[ERROR] Guardando juegos: " + e.getMessage());
        }
    }

    // ── COMPRAS ────────────────────────────────────────────────────────────────

    /** Carga todas las compras desde el archivo. */
    public static ArrayList<Purchase> cargarCompras() {
        ArrayList<Purchase> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_PURCHASES))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;
                String[] p = linea.split("\\|");
                if (p.length == 6) {
                    String  username    = p[0];
                    int     gameId      = Integer.parseInt(p[1]);
                    String  gameName    = p[2];
                    double  precio      = Double.parseDouble(p[3]);
                    String  fecha       = p[4];
                    boolean esFavorito  = Boolean.parseBoolean(p[5]);
                    Purchase compra = new Purchase(username, gameId, gameName, precio, fecha);
                    compra.setEsFavorito(esFavorito);
                    lista.add(compra);
                }
            }
        } catch (IOException e) {
            System.out.println("[ERROR] Leyendo compras: " + e.getMessage());
        }
        return lista;
    }

    /** Guarda TODAS las compras (sobreescribe el archivo). */
    public static void guardarCompras(ArrayList<Purchase> compras) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(RUTA_PURCHASES, false))) {
            for (Purchase c : compras) {
                pw.println(c.toFileString());
            }
        } catch (IOException e) {
            System.out.println("[ERROR] Guardando compras: " + e.getMessage());
        }
    }
}
