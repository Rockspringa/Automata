package edu.codepad.model.exceptions;

public class InvalidCharacterException extends RuntimeException {

    private final String[] report;
    
    public InvalidCharacterException(String[] reporte) {
        this.report = reporte;
    }

    public String[] getReporte() {
        return this.report;
    }
}
