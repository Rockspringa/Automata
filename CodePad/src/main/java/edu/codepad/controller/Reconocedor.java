package edu.codepad.controller;

import java.util.ArrayList;

public class Reconocedor {
    
    private ArrayList<String[]> report;
    private String[] fileContent;

    public Reconocedor(String[] cont) {
        this.fileContent = cont;
    }
    
    public String[][] getReport() {
        return report.toArray(new String[0][0]);
    }

    public String[][] countTokens() {       
        // Agregar funcion
        return null;
    }
}
