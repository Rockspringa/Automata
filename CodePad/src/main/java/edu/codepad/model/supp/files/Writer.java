package edu.codepad.model.supp.files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * El objeto <code>Writer</code> tiene por objetivo guardar archivos.
 */
public class Writer {

    private JFileChooser chooser;

    /**
     * Crea un JFileChooser que se muestra en la carpeta del archivo que se ingresa.
     * 
     * @param file directoria en el cual se abrira el JFileChooser.
     */
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

    /**
     * Abre el JFileChooser para poder escoger en el cual se sobreescribiran los
     * datos.
     * 
     * @return el archivo escogido por medio del JFileChooser.
     */
    private File getFile() {
        int aprove = chooser.showOpenDialog(null);

        if (aprove == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            return file;
        }

        return null;
    }

    /**
     * Se encarga de abrir un JFileChooser para escoger donde se guardaran los
     * datos, y los escribe.
     * 
     * @param file    indica el directorio donde se abrira el JFileChooser.
     * @param content es el contenido que se escribira o exportara.
     * @return el archivo en el cual se escribieron los datos.
     */
    public File export(File file, String content) throws IOException {
        this.setJFileChooser(file);
        File dir = getFile();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dir))) {
            writer.write(content);
        }

        return dir;
    }
}
