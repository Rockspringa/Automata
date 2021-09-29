package edu.codepad.model.objs.automatas;

import java.util.ArrayList;

import edu.codepad.model.exceptions.InvalidCharacterException;
import edu.codepad.model.objs.Automata;
import edu.codepad.model.supp.analisis.Alfabeto;
import edu.codepad.model.supp.analisis.enums.GeneralToken;

/**
 * El objeto <code>Buscador</code> tiene por objetivo analizar una secuencia de
 * caracteres para ver si coinciden con la palabra con la que se creo el
 * automata.
 */
public class Buscador implements Automata {

    private final Alfabeto alfabeto;
    private final int[][] transiciones;
    private final GeneralToken[] tokens;

    private int state = 0;

    /**
     * Crea un automata que toma como su alfabeto cada caracter de la cadena de
     * texto ingresada.
     */
    public Buscador(String buscar) {
        ArrayList<char[]> alf = new ArrayList<>();

        for (char ch : buscar.toCharArray()) {
            alf.add(new char[] { ch, ch });
        }

        this.alfabeto = new Alfabeto(alf.toArray(new char[0][]));
        int cantEstados = this.alfabeto.getCantConjuntos();

        this.transiciones = new int[cantEstados + 1][cantEstados];
        this.tokens = new GeneralToken[cantEstados + 1];

        for (int i = 0; i < cantEstados + 1; i++) {
            for (int j = 0; j < cantEstados; j++) {
                int val;

                if (i == j) {
                    val = i + 1;
                } else
                    val = 0;

                this.transiciones[i][j] = val;
            }

            GeneralToken token;

            if (i == 0)
                token = GeneralToken.VACIO;
            else if (i < cantEstados)
                token = GeneralToken.INCOMPLETO;
            else
                token = GeneralToken.COMPLETO;

            this.tokens[i] = token;
        }
    }

    private int verifyCharacter(char ch) {
        int col = alfabeto.getIndex(ch);

        if (col < 0 || col >= alfabeto.getCantConjuntos()) {
            state = 0;
            throw new InvalidCharacterException();
        }

        return col;
    }

    private int verifyNextState(int col, char ch) {
        int tempState = transiciones[state][col];

        if (tempState < 0) {
            state = 0;
            throw new InvalidCharacterException();
        }

        return tempState;
    }

    @Override
    public Object[] getNextState(char ch) {
        int col = verifyCharacter(ch);
        this.state = verifyNextState(col, ch);

        return new Object[] { tokens[this.state] };
    }

    @Override
    public Object[] getTokenState() {
        return new Object[] { tokens[this.state] };
    }

    @Deprecated
    public String getLog() {
        return null;
    }
}
