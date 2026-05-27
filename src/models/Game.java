package models;

/**
 * Modelo que representa un videojuego dentro del catálogo de NexusGames.
 * Contiene todos los atributos necesarios para describir un producto digital:
 * identificador único, nombre, precio, género, disponibilidad y clasificación de edad.
 */
public class Game {

    // ── Constantes de clasificación válidas ───────────────────────────────────
    public static final String CLASIFICACION_E  = "E";   // Everyone (Todos)
    public static final String CLASIFICACION_T  = "T";   // Teen (Adolescentes)
    public static final String CLASIFICACION_M  = "M";   // Mature (Adultos)

    // ── Atributos ──────────────────────────────────────────────────────────────
    private int    id;
    private String nombre;
    private double precio;
    private String categoria;
    private int    stock;
    private String clasificacion;

    // ── Constructor completo ───────────────────────────────────────────────────

    public Game(int id, String nombre, double precio,
                String categoria, int stock, String clasificacion) {
        this.id            = id;
        this.nombre        = nombre.trim();          // Elimina espacios accidentales
        this.precio        = precio;
        this.categoria     = categoria.trim();
        this.stock         = stock;
        this.clasificacion = clasificacion.toUpperCase().trim(); // Normaliza a mayúsculas
    }

    // ── Getters y Setters ──────────────────────────────────────────────────────

    public int    getId()                        { return id; }
    public void   setId(int id)                  { this.id = id; }

    public String getNombre()                    { return nombre; }
    public void   setNombre(String n)            { this.nombre = n.trim(); }

    public double getPrecio()                    { return precio; }
    public void   setPrecio(double p)            { this.precio = p; }

    public String getCategoria()                 { return categoria; }
    public void   setCategoria(String c)         { this.categoria = c.trim(); }

    public int    getStock()                     { return stock; }
    public void   setStock(int s)                { this.stock = s; }

    public String getClasificacion()             { return clasificacion; }
    public void   setClasificacion(String cl)    { this.clasificacion = cl.toUpperCase().trim(); }

    // ── Métodos de utilidad ────────────────────────────────────────────────────

    /**
     * Verifica si hay unidades disponibles para compra.
     * @return true si stock > 0, false si está agotado.
     */
    public boolean hayStock() {
        return this.stock > 0;
    }

    /**
     * Indica si el juego es apto para menores de edad.
     * Solo las clasificaciones "E" y "T" son aptas para menores.
     * @return true si la clasificación es E o T.
     */
    public boolean esAptoParaMenores() {
        return clasificacion.equals(CLASIFICACION_E) || clasificacion.equals(CLASIFICACION_T);
    }

    /**
     * Calcula el precio con un porcentaje de descuento aplicado.
     * @param porcentaje Descuento entre 0 y 100.
     * @return Precio final después del descuento.
     */
    public double getPrecioConDescuento(double porcentaje) {
        if (porcentaje < 0 || porcentaje > 100) return precio;
        return precio - (precio * porcentaje / 100.0);
    }

    /**
     * Convierte el juego a línea de texto para persistencia en archivo.
     * Formato: id|nombre|precio|categoria|stock|clasificacion
     */
    public String toFileString() {
        return id + "|" + nombre + "|" + precio + "|" +
               categoria + "|" + stock + "|" + clasificacion;
    }

    /**
     * Representación visual del juego formateada para mostrar en consola.
     * Incluye indicador de disponibilidad cuando el stock es bajo o agotado.
     */
    @Override
    public String toString() {
        String estadoStock = stock == 0 ? " [AGOTADO]" : (stock <= 5 ? " [POCAS UNIDADES]" : "");
        return String.format("  [%d] %-30s $%-8.2f  Categoría: %-15s  Stock: %-5d  [%s]%s",
                id, nombre, precio, categoria, stock, clasificacion, estadoStock);
    }
}