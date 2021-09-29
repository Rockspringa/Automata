package edu.codepad.model.supp.analisis;

/**
 * Clase que tiene por objetivo solo guardar las coordenadas de una cadena de
 * caracteres.
 */
public class Coordenadas {
    private final int start;
    private final int end;

    public Coordenadas(int start, int end) {
        this.start = start;
        this.end = end;
    }

    /**
     * @return un entero que representa el inicio de la cadena de caracteres.
     */
    public int getStart() {
        return this.start;
    }

    /**
     * @return un entero que representa el final de la cadena de caracteres.
     */
    public int getEnd() {
        return this.end;
    }
}
