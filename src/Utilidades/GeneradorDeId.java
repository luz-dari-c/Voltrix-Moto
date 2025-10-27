package Utilidades;

public class GeneradorDeId {

    private static int contador = 0;

    public static int generarId() {
        return ++contador;
    }

}
