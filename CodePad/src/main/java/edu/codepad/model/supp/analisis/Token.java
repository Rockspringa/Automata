package edu.codepad.model.supp.analisis;

public enum Token {
    VACIO(),
    IDENTIFICADOR(),
    NUMERO(),
    DECIMAL(),
    PUNTUACION(),
    OPERADOR(),
    AGRUPACION(),
    INCOMPLETO("Se esperaba un digito.");

    private final String msg;

    private Token() {
        this.msg = "";
    }

    private Token(String msg) {
        this.msg = msg;
    }

    public String getMessage() {
        return this.msg;
    }
}
