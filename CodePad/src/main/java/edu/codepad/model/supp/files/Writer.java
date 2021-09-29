package edu.codepad.model.supp.files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Writer {
    
    private JFileChooser chooser;

    private void setJFileChooser(File file) {
        if (file != null)
            this.chooser = new JFileChooser(file.toPath().toString());

        else
            this.chooser = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        chooser.setFileFilter(filter);

        this.chooser.setDialogTitle("Escoja la carpeta donde se guardara el archivo.");
        this.chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    }

    private File getFile() {
        int aprove = chooser.showOpenDialog(null);

        if (aprove == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            return file;
        }
        
        return null;
    }

    public File export(File file, String content) throws IOException {
        this.setJFileChooser(file);
        File dir = getFile();
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dir))) {
            writer.write(content);
        }

        return dir;
    }
}
