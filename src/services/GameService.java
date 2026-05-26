package services;

import models.Game;
import utils.FileManager;

import java.util.ArrayList;

/**
 * Servicio del catálogo de videojuegos.
 * Permite agregar, modificar, eliminar y buscar juegos.
 */
public class GameService {

    private ArrayList<Game> juegos;
    private int             nextId;   // Contador para asignar IDs únicos

    // ── Constructor ────────────────────────────────────────────────────────────

    public GameService() {
        this.juegos = FileManager.cargarJuegos();
        this.nextId = calcularNextId();

        // Si el catálogo está vacío, cargamos juegos de ejemplo
        if (juegos.isEmpty()) {
            cargarJuegosDeEjemplo();
        }
    }

    // ── Calcular siguiente ID ──────────────────────────────────────────────────

    /**
     * Recorre la lista para encontrar el ID más alto y le suma 1.
     * Así nunca habrá IDs repetidos aunque se borren juegos.
     */
    private int calcularNextId() {
        int maxId = 0;
        for (Game g : juegos) {
            if (g.getId() > maxId) {
                maxId = g.getId();
            }
        }
        return maxId + 1;
    }

    // ── Datos de ejemplo ───────────────────────────────────────────────────────

    private void cargarJuegosDeEjemplo() {
        juegos.add(new Game(nextId++, "Elden Ring",          59.99, "RPG",      15, "M"));
        juegos.add(new Game(nextId++, "Minecraft",           26.99, "Sandbox",  50, "E"));
        juegos.add(new Game(nextId++, "God of War Ragnarok", 69.99, "Accion",   10, "M"));
        juegos.add(new Game(nextId++, "FIFA 25",             49.99, "Deportes", 30, "E"));
        juegos.add(new Game(nextId++, "Hollow Knight",       14.99, "Indie",    99, "E"));
        juegos.add(new Game(nextId++, "Cyberpunk 2077",      39.99, "RPG",      2077, "M"));
        juegos.add(new Game(nextId++, "Among Us",             4.99, "Social",   99, "E"));
        juegos.add(new Game(nextId++, "The Witcher 3",        29.99, "RPG",     20, "M"));
        FileManager.guardarJuegos(juegos);
    }

    // ── Obtener catálogo ───────────────────────────────────────────────────────

    public ArrayList<Game> getTodosLosJuegos() {
        return juegos;
    }

    // ── Buscar por ID ──────────────────────────────────────────────────────────

    public Game buscarPorId(int id) {
        for (Game g : juegos) {
            if (g.getId() == id) return g;
        }
        return null;
    }

    // ── Buscar por nombre (búsqueda parcial) ───────────────────────────────────

    /**
     * Devuelve todos los juegos cuyo nombre contiene la palabra buscada.
     * No distingue mayúsculas/minúsculas.
     */
    public ArrayList<Game> buscarPorNombre(String palabra) {
        ArrayList<Game> resultado = new ArrayList<>();
        String busqueda = palabra.toLowerCase();
        for (Game g : juegos) {
            if (g.getNombre().toLowerCase().contains(busqueda)) {
                resultado.add(g);
            }
        }
        return resultado;
    }

    // ── Filtrar por categoría ──────────────────────────────────────────────────

    public ArrayList<Game> filtrarPorCategoria(String categoria) {
        ArrayList<Game> resultado = new ArrayList<>();
        for (Game g : juegos) {
            if (g.getCategoria().equalsIgnoreCase(categoria)) {
                resultado.add(g);
            }
        }
        return resultado;
    }

    // ── Agregar juego (Admin) ──────────────────────────────────────────────────

    public String agregarJuego(String nombre, double precio, String categoria,
                                int stock, String clasificacion) {
        if (nombre.isEmpty() || categoria.isEmpty() || clasificacion.isEmpty()) {
            return "ERROR:Todos los campos son obligatorios.";
        }
        if (precio <= 0)  return "ERROR:El precio debe ser mayor a cero.";
        if (stock  < 0)   return "ERROR:El stock no puede ser negativo.";

        Game nuevo = new Game(nextId++, nombre, precio, categoria, stock, clasificacion);
        juegos.add(nuevo);
        FileManager.guardarJuegos(juegos);
        return "OK:Juego '" + nombre + "' agregado con ID " + nuevo.getId() + ".";
    }

    // ── Modificar juego (Admin) ────────────────────────────────────────────────

    public String modificarPrecio(int id, double nuevoPrecio) {
        Game g = buscarPorId(id);
        if (g == null) return "ERROR:No existe un juego con ID " + id + ".";
        if (nuevoPrecio <= 0) return "ERROR:El precio debe ser mayor a cero.";
        g.setPrecio(nuevoPrecio);
        FileManager.guardarJuegos(juegos);
        return "OK:Precio actualizado.";
    }

    public String modificarStock(int id, int nuevoStock) {
        Game g = buscarPorId(id);
        if (g == null) return "ERROR:No existe un juego con ID " + id + ".";
        if (nuevoStock < 0) return "ERROR:El stock no puede ser negativo.";
        g.setStock(nuevoStock);
        FileManager.guardarJuegos(juegos);
        return "OK:Stock actualizado.";
    }

    // ── Eliminar juego (Admin) ─────────────────────────────────────────────────

    public String eliminarJuego(int id) {
        Game g = buscarPorId(id);
        if (g == null) return "ERROR:No existe un juego con ID " + id + ".";
        juegos.remove(g);
        FileManager.guardarJuegos(juegos);
        return "OK:Juego '" + g.getNombre() + "' eliminado.";
    }

    // ── Reducir stock al comprar ───────────────────────────────────────────────

    public void reducirStock(int id) {
        Game g = buscarPorId(id);
        if (g != null && g.getStock() > 0) {
            g.setStock(g.getStock() - 1);
            FileManager.guardarJuegos(juegos);
        }
    }
}
