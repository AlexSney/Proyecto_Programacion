package utils;

public class AnimationHelper {

    /**
     * @parami metodo barraCarga
     * @parami descripcion muestra una barra de progreso animada
     */
    public static void barraCarga(String mensaje) {
        System.out.print("\n   " + mensaje + " ");

        for (int i = 0; i <= 20; i++) {
            System.out.print("█");
            pausar(80);
        }

        System.out.println(" ✔");
    }

    /**
     * @parami metodo puntosCarga
     * @parami descripcion animacion con puntos tipo cargando...
     */
    public static void puntosCarga(String mensaje) {
        System.out.print("\n   " + mensaje);

        for (int i = 0; i < 3; i++) {
            pausar(400);
            System.out.print(".");
        }

        System.out.println();
    }

    /**
     * @parami metodo pausar
     * @parami descripcion pausa el hilo para dar efecto de animacion
     */
    private static void pausar(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}