package edu.codepad.controller;

import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import edu.codepad.model.supp.busqueda.Reemplazador;
import edu.codepad.model.supp.files.Reader;
import edu.codepad.model.supp.files.Writer;

/**
 * Uno de los controladores, este sirve para cargar texto, o contenido, a la
 * vista, proveer el contenido a los otros controladores y actualizar el archivo
 * de entrada.
 */
public class ContentManager {

    private String[] fileContent;
    private File actualFile;

    private final JFrame frame;

    public ContentManager(JFrame frame) {
        this.frame = frame;
    }

    /**
     * El objetivo del metodo es el de actualizar el area de texto de la vista
     * mediante un archivo de entrada.
     */
    public String updateTextArea() {
        StringBuilder sb = new StringBuilder();
        Reader reader = new Reader();

        actualFile = reader.read(frame);
        fileContent = reader.getContentFile(actualFile);

        for (String line : fileContent)
            sb.append(line);

        fileContent = Reemplazador.replace(sb.toString(), "\n", "|");

        return sb.toString();
    }

    /**
     * El metodo sirve para que se ingrese el contenido del area de texto de la
     * vista al manejador, esto al momento de cambiar el de la vista, para asi tener
     * el mismo contenido el controlador y la vista.
     */
    public void setContent(String cont) {
        fileContent = Reemplazador.replace(cont, "\n", "");
    }

    /**
     * El metodo sirve para recuperar el texto guardado dento del manejador, para
     * eliminar el subrayado, manteniendo el contenido limpio.
     */
    public String getDefaultText() {
        return Reemplazador.replace(fileContent, "|", "\n");
    }

    /**
     * El metodo sirve para actualizar el archivo de entrada, no funcionara para
     * crear un nuevo archivo.
     */
    public boolean saveChanges() throws IOException {
        Writer writer = new Writer(actualFile.toPath().toString());
        writer.export(actualFile, Reemplazador.replace(fileContent, "|", "\n"));
        return true;
    }
}
