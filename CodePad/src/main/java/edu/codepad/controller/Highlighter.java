package edu.codepad.controller;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JEditorPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;

import edu.codepad.model.exceptions.InvalidCharacterException;
import edu.codepad.model.objs.automatas.Buscador;
import edu.codepad.model.supp.analisis.Coordenadas;
import edu.codepad.model.supp.analisis.enums.GeneralToken;

public class Highlighter {

    private ArrayList<Coordenadas> coors = new ArrayList<>();
    private String fileContent;
    private JEditorPane pane;

    public Highlighter(String cont, JEditorPane pane) {
        this.fileContent = cont;
        this.pane = pane;
    }

    private void highlightText() {
        for (Coordenadas coor : coors) {
            DefaultHighlightPainter highlightPainter = new DefaultHighlightPainter(Color.MAGENTA);

            try {
                pane.getHighlighter().addHighlight(coor.getStart(), coor.getEnd(), highlightPainter);
            } catch (BadLocationException e) {
            }
        }
    }

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
