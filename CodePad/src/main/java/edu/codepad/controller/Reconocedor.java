package edu.codepad.controller;

import java.util.ArrayList;

import edu.codepad.model.exceptions.InvalidCharacterException;
import edu.codepad.model.objs.AFDToken;

public class Reconocedor {

    private boolean errors;
    private String logContent;
    private String fileContent;
    private String[] columnNames;

    public Reconocedor(String cont) {
        this.fileContent = cont;
    }

    public Object[][] getReport() {
        ArrayList<Object[]> report = new ArrayList<>();
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
                        
                        Object[] reportPart = recog.getTokenState();
                        
                        if (reportPart != null) {
                            reportPart[2] = "Fi: " + start + ", Ff: " + (end - 1) + ", C: " + column;
                            report.add(reportPart);
                        }
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
                    column++;
                }
            }

            this.errors = true;
            this.columnNames = new String[] { "Causa", "Cadena de error", "Posicion" };
        }

        logContent = recog.getLog();
        return report.toArray(new Object[0][]);
    }

    public boolean isErrors() {
        return this.errors;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public String getTrasitionLog() {
        return logContent;
    }

    public String[][] countTokens() {
        // Agregar funcion
        return null;
    }
}
