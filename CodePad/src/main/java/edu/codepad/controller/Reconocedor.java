package edu.codepad.controller;

import java.util.ArrayList;
import java.util.HashMap;

import edu.codepad.model.exceptions.InvalidCharacterException;
import edu.codepad.model.objs.automatas.AFDToken;
import edu.codepad.model.supp.analisis.enums.Token;

/**
 * Su funcionalidad es la de reconocer tokens del texto de entrada, busca y
 * maneja errores posibles de esta funcion.
 */
public class Reconocedor {

    private ArrayList<Object[]> report = new ArrayList<>();
    private boolean errors;
    private String logContent;
    private String fileContent;
    private String[] columnNames;

    /**
     * @param cont es el contenido del cual se reconoceran los tokens existentes.
     */
    public Reconocedor(String cont) {
        this.fileContent = cont;
    }

    /**
     * Reconoce el texto con el cual se creo el objeto, con este busca todos los
     * Tokens o errores y los guarda en una matriz.
     * 
     * @return una matriz que contiene los errores o de no existir ninguno, los
     *         tokens.
     */
    public Object[][] getReport() {
        char[] content = fileContent.toCharArray();
        AFDToken recog = new AFDToken();
        int length = content.length - 1;

        try {
            int column = 1;
            int start = 1;
            int end = 1;

            for (char ch : content) {
                Object[] reportPart = recog.getNextState(ch);

                if (reportPart != null) {
                    reportPart[2] = "Fi: " + start + ", Ff: " + (end - 1) + ", C: " + column;
                    report.add(reportPart);
                }

                if (ch != ' ' && ch != '\n' && ch != '\r') {
                    end++;
                } else {
                    start = ++end;
                }
                if (ch == '\n') {
                    start = 1;
                    end = 1;
                    column++;
                }
            }
            if (length >= 0 && (content[length] != ' ' && content[length] != '\n' && content[length] != '\r')) {
                Object[] reportPart = recog.getTokenState();

                if (reportPart != null) {
                    reportPart[2] = "Fi: " + start + ", Ff: " + (end - 1) + ", C: " + column;
                    report.add(reportPart);
                }
            }

            this.errors = false;
            this.columnNames = new String[] { "Nombre del Token", "Token", "Posicion" };
        } catch (InvalidCharacterException e) {
            int column = 1;
            int start = 1;
            int end = 1;

            report.clear();

            for (char ch : content) {
                try {
                    recog.getNextState(ch);

                    if (ch == content[length] && length >= 0
                            && (content[length] != ' ' && content[length] != '\n' && content[length] != '\r')) {

                        recog.getTokenState();
                    }
                } catch (InvalidCharacterException ex) {
                    Object[] reportPart = ex.getReporte();
                    reportPart[2] = "Fi: " + start + ", Ff: " + (end - 1) + ", C: " + column;
                    report.add(reportPart);
                }

                if (ch != ' ' && ch != '\n' && ch != '\r') {
                    end++;
                } else {
                    start = ++end;
                }
                if (ch == '\n') {
                    start = 1;
                    end = 1;
                    column++;
                }
            }

            this.errors = true;
            this.columnNames = new String[] { "Causa", "Cadena de error", "Posicion" };
        }

        logContent = recog.getLog();
        return report.toArray(new Object[0][]);
    }

    /**
     * @return un boolean que representa si se encontraron errores en el analisis de
     *         tokens.
     */
    public boolean isErrors() {
        return this.errors;
    }

    /**
     * @return un array de Strings que contiene los nombres de las columnas.
     *         <ul>
     *         <li>Si existen errores devolver el array
     *         <code>{ "Causa", "Cadena de error", "Posicion" }</code></li>
     *         <li>De lo contrario devolvera el array
     *         <code>{ "Nombre del Token", "Token", "Posicion" }</code>.</li>
     *         </ul>
     */
    public String[] getColumnNames() {
        return columnNames;
    }

    /**
     * @return el log de todas las transiciones que se realizaron al analizar el
     *         texto.
     */
    public String getTrasitionLog() {
        return logContent;
    }

    /**
     * El metodo busca todos los lexemas que se guardaron al crear el reporte. Estos
     * los cuenta y los formatea para que sea una matriza que contenga, el lexema,
     * su token y su cantidad de apariciones en el texto.
     * 
     * @return un array con el lexema, su token y su cantidad de apariciones en el
     *         texto.
     */
    public Object[][] countLexemes() {
        ArrayList<Object[]> output = new ArrayList<>();
        HashMap<Object, Object[]> lexemas = new HashMap<>();

        for (Object[] objs : report) {
            Object[] tmp;

            if (lexemas.containsKey(objs[1])) {
                tmp = lexemas.get(objs[1]);
                tmp[1] = (Integer) tmp[1] + 1;
            } else {
                tmp = new Object[2];
                tmp[0] = objs[0];
                tmp[1] = 1;
            }

            lexemas.put(objs[1], tmp);
        }

        for (Object obj : lexemas.keySet()) {
            String lexema = (String) obj;
            Token token = (Token) lexemas.get(obj)[0];
            Integer cant = (Integer) lexemas.get(obj)[1];

            output.add(new Object[] { lexema, token, cant });
        }

        return output.toArray(new Object[0][]);
    }
}
