package utils;

/**
 * Utilidad de presentación visual.
 * Centraliza todos los banners, líneas separadoras y mensajes de interfaz.
 */
public class Printer {

    // ── Constantes de diseño ───────────────────────────────────────────────────
    private static final String LINEA  = "  ══════════════════════════════════════════════════";
    private static final String LINEA2 = "  ──────────────────────────────────────────────────";

    // ── Banner principal ───────────────────────────────────────────────────────

    public static void bannerPrincipal() {
        limpiarConsola();
        System.out.println();
        System.out.println(LINEA);
       
        System.out.println("              G A M E S  — Plataforma Digital");
        System.out.println(LINEA);
        System.out.println();
    }

    // ── Títulos de sección ─────────────────────────────────────────────────────

    public static void titulo(String texto) {
        System.out.println();
        System.out.println(LINEA);
        System.out.println("   " + texto);
        System.out.println(LINEA);
    }

    public static void subtitulo(String texto) {
        System.out.println(LINEA2);
        System.out.println("   " + texto);
        System.out.println(LINEA2);
    }

    // ── Mensajes de estado ─────────────────────────────────────────────────────

    public static void exito(String msg)  { System.out.println("  ✔ " + msg); }
    public static void error(String msg)  { System.out.println("  ✘ " + msg); }
    public static void info(String msg)   { System.out.println("  ℹ " + msg); }
    public static void aviso(String msg)  { System.out.println("  ⚠ " + msg); }

    // ── Líneas ─────────────────────────────────────────────────────────────────

    public static void linea()  { System.out.println(LINEA); }
    public static void linea2() { System.out.println(LINEA2); }

    // ── Limpiar consola (multiplataforma) ──────────────────────────────────────

    public static void limpiarConsola() {
        // Funciona en terminales ANSI (Linux/Mac/Windows Terminal)
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
