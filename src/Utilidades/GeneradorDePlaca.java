package Utilidades;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GeneradorDePlaca{

    private static final String LETRAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Random random = new Random();

    private static final Set<String> placasGeneradas = new HashSet<>();

    public static String generarPlaca() {
        String placa;

        do {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < 3; i++) {
                int index = random.nextInt(LETRAS.length());
                sb.append(LETRAS.charAt(index));
            }

            sb.append("-");

            for (int i = 0; i < 3; i++) {
                sb.append(random.nextInt(10));
            }

            placa = sb.toString();
        } while (placasGeneradas.contains(placa)); 

        placasGeneradas.add(placa);

        return placa;
    }

    public static int getTotalPlacasGeneradas() {
        return placasGeneradas.size();
    }
}
