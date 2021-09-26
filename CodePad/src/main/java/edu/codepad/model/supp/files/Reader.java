package edu.codepad.model.supp.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Reader {

    private final JFileChooser chooser;

    public Reader() {
        chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        chooser.setFileFilter(filter);
    }
    
    public File read(JFrame parent) {
        int aprove = chooser.showOpenDialog(parent);
        if (aprove == JFileChooser.APPROVE_OPTION) return chooser.getSelectedFile();
        return null;
    }

    public String[] getContentFile(File file) {
        ArrayList<String> out = new ArrayList<>();
        String line;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            while ((line = reader.readLine()) != null)
                out.add(line + "\n");

        } catch (IOException e) {
            out.add("Hubo un error al abrir o leer el archivo. Msg: " + e.getMessage());
        }
        return out.toArray(new String[0]);
    }
}
