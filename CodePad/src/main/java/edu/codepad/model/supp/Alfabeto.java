package edu.codepad.model.supp;

/**
 * El <code>Alfabeto</code> representa un objeto que almacena los rangos entro
 * los que estan los caracteres que son parte del alfabeto de un automata.
 */
public class Alfabeto {
    private final char[][] rangos;

    /**
     * @param elems es un array de arrays porque almacenara un array que sera los
     *              elementos del conjunto alfabeto, y cada array interno simboliza
     *              un rango, incluyendo ambos extremos del rango, por lo cual, el
     *              array debe ser de numero par de elementos, si solo se desea un
     *              solo caracter se debera de colocar el mismo caracter dos veces.
     */
    public Alfabeto(char[][] elems) {
        this.rangos = elems;
    }

    public int getIndex(char ch) {
        for (int i = 0; i < this.rangos.length; i++) {
            for (int j = 0; j < this.rangos[i].length / 2; j += 2)
                if (ch >= this.rangos[i][j] && ch <= this.rangos[i][j + 1])
                    return i;
        }
        return -1;
    }
}
