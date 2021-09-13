package edu.codepad.model.objs;

import edu.codepad.model.supp.Alfabeto;

public enum Token {
    IDENTIFICADOR(new int[][] { { -1, 1 }, { 1, 1 } }, new boolean[] { false, true },
            new char[][] { { 'a', 'z', 'A', 'Z' }, { '0', '9' } }),

    NUMERO(new int[][] { { 1 }, { 1 } }, new boolean[] { false, true }, new char[][] { { '0', '9' } }),

    DECIMAL(new int[][] { { 1, -1 }, { 1, 2 }, { 3, -1 }, { 3, -1 } }, new boolean[] { false, false, false, true },
            new char[][] { { '0', '9' }, { '.', '.' } }),

    PUNTUACION(new int[][] { { 1 }, { -1 } }, new boolean[] { false, true },
            new char[][] { { '.', '.', ',', ',', ':', ';' } }),

    OPERADOR(new int[][] { { 1 }, { 1 } }, new boolean[] { false, true }, new char[][] { { '0', '9' } }),

    AGRUPACION(new int[][] { { 1 }, { 1 } }, new boolean[] { false, true }, new char[][] { { '0', '9' } });

    private static final StringBuilder LOG = new StringBuilder();
    private final Alfabeto alfabeto;
    private final boolean[] estados;
    private final int[][] transiciones;

    /**
     * El constructor se encargara de definir formalmente un automata.
     * 
     * @param transiciones son en escencia la matriz de transisciones o las
     *                     funciones de transisicon, donde las columnas de esta
     *                     seran los grupos de caracteres que representan el
     *                     alfabeto, y las filas representan los distintos estados
     *                     entre los cuales se movera el automata, los enteros que
     *                     se declararan seran el estado al cual se trasladaran, o
     *                     sea que seria el numero de fila al cual se trasladara.
     * @param estados      sera la representacion de si un estado es de aceptacion o
     *                     no, si el estado es <code>true</code>, el estado sera de
     *                     aceptacion.
     * @param alfabeto     sera el alfabeto que acepta y usara el automata, el
     *                     alfabeto puede ser conformado caracteres o por cadenas de
     *                     caracteres, estos representando un conjunto dentro del
     *                     conjunto <code>alfabeto</code>.
     */
    private Token(int[][] transiciones, boolean[] estados, char[][] alfabeto) {
        this.transiciones = transiciones;
        this.alfabeto = new Alfabeto(alfabeto);
        this.estados = estados;
    }

}
