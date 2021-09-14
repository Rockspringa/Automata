package edu.codepad.model.objs;

import edu.codepad.model.supp.Alfabeto;

/**
 * El objeto de AFDToken se encarga de ser el analizador de todos los tokens.
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
public class AFDToken implements Automata {
    private static final StringBuilder LOG = new StringBuilder();

    private final Alfabeto alfabeto = new Alfabeto(new char[][] { { 'a', 'z', 'A', 'Z' }, { '0', '9' }, { '.', '.' },
            { '.', '.', ',', ',', ':', ';' }, { '*', '+', '-', '-', '/', '/', '%', '%' } });

    private final boolean[] estados = new boolean[] { false, true, true, true, true, true, false, true };

    private final int[][] transiciones = new int[][];

    @Override
    public int getNextState(char ch, int state) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean isAcceptState(int state) {
        // TODO Auto-generated method stub
        return false;
    }
}
