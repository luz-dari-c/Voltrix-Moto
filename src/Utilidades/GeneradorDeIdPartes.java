package Utilidades;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GeneradorDeIdPartes {

    private static final Random random = new Random();
    private static final Set<String> usados = new HashSet<>();

    public static String generarId(String prefijo) {
        String id;
        do {
            int num = 10000 + random.nextInt(90000);
            id = prefijo + "-" + num;
        } while (usados.contains(id));
        usados.add(id);
        return id;
    }
}
