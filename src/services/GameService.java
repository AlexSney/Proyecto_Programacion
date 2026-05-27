package services;

import java.util.ArrayList;
import models.Game;
import utils.FileManager;

/**
 * Servicio del catálogo de videojuegos de NexusGames.
 * Centraliza toda la lógica de negocio: agregar, modificar,
 * eliminar, buscar y filtrar juegos del catálogo.
 */
public class GameService {

    private ArrayList<Game> juegos;
    private int             nextId;

    // ── Constructor ────────────────────────────────────────────────────────────

    public GameService() {
        this.juegos = FileManager.cargarJuegos();
        this.nextId = calcularNextId();
        if (juegos.isEmpty()) {
            cargarJuegosDeEjemplo();
        }
    }

    // ── Calcular siguiente ID ──────────────────────────────────────────────────

    private int calcularNextId() {
        int maxId = 0;
        for (Game g : juegos) {
            if (g.getId() > maxId) maxId = g.getId();
        }
        return maxId + 1;
    }

    // ── Datos de ejemplo ───────────────────────────────────────────────────────

    private void cargarJuegosDeEjemplo() {
        juegos.add(new Game(nextId++, "Elden Ring",          59.99, "RPG",      15,   "M"));
        juegos.add(new Game(nextId++, "Minecraft",           26.99, "Sandbox",  50,   "E"));
        juegos.add(new Game(nextId++, "God of War Ragnarok", 69.99, "Accion",   10,   "M"));
        juegos.add(new Game(nextId++, "FIFA 25",             49.99, "Deportes", 30,   "E"));
        juegos.add(new Game(nextId++, "Hollow Knight",       14.99, "Indie",    99,   "E"));
        juegos.add(new Game(nextId++, "Cyberpunk 2077",      39.99, "RPG",      20,   "M"));
        juegos.add(new Game(nextId++, "Among Us",             4.99, "Social",   99,   "E"));
        juegos.add(new Game(nextId++, "The Witcher 3",        29.99, "RPG",     20,   "M"));
        juegos.add(new Game(nextId++, "Celeste",             19.99, "Indie",    45,   "E"));
        juegos.add(new Game(nextId++, "Stardew Valley",      14.99, "Simulacion", 80, "E"));
        FileManager.guardarJuegos(juegos);
    }

    // ── Obtener catálogo ───────────────────────────────────────────────────────

    public ArrayList<Game> getTodosLosJuegos() {
        return juegos;
    }

    /**
     * Devuelve solo los juegos que tienen stock disponible.
     */
    public ArrayList<Game> getJuegosDisponibles() {
        ArrayList<Game> disponibles = new ArrayList<>();
        for (Game g : juegos) {
            if (g.hayStock()) disponibles.add(g);
        }
        return disponibles;
    }

    // ── Buscar por ID ──────────────────────────────────────────────────────────

    public Game buscarPorId(int id) {
        for (Game g : juegos) {
            if (g.getId() == id) return g;
        }
        return null;
    }

    // ── Buscar por nombre (búsqueda parcial) ───────────────────────────────────

