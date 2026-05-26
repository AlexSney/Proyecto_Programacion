package CatalogoDeJuegosModels;

public class Juegos {

    private String nombre;
    private double precio;
    private String categoria;
    private int stock;
    private String clasificacion;

    public Juegos(String nombre, double precio,
                String categoria,
                int stock,
                String clasificacion) {

        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.stock = stock;
        this.clasificacion = clasificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getStock() {
        return stock;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {

        return nombre +
                " | $" + precio +
                " | " + categoria +
                " | Stock: " + stock +
                " | " + clasificacion;
    }
}