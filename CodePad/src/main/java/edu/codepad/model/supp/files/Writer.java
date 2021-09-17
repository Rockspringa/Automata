package edu.codepad.model.supp.files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;

public class Writer {
    
    private final JFileChooser chooser;

    public Writer() {
        this.chooser = new JFileChooser();
        this.chooser.setDialogTitle("Escoja la carpeta donde se guardara el archivo.");
        this.chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }

    public Writer(String path) {
        this.chooser = new JFileChooser(path);
        this.chooser.setDialogTitle("Escoja la carpeta donde se guardara el archivo.");
        this.chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }

    public void export(File file, String[] content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            StringBuilder sb = new StringBuilder();

            for (String line : content)
                sb.append(line);

            writer.write(sb.toString());
        }
    }
}
