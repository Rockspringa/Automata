package edu.codepad.model.supp.analisis.enums;

/**
 * Es la representacion de los posibles estados que puede tomar especificamente
 * el automata general de este sistema. Algunos de los estados son tomados como
 * de aceptacion o no aceptacion.
 */
public enum Token {
    VACIO, IDENTIFICADOR, NUMERO, DECIMAL, PUNTUACION, OPERADOR, AGRUPACION, INCOMPLETO;
}
