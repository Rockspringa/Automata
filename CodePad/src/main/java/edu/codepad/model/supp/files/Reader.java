package edu.codepad.model.supp.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * El objeto <code>Reader</code> tiene por objetivo leer archivos de texto.
 */
public class Reader {

    private final JFileChooser chooser;

    /**
     * Al momento de crear el objeto <code>Reader</code>, crea un
     * <code>JFileChooser</code> para escojer el archivo a leer en el futuro.
     */
    public Reader() {
        chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        chooser.setFileFilter(filter);
    }

    /**
     * Utilizado para escoger el archivo a leer.
     * 
     * @param parent es la ventana a la cual sera relativo el
     *               <code>JFileChooser</code>.
     * @return un objeto <code>File</code> que representa el archivo escogido.
     */
    public File read(JFrame parent) {
        int aprove = chooser.showOpenDialog(parent);
        if (aprove == JFileChooser.APPROVE_OPTION)
            return chooser.getSelectedFile();
        return null;
    }

    /**
     * Lee el contenido del archivo ingresado, no importa si no fue el que se
     * escogio mediante esta misma clase.
     * 
     * @param file es el objeto que representa el archivo del cual se leera el
     *             contenido.
     * @return un array de String que contiene cada linea del archivo.
     */
    public String[] getContentFile(File file) throws IOException {
        ArrayList<String> out = new ArrayList<>();
        String line;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            while ((line = reader.readLine()) != null)
                out.add(line + "\n");

        }
        return out.toArray(new String[0]);
    }
}
