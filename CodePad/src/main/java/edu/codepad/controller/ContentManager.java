package edu.codepad.controller;

import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import edu.codepad.model.supp.busqueda.Reemplazador;
import edu.codepad.model.supp.files.Reader;
import edu.codepad.model.supp.files.Writer;

public class ContentManager {

    private String[] fileContent;
    private File actualFile;

    private final JFrame frame;

    public ContentManager(JFrame frame) {
        this.frame = frame;
    }

    public String updateTextArea() {
        StringBuilder sb = new StringBuilder();
        Reader reader = new Reader();

        actualFile = reader.read(frame);
        fileContent = reader.getContentFile(actualFile);

        for (String line : fileContent)
            sb.append(line);

        return sb.toString();
    }

    public void setContent(String cont) {
        fileContent = Reemplazador.replace(cont, "\n", " ");
    }

    public String getDefaultText() {
        return Reemplazador.replace(fileContent, "|", "|");
    }

    public boolean saveChanges() throws IOException {
        Writer writer = new Writer(actualFile.toPath().toString());
        writer.export(actualFile, fileContent);
        return true;
    }
}
