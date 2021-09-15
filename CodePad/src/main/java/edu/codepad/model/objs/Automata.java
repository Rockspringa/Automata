package edu.codepad.model.objs;

public interface Automata {
    
    /**
     * El metodo se encargara de mover al automata entre estado ingresando un nuevo caracter.
     * @param ch es el caracter con el que se decidira a que estado cambiara el automata.
     * @return array a agregar al reporte, por lo general se regresaria array solo al encontrar un espacio.
     */
    String[] getNextState(char ch);

    /**
     * El metodo corrobora el estado actual, para evitar que el metodo {@link #getNextState} deje sin
     * reporte del ultimo estado cuando no existe un espacio final.
     * @return array a agregar al reporte.
     */
    String[] getTokenState();

    /**
     * El metodo devolvera lo que exista en el log interno de transiciones, donde se encuentra cada
     * transicion generada por el automata incluyendo los errores.
     * @return todo lo que hay en el log.
     */
    String getLog();
}
