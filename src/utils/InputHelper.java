package utils;

import java.util.Scanner;

/**
 * Utilidad para leer datos del teclado de forma segura.
 * Evita crashes por entradas incorrectas (letras donde se espera número, etc.).
 */
public class InputHelper {

    // Un solo Scanner compartido por toda la app
    private static final Scanner sc = new Scanner(System.in);

    /** Lee una línea de texto. Nunca retorna null. */
    public static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    /**
     * Lee un número entero.
     * Si el usuario escribe algo que no es número, muestra un error
     * y vuelve a preguntar (recursividad simple para validación).
     */
    public static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        String entrada = sc.nextLine().trim();
        try {
            return Integer.parseInt(entrada);
        } catch (NumberFormatException e) {
            System.out.println("  ⚠ Entrada inválida. Ingresa un número entero.");
            return leerEntero(mensaje);   // recursividad para reintentar
        }
    }

    /**
     * Lee un número decimal.
     * Recursivo al igual que leerEntero.
     */
    public static double leerDecimal(String mensaje) {
        System.out.print(mensaje);
        String entrada = sc.nextLine().trim();
        try {
            return Double.parseDouble(entrada);
        } catch (NumberFormatException e) {
            System.out.println("  ⚠ Entrada inválida. Ingresa un número (ej: 10.50).");
            return leerDecimal(mensaje);  // recursividad para reintentar
        }
    }

    /** Verifica que un texto no esté vacío. */
    public static boolean estaVacio(String texto) {
        return texto == null || texto.isEmpty();
    }

    /** Pausa la ejecución hasta que el usuario presione ENTER. */
    public static void pausar() {
        System.out.print("\n  Presiona ENTER para continuar...");
        sc.nextLine();
    }
}