    public ArrayList<Game> buscarPorNombre(String palabra) {
        ArrayList<Game> resultado = new ArrayList<>();
        String busqueda = palabra.toLowerCase().trim();
        if (busqueda.isEmpty()) return resultado;
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

    /**
     * Devuelve todas las categorías únicas presentes en el catálogo.
     * Útil para mostrar un menú dinámico de filtros al usuario.
     */
    public ArrayList<String> getCategorias() {
        ArrayList<String> categorias = new ArrayList<>();
        for (Game g : juegos) {
            if (!categorias.contains(g.getCategoria())) {
                categorias.add(g.getCategoria());
            }
        }
        return categorias;
    }

    // ── Filtrar por clasificación de edad ──────────────────────────────────────

    /**
     * Filtra juegos por su clasificación de edad (E, T, M).
     */
    public ArrayList<Game> filtrarPorClasificacion(String clasificacion) {
        ArrayList<Game> resultado = new ArrayList<>();
        for (Game g : juegos) {
            if (g.getClasificacion().equalsIgnoreCase(clasificacion)) {
                resultado.add(g);
            }
        }
        return resultado;
    }

    // ── Agregar juego (Admin) ──────────────────────────────────────────────────

    public String agregarJuego(String nombre, double precio, String categoria,
                                int stock, String clasificacion) {
        if (nombre.trim().isEmpty() || categoria.trim().isEmpty() || clasificacion.trim().isEmpty()) {
            return "ERROR:Todos los campos son obligatorios.";
        }
        if (precio <= 0)  return "ERROR:El precio debe ser mayor a cero.";
        if (stock  < 0)   return "ERROR:El stock no puede ser negativo.";

        // Verificar si ya existe un juego con el mismo nombre
        if (!buscarPorNombre(nombre.trim()).isEmpty()) {
            return "ERROR:Ya existe un juego con un nombre similar ('" + nombre + "').";
        }

        Game nuevo = new Game(nextId++, nombre, precio, categoria, stock, clasificacion);
        juegos.add(nuevo);
        FileManager.guardarJuegos(juegos);
        return "OK:Juego '" + nuevo.getNombre() + "' agregado con ID " + nuevo.getId() + ".";
    }

    // ── Modificar juego (Admin) ────────────────────────────────────────────────

    public String modificarPrecio(int id, double nuevoPrecio) {
        Game g = buscarPorId(id);
        if (g == null)       return "ERROR:No existe un juego con ID " + id + ".";
        if (nuevoPrecio <= 0) return "ERROR:El precio debe ser mayor a cero.";
        double precioAnterior = g.getPrecio();
        g.setPrecio(nuevoPrecio);
        FileManager.guardarJuegos(juegos);
        return "OK:Precio de '" + g.getNombre() + "' actualizado de $"
                + String.format("%.2f", precioAnterior) + " a $"
                + String.format("%.2f", nuevoPrecio) + ".";
    }

    public String modificarStock(int id, int nuevoStock) {
        Game g = buscarPorId(id);
        if (g == null)    return "ERROR:No existe un juego con ID " + id + ".";
        if (nuevoStock < 0) return "ERROR:El stock no puede ser negativo.";
        g.setStock(nuevoStock);
        FileManager.guardarJuegos(juegos);
        return "OK:Stock de '" + g.getNombre() + "' actualizado a " + nuevoStock + " unidades.";
    }

    // ── Eliminar juego (Admin) ─────────────────────────────────────────────────

    public String eliminarJuego(int id) {
        Game g = buscarPorId(id);
        if (g == null) return "ERROR:No existe un juego con ID " + id + ".";
        juegos.remove(g);
        FileManager.guardarJuegos(juegos);
        return "OK:Juego '" + g.getNombre() + "' (ID " + id + ") eliminado del catálogo.";
    }

    // ── Reducir stock al comprar ───────────────────────────────────────────────

    public void reducirStock(int id) {
        Game g = buscarPorId(id);
        if (g != null && g.getStock() > 0) {
            g.setStock(g.getStock() - 1);
            FileManager.guardarJuegos(juegos);
        }
    }

    // ── Estadísticas del catálogo ──────────────────────────────────────────────

    /**
     * Devuelve el juego con el precio más alto del catálogo.
     */
    public Game getJuegoMasCaro() {
        if (juegos.isEmpty()) return null;
        Game masCaro = juegos.get(0);
        for (Game g : juegos) {
            if (g.getPrecio() > masCaro.getPrecio()) masCaro = g;
        }
        return masCaro;
    }

    /**
     * Cuenta cuántos juegos están agotados (stock == 0).
     */
    public int contarJuegosAgotados() {
        int count = 0;
        for (Game g : juegos) {
            if (!g.hayStock()) count++;
        }
        return count;
    }
}
