package edu.codepad.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.text.DefaultCaret;

import edu.codepad.controller.ContentManager;
import edu.codepad.controller.Reconocedor;

public class Principal extends JFrame implements KeyListener, FocusListener {
    public static final Font JBRAINS = new Font("JetBrainsMono NF", 1, 13);

    private ContentManager manager;
    private Principal window = this;

    private JPanel panelTexto;
    private JScrollPane contadorScroll;
    private JScrollPane principalScroll;
    private JTextPane textoContador;
    private JTextPane textoPrincipal;

    private JPanel botoneraPane;
    private JButton cargarBtn;
    private JButton guardarBtn;
    private JButton buscarBtn;
    private JButton reporteBtn;

    private Report reportWindow;

    static {
        UIManager.put("Table.font", JBRAINS);
        UIManager.put("Label.font", JBRAINS);
        UIManager.put("Button.font", JBRAINS);
        UIManager.put("TextPane.font", JBRAINS);
    }

    public Principal() {
        super("Analizador lexico");
        this.setLayout(new BorderLayout(10, 5));
        ((JPanel) this.getContentPane()).setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        /* Componentes para la parte de texto, numero de linea y demas */

        this.panelTexto = new JPanel(new BorderLayout(3, 0));
        this.add(this.panelTexto, BorderLayout.CENTER);

        this.textoContador = new JTextPane();
        this.textoContador.setText("    ");
        this.textoContador.setEditable(false);

        DefaultCaret caret = (DefaultCaret) textoContador.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);

        this.textoPrincipal = new JTextPane();
        this.textoPrincipal.addKeyListener(this);
        this.textoPrincipal.addFocusListener(this);

        this.contadorScroll = new JScrollPane(this.textoContador);
        this.contadorScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        this.contadorScroll.setHorizontalScrollBar(null);
        this.panelTexto.add(this.contadorScroll, BorderLayout.LINE_START);

        this.principalScroll = new JScrollPane(this.textoPrincipal);
        this.panelTexto.add(this.principalScroll, BorderLayout.CENTER);

        this.contadorScroll.getVerticalScrollBar().setModel(this.principalScroll.getVerticalScrollBar().getModel());

        /* Componentes de la parte de botones */

        this.botoneraPane = new JPanel();
        this.botoneraPane.setLayout(new GridLayout(1, 0, 10, 0));
        this.add(this.botoneraPane, BorderLayout.PAGE_START);

        /* Boton para cargar un archivo con un panel que lo coloque */

        this.cargarBtn = new JButton("\nCargar Archivo\n");
        this.cargarBtn.addActionListener(new CargarArchivoListener());

        JPanel cargarPane = new JPanel();
        cargarPane.add(this.cargarBtn);
        this.botoneraPane.add(cargarPane);

        /* Boton para guardar los cambios del archivo */

        this.guardarBtn = new JButton("\nGuardar Archivo\n");
        this.guardarBtn.addActionListener(new GuardarArchivoListener());

        JPanel guardarPane = new JPanel();
        guardarPane.add(this.guardarBtn);
        this.botoneraPane.add(guardarPane);

        /* Boton para generar un reporte de tokens */

        this.reporteBtn = new JButton("\nGenerar Reporte\n");
        this.reporteBtn.addActionListener(new ReporteListener());

        JPanel reportePane = new JPanel();
        reportePane.add(this.reporteBtn);
        this.botoneraPane.add(reportePane);

        /* Boton para abrir el buscador de caracteres */

        this.buscarBtn = new JButton("\nAbrir Buscador\n");
        this.buscarBtn.addActionListener(new BuscadorListener());

        JPanel buscarPane = new JPanel();
        buscarPane.add(this.buscarBtn);
        this.botoneraPane.add(buscarPane);

        /* Seteo de la informacion de la ventana */

        this.setBounds(0, 0, 750, 600);
        this.setMinimumSize(new Dimension(700, 400));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        this.manager.setContent(this.textoPrincipal.getText().replace("\r", ""));
        this.textoContador.setText(this.manager.getLineNums());

        if (reportWindow != null)
            reportWindow.setVisible(false);
    }

    @Override
    public void focusGained(FocusEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void focusLost(FocusEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void setContentManager(ContentManager manager) {
        this.manager = manager;
    }

    private class CargarArchivoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                textoPrincipal.setText(manager.updateTextArea());
                textoContador.setText(manager.getLineNums());
            } catch (IOException | NullPointerException ex) {
                JOptionPane.showMessageDialog(window,
                        "Hubo un error al abrir o leer el archivo. Msg: " + ex.getMessage(), "Error al cargar archivo",
                        JOptionPane.ERROR_MESSAGE);
            }
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

    private class BuscadorListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // modificar
        }

    }

    private class ReporteListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Reconocedor recog = new Reconocedor(textoPrincipal.getText().replace("\r", ""));

            if (reportWindow == null) {
                reportWindow = new Report();
            }
            reportWindow.setContent(recog);
            reportWindow.setVisible(true);
        }

    }
}
