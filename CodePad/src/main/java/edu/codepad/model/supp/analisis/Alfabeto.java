package edu.codepad.model.supp.analisis;

/**
 * El <code>Alfabeto</code> representa un objeto que almacena los rangos entro
 * los que estan los caracteres que son parte del alfabeto de un automata.
 * En otras palabras un alfabeto es un conjunto de alfabetos, pero que son parte
 * de un unico alfabeto de un automata.
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

    /**
     * Obtiene el indice del caracter buscado, segun la matriz de caracteres el
     * indice regresado seria la columna de esta matriz.
     * 
     * @param ch es el caracter que se busca dentro del alfabeto.
     * @return un entero que simboliza la columna de la matriz del alfabeto. Si
     *         regresa un <code>1</code> entonces simboliza que el caracter no
     *         existe en el alfabeto.
     */
    public int getIndex(char ch) {
        for (int i = 0; i < this.rangos.length; i++)
            for (int j = 0; j < this.rangos[i].length / 2; j++)
                if (ch >= this.rangos[i][j * 2] && ch <= this.rangos[i][j * 2 + 1])
                    return i;
        return -1;
    }

    /**
     * @return Regresa la cantidad de columnas que hay en la matriz.
     */
    public int getCantConjuntos() {
        return rangos.length;
    }
}
