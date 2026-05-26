package data;

import java.util.ArrayList;
import CatalogoDeJuegosModels.Juegos;

public class dataJuegos {

    public static ArrayList<Juegos> games =
            new ArrayList<>();

    static {

        games.add(new Juegos(
                "Minecraft",
                20,
                "Sandbox",
                5,
                "E"));

        games.add(new Juegos(
                "GTA V",
                35,
                "Accion",
                3,
                "+18"));

        games.add(new Juegos(
                "FIFA 25",
                60,
                "Deportes",
                10,
                "E"));
    }
}
