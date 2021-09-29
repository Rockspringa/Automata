package edu.codepad.model.exceptions;

public class InvalidCharacterException extends RuntimeException {

    private final Object[] report;

    public InvalidCharacterException() {
        report = null;
    }
    
    public InvalidCharacterException(String[] reporte) {
        Object[] objs = new Object[reporte.length];

        for (int i = 0; i < reporte.length; i++)
            objs[i] = reporte[i];

        this.report = objs;
    }

    public InvalidCharacterException(Object[] reporte) {
        this.report = reporte;
    }

    public Object[] getReporte() {
        return this.report;
    }
}
