package edu.codepad.controller;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;

import edu.codepad.model.exceptions.InvalidCharacterException;
import edu.codepad.model.objs.automatas.Buscador;
import edu.codepad.model.supp.analisis.Coordenadas;
import edu.codepad.model.supp.analisis.enums.GeneralToken;

/**
 * Esta clase tiene busca las cadenas de texto y las subraya en el texto
 * proporcionado.
 */
public class Highlighter {

    private ArrayList<Coordenadas> coors = new ArrayList<>();
    private String fileContent;
    private JTextPane pane;

    /**
     * @param cont es el contenido del panel de texto.
     * @param pane es el panel en el cual se hara el highlight o subrayado.
     */
    public Highlighter(String cont, JTextPane pane) {
        this.fileContent = cont;
        this.pane = pane;
    }

    /**
     * Se encarga de hacer el subrayado del texto en el panel.
     */
    private void highlightText() {
        for (Coordenadas coor : coors) {
            DefaultHighlightPainter highlightPainter = new DefaultHighlightPainter(Color.MAGENTA);

            try {
                pane.getHighlighter().addHighlight(coor.getStart(), coor.getEnd(), highlightPainter);
            } catch (BadLocationException e) {
            }
        }
    }

    /**
     * Realiza la busqueda y subrayado de un String dentro del texto proporcionado
     * al crear este objeto.
     * 
     * @param buscar es el String a buscar dentro del texto.
     */
    public void buscar(String buscar) {
        Buscador bus = new Buscador(buscar);
        int start = 1;
        int end = 1;

        for (char ch : fileContent.toCharArray()) {
            GeneralToken token = null;

            try {
                token = (GeneralToken) bus.getNextState(ch)[0];
            } catch (InvalidCharacterException e) {
            }

            if (token == null || token == GeneralToken.VACIO) {
                start = ++end;
            } else if (token == GeneralToken.COMPLETO) {
                coors.add(new Coordenadas(start - 1, end));
                start = ++end;
            } else {
                end++;
            }
        }

        highlightText();
    }
}
