package edu.codepad.model.supp;

import java.util.ArrayList;

public class Reemplazador {

    /**
     * El objetivo del metodo es reemplazar una cadena de texto por otra.
     * 
     * @param cont      es la cadena de texto que contiene la cadena a reemplazar.
     * @param rpl       es la cadena que se desea reemplazar, o eliminar.
     * @param newString es la nueva cadena que se desea poner en lugar de la
     *                  anterior cadena.
     * @return un array de string que contiene la informacion modificada.
     */
    public static String[] replace(String cont, String rpl, String newString) {
        ArrayList<String> tmp = new ArrayList<>();
        StringBuilder sup = new StringBuilder();

        char[] rplChars = rpl.toCharArray();
        int rplIndex = 0;

        for (char l : cont.toCharArray()) {
            if (l == rplChars[rplIndex++]) {
                if (rplIndex == rplChars.length) {
                    sup.append(newString);
                    rplIndex = 0;
                }
            } else {
                for (int i = 0; i < rplIndex - 1; i++)
                    sup.append(rplChars[i]);

                rplIndex = 0;
                sup.append(l);
            }
            if (l == '\n') {
                tmp.add(sup.toString());
                sup = new StringBuilder();
            }
        }
        if (!sup.isEmpty()) {
            tmp.add(sup.toString());
        }
        if (cont.length() > 0 && cont.toCharArray()[cont.length() - 1] == '\n') {
            tmp.add("");
        }

        return tmp.toArray(new String[0]);
    }

    /**
     * El objetivo del metodo es reemplazar una cadena de texto por otra.
     * 
     * @param cont      es un array de cadenas de textos que contienen la cadena a
     *                  reemplazar.
     * @param rpl       es la cadena que se desea reemplazar, o eliminar.
     * @param newString es la nueva cadena que se desea poner en lugar de la
     *                  anterior cadena.
     * @return un string que contiene la informacion modificada.
     */
    public static String replace(String[] cont, String rpl, String newString) {
        StringBuilder sup = new StringBuilder();

        char[] rplChars = rpl.toCharArray();
        int rplIndex = 0;

        for (String elem : cont) {
            elem = elem.strip();
            for (char l : elem.toCharArray()) {
                if (l == rplChars[rplIndex++]) {
                    if (rplIndex == rplChars.length) {
                        sup.append(newString);
                        rplIndex = 0;
                    }
                } else {
                    for (int i = 0; i < rplIndex - 1; i++)
                        sup.append(rplChars[i]);

                    rplIndex = 0;
                    sup.append(l);
                }
            }
            sup.append("\n");
        }

        return sup.toString();
    }
}
