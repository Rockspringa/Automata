package edu.codepad.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import edu.codepad.controller.ContentManager;

public class Principal extends JFrame implements KeyListener {

    private ContentManager manager;
    private Principal window = this;

    private JTextPane textoPrincipal;

    private JPanel botoneraPane;
    private JButton cargarBtn;
    private JButton guardarBtn;

    public Principal() {
        super("Analizador lexico");
        this.setLayout(new BorderLayout(10, 20));
        ((JPanel) this.getContentPane()).setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        this.textoPrincipal = new JTextPane();
        this.textoPrincipal.addKeyListener(this);
        this.add(this.textoPrincipal, BorderLayout.CENTER);

        this.botoneraPane = new JPanel();
        this.botoneraPane.setLayout(new GridLayout(0, 1, 10, 50));
        this.add(this.botoneraPane, BorderLayout.LINE_END);

        this.cargarBtn = new JButton("Cargar Archivo");
        this.cargarBtn.addActionListener(new CargarArchivoListener());
        this.botoneraPane.add(this.cargarBtn);

        this.guardarBtn = new JButton("Guardar Archivo");
        this.guardarBtn.addActionListener(new GuardarArchivoListener());
        this.botoneraPane.add(this.guardarBtn);

        this.setBounds(0, 0, 500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // agregar contador de lineas
        this.manager.setContent(this.textoPrincipal.getText());
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    public void setContentManager(ContentManager manager) {
        this.manager = manager;
    }

    private class CargarArchivoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            textoPrincipal.setText(manager.updateTextArea());
        }

    }

    private class GuardarArchivoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (manager.saveChanges()) {
                    JOptionPane.showMessageDialog(window, "El archivo se ha modificado con exito.", "Archivo guardado",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(window,
                            "Ocurrio un error al momento de intentar guardar el archivo, vuelva a intentarlo.",
                            "Error al guardar", JOptionPane.ERROR_MESSAGE);
                }
            } catch (HeadlessException | IOException e1) {
                JOptionPane.showMessageDialog(window,
                        "Ocurrio un error al momento de intentar guardar el archivo, vuelva a intentarlo.",
                        "Error al guardar", JOptionPane.ERROR_MESSAGE);
            }
        }

    }
}
