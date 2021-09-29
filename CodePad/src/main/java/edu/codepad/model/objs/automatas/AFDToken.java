package edu.codepad.model.objs.automatas;

import edu.codepad.model.exceptions.InvalidCharacterException;
import edu.codepad.model.objs.Automata;
import edu.codepad.model.supp.analisis.Alfabeto;
import edu.codepad.model.supp.analisis.enums.Token;

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
 * @param tokens       sera la representacion del token que se obtiene del
 *                     estado, si es distinto a <code>VACIO</code> o <code>
 *                     INCOMPLETO</code> sera de aceptacion.
 * @param alfabeto     sera el alfabeto que acepta y usara el automata, el
 *                     alfabeto puede ser conformado caracteres o por cadenas de
 *                     caracteres, estos representando un conjunto dentro del
 *                     conjunto <code>alfabeto</code>.
 */
public class AFDToken implements Automata {
    private final StringBuilder LOG = new StringBuilder();
    private int state = 0;
    private String lexema = "";

    private final Alfabeto alfabeto = new Alfabeto(
            new char[][] { { 'a', 'z', 'A', 'Z' },
                            { '0', '9' },
                            { '.', '.' },
                            { '.', '.', ',', ',', ':', ';' },
                            { '*', '+', '-', '-', '/', '/', '%', '%' },
                            { '(', ')', '[', '[', ']', ']', '{', '{', '}', '}' },
                            { '\r', '\r', '\n', '\n', ' ', ' '  }
                        });

    private final int[][] transiciones = new int[][] {
                            {  1,  2,  3,  3,  4,  5,  0 },
                            {  1,  1, -1, -1, -1, -1,  0 },
                            { -1,  2,  6, -1, -1, -1,  0 },
                            { -1, -1,  3,  3, -1, -1,  0 },
                            { -1, -1, -1, -1,  4, -1,  0 },
                            { -1, -1, -1, -1, -1,  5,  0 },
                            { -1,  7, -1, -1, -1, -1, -1 },
                            { -1,  7, -1, -1, -1, -1,  0 }
                        };

    private final Token[] tokens = new Token[] {
                            Token.VACIO,
                            Token.IDENTIFICADOR,
                            Token.NUMERO,
                            Token.PUNTUACION,
                            Token.OPERADOR,
                            Token.AGRUPACION,
                            Token.INCOMPLETO,
                            Token.DECIMAL
                        };

    private int verifyCharacter(char ch) {
        int col = alfabeto.getIndex(ch);

        if (ch != ' ' && ch != '\n' && ch != '\r') lexema += ch;

        if (col < 0 || col >= alfabeto.getCantConjuntos()) {
            String tmpLexema = lexema;
            String msg = "El caracter '" + ch + "' no pertenece al alfabeto aceptado.";

            LOG.append(msg + "\n");
            state = 0;
            lexema = "";

            throw new InvalidCharacterException(new String[] { msg, tmpLexema, "" });
        }

        return col;
    }

    private int verifyNextState(int col, char ch) {
        int tempState = transiciones[state][col];

        if (tempState < 0) {
            String tmpLexema = lexema;
            String msg = "Se ingreso un caracter invalido cuando se esperaba ";

            msg += switch (tokens[state]) {
                case IDENTIFICADOR  -> "una letra o un digito";
                case NUMERO         -> "un digito";
                case PUNTUACION     -> "un signo de puntuacion";
                case OPERADOR       -> "un operador aritmeticos";
                case AGRUPACION     -> "un signo de agrupacion";
                case INCOMPLETO     -> "un digito";
                case DECIMAL        -> "un digito";
                default -> "";
            };

            if (ch == '\n' || ch == '\r') msg += " o un espacio, no el caracter '\\n'.";
            else msg += " o un espacio, no el caracter '" + ch + "'.";
            LOG.append(msg + "\n");
            state = 0;
            lexema = "";

            throw new InvalidCharacterException(new String[] { msg, tmpLexema, "" });
        }

        return tempState;
    }

    private Object[] isSemiFinalState(Token preToken) {
        Object[] retornar = null;
        if (state == 0) {
            if (preToken != Token.VACIO) {
                if (preToken == Token.PUNTUACION || preToken == Token.OPERADOR || preToken == Token.AGRUPACION) {
                    lexema = "";
                } else {
                    retornar = new Object[] { preToken, lexema, "" };
                }
            }
            lexema = "";
        } else if (state == 3 || state == 4 || state == 5) {
            retornar = new Object[] { tokens[state], lexema, "" };
            lexema = "";
        }
        return retornar;
    }

    @Override
    public Object[] getNextState(char ch) {
        int col = verifyCharacter(ch);

        Token preToken = tokens[state];
        String estado = "Transicion del estado '" + state + "' al estado '";
        
        state = verifyNextState(col, ch);

        LOG.append(estado + state + "' con el caracter '");

        if (ch == '\n' || ch == '\r') LOG.append("\\n");
        else LOG.append(ch);

        LOG.append("'. Token actual: " + tokens[state] + "\n");

        return isSemiFinalState(preToken);
    }

    @Override
    public Object[] getTokenState() {
        if (tokens[state] != Token.VACIO && tokens[state] != Token.PUNTUACION
                && tokens[state] != Token.OPERADOR && tokens[state] != Token.AGRUPACION)
            return new Object[] { tokens[state], lexema, "" };

        else if (tokens[state] == Token.INCOMPLETO)
            throw new InvalidCharacterException(new String[] { "El decimal no se completo.", lexema, "" });

        return null;
    }

    @Override
    public String getLog() {
        return LOG.toString();
    }
}
