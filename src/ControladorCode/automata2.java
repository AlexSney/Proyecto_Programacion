

package ControladorCode;
 
import java.util.Scanner;
 
public class automata2 {
 
    // Alfabeto en orden — sin espacios, sin comas con espacios
    // índice: 0=5, 1=10, 2=25, 3=50, 4=100, 5=enter, 6=space
    final String alfabeto = "5,10,25,50,100,enter,space";
 
    final int er   = -10;
    final int ch   = 2; // chicle  —  10¢
    final int pa   = 5; // pan     —  25¢
    final int gas  = 12; // gaseosa —  60¢
    final int papa = 16; // papas   —  80¢
    final int cola = 20; // cola    — 100¢
 
    //          5c    10c   25c   50c  100c  enter  space
    final int mt[][] = {
        {  1,    2,    5,   10,   20,   er,   er  }, // q0  —   0¢
        {  2,    3,    6,   11,   er,   er,   er  }, // q1  —   5¢
        {  3,    4,    7,   12,   er,   ch,   ch  }, // q2  —  10¢  *chicle
        {  4,    5,    8,   13,   er,   er,   er  }, // q3  —  15¢
        {  5,    6,    9,   14,   er,   er,   er  }, // q4  —  20¢
        {  6,    7,   10,   15,   er,   pa,   pa  }, // q5  —  25¢  *pan
        {  7,    8,   11,   16,   er,   er,   er  }, // q6  —  30¢
        {  8,    9,   12,   17,   er,   er,   er  }, // q7  —  35¢
        {  9,   10,   13,   18,   er,   er,   er  }, // q8  —  40¢
        { 10,   11,   14,   19,   er,   er,   er  }, // q9  —  45¢
        { 11,   12,   15,   20,   er,   er,   er  }, // q10 —  50¢
        { 12,   13,   16,   er,   er,   er,   er  }, // q11 —  55¢
        { 13,   14,   17,   er,   er,  gas,  gas  }, // q12 —  60¢  *gaseosa
        { 14,   15,   18,   er,   er,   er,   er  }, // q13 —  65¢
        { 15,   16,   19,   er,   er,   er,   er  }, // q14 —  70¢
        { 16,   17,   20,   er,   er,   er,   er  }, // q15 —  75¢
        { 17,   18,   er,   er,   er, papa, papa  }, // q16 —  80¢  *papas
        { 18,   19,   er,   er,   er,   er,   er  }, // q17 —  85¢
        { 19,   20,   er,   er,   er,   er,   er  }, // q18 —  90¢
        { 20,   er,   er,   er,   er,   er,   er  }, // q19 —  95¢
        {  er,  er,   er,   er,   er, cola, cola  }, // q20 — 100¢  *cola
    };
 
    // Estructura idéntica a la del profe: Scanner sobre el String del alfabeto
    @SuppressWarnings("resource")
    private int getIndexAlfabeto(String moneda) {
 
        Scanner scAlfa = new Scanner(alfabeto).useDelimiter(",");
 
        for (int indexAlfa = 0; scAlfa.hasNext(); indexAlfa++) {
            if (moneda.equals(scAlfa.next())) {
                return indexAlfa;
            }
        }
 
        return er;
    }
 
    // Estructura idéntica a la del profe: while → indexAlfa → if break → q = mt
    // Switch al final sobre q para imprimir el producto
    @SuppressWarnings("resource")
    public void validarPalabra(Scanner dinero) {
 
        int indexAlfa;
        int q = 0;
 
        while (dinero.hasNext()) {
 
            String simbolo = dinero.next();
 
            // "enter" se intercepta antes de tocar la tabla,
            // igual que en el código del profe pero explícito
            if (simbolo.equals("enter") || simbolo.equals("space")) {
 
                switch (q) {
                    case ch:
                        System.out.println("chicle");
                        break;
                    case pa:
                        System.out.println("pan");
                        break;
                    case gas:
                        System.out.println("gaseosa");
                        break;
                    case papa:
                        System.out.println("papas");
                        break;
                    case cola:
                        System.out.println("cola");
                        break;
                    default:
                        System.out.println("Devolvemos su dinero");
                        break;
                }
 
                q = 0;
                continue;
            }
 
            indexAlfa = getIndexAlfabeto(simbolo);
 
            if (indexAlfa == er || mt[q][indexAlfa] == er) {
                System.out.println("Entrada invalida");
                q = 0;
                continue;
            }
 
            q = mt[q][indexAlfa];
        }
    }
}