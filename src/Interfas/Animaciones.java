package Interfas;

public class Animaciones {

    public void mostrarCarga(int avance) {

        String[] animacion = {
            "Cargando   ",
            "Cargando.  ",
            "Cargando.. ",
            "Cargando..."
        };

        for (int i = 0; i <= avance; i++) {

            System.out.print("\r" + animacion[i % animacion.length]);
            System.out.print(" " + i + "%");

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {

            }
        }

        System.out.println("\nCarga completada.");
    }
}