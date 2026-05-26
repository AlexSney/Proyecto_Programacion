package CatalogoDeJuegosModels;

import java.util.ArrayList;

public class User {

    private String username;
    private String password;
    private double saldo;

    private ArrayList<Juegos> biblioteca;

    public User(String username,
                String password,
                double saldo) {

        this.username = username;
        this.password = password;
        this.saldo = saldo;

        biblioteca = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public double getSaldo() {
        return saldo;
    }

    public ArrayList<Juegos> getBiblioteca() {
        return biblioteca;
    }

    public void descontarSaldo(double cantidad) {
        saldo -= cantidad;
    }
}