package saldo;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
//prueba
public class Saldo {
    public void modificarSaldo(double saldo, boolean modificar) {
         try {

        String ruta = "C:\\jhajho\\progra\\Proyecto_Programacion\\src\\datos.txt";

        List<String> lineas = Files.readAllLines(Paths.get(ruta));

        for (int i = 0; i < lineas.size(); i++) {

            if (lineas.get(i).startsWith("saldo")) {

                // Obtener el saldo actual
                String textoSaldo = lineas.get(i).split("=")[1].trim();

                // Cambiar coma por punto para convertir a double
                textoSaldo = textoSaldo.replace(',', '.');

                double saldoActual = Double.parseDouble(textoSaldo);

                // Sumar o restar
                if (modificar) {
                    saldoActual += saldo;
                } else {
                    saldoActual -= saldo;
                }

                // Formatear nuevamente
                String saldoFormateado =
                        String.format("saldo = %.2f", saldoActual)
                        .replace('.', ',');

                // Reemplazar línea
                lineas.set(i, saldoFormateado);
            }
        }

        Files.write(Paths.get(ruta), lineas);

        System.out.println("Saldo actualizado.");

    } catch (IOException e) {

        System.out.println("Error al modificar el archivo.");

    } catch (NumberFormatException e) {

        System.out.println("El saldo del archivo no tiene un formato válido.");
    }
    }

    public void mostrarSaldo() {
        try {

            List<String> lineas = Files.readAllLines(Paths.get("C:\\jhajho\\progra\\Proyecto_Programacion\\src\\datos.txt"));

            for (String linea : lineas) {

                if (linea.startsWith("saldo")) {

                    System.out.println("Saldo actual: " + linea.split("=")[1].trim());
                }
            }

        } catch (IOException e) {

            System.out.println("Error al leer el archivo.");
        }
    }

    public int ingresarInt() {
        Scanner sc = new Scanner(System.in);
        int x = sc.nextInt();
        return x;
    }
}
