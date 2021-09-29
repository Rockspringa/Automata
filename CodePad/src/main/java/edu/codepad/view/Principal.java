package edu.codepad.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.text.DefaultCaret;

import edu.codepad.controller.ContentManager;
import edu.codepad.controller.Highlighter;
import edu.codepad.controller.Reconocedor;

public class Principal extends JFrame implements KeyListener {
    public static final Font JB_BOLD = new Font("JetBrainsMono Nerd Font Mono", 1, 15);
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
    private JToggleButton buscarBtn;
    private JButton reporteBtn;

    private Report reportWindow;

    static {
        UIManager.put("Table.font", JBRAINS);
        UIManager.put("Label.font", JBRAINS);
        UIManager.put("Button.font", JB_BOLD);
        UIManager.put("TextPane.font", JBRAINS);
        UIManager.put("MenuItem.font", JBRAINS);
        UIManager.put("TableHeader.font", JB_BOLD);
        UIManager.put("ToggleButton.font", JB_BOLD);

        UIManager.put("ScrollBar.thumbDarkShadow", Color.LIGHT_GRAY);
        UIManager.put("ScrollBar.thumbHighlight", Color.DARK_GRAY);
        UIManager.put("ScrollBar.thumbShadow", Color.DARK_GRAY);
        UIManager.put("ScrollBar.background", Color.GRAY);
        UIManager.put("ScrollBar.thumb", Color.DARK_GRAY);
        UIManager.put("ScrollBar.track", Color.GRAY);
        UIManager.put("ScrollBar.width", 12);

        UIManager.put("ComboBox.font", JBRAINS);
        UIManager.put("ComboBox.background", Color.GRAY);
        UIManager.put("ComboBox.foreground", Color.BLACK);
        UIManager.put("ComboBox.buttonBackground", Color.DARK_GRAY);
        UIManager.put("ComboBox.border", BorderFactory.createEmptyBorder());
        UIManager.put("ComboBox.focus", Color.WHITE);
        UIManager.put("ComboBox.selectionBackground", Color.BLACK);
        UIManager.put("ComboBox.selectionForeground", Color.WHITE);
        UIManager.put("ComboBox.selectionFocus", Color.WHITE);

        UIManager.put("ScrollPane.background", Color.LIGHT_GRAY);
        UIManager.put("TextPane.background", Color.LIGHT_GRAY);

        UIManager.put("TableHeader.background", Color.DARK_GRAY);
        UIManager.put("TableHeader.foreground", Color.WHITE);
        UIManager.put("Table.background", Color.LIGHT_GRAY);
        UIManager.put("Table.gridColor", Color.DARK_GRAY);

        UIManager.put("Button.background", Color.DARK_GRAY);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.select", Color.BLACK);
        UIManager.put("Button.focus", Color.LIGHT_GRAY);
        UIManager.put("Button.border", BorderFactory.createEmptyBorder(5, 10, 5, 10));

        UIManager.put("ToggleButton.select", Color.GRAY);
        UIManager.put("ToggleButton.background", Color.DARK_GRAY);
        UIManager.put("ToggleButton.foreground", Color.WHITE);
        UIManager.put("ToggleButton.focus", Color.WHITE);
        UIManager.put("ToggleButton.border", BorderFactory.createEmptyBorder(5, 10, 5, 10));
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

        this.contadorScroll = new JScrollPane(this.textoContador);
        this.contadorScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        this.contadorScroll.setHorizontalScrollBar(null);
        this.panelTexto.add(this.contadorScroll, BorderLayout.LINE_START);

        this.principalScroll = new JScrollPane(this.textoPrincipal);
        this.panelTexto.add(this.principalScroll, BorderLayout.CENTER);

        this.principalScroll.getVerticalScrollBar().setUI(new BasicScrollBarUI());
        this.principalScroll.getHorizontalScrollBar().setUI(new BasicScrollBarUI());
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

        this.buscarBtn = new JToggleButton("\nAbrir Buscador\n");
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
        if (buscarBtn.isSelected())
            buscarBtn.doClick();

        this.manager.setContent(this.textoPrincipal.getText().replace("\r", ""));
        this.textoContador.setText(this.manager.getLineNums());

        if (reportWindow != null)
            reportWindow.setVisible(false);
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
                if (buscarBtn.isSelected())
                    buscarBtn.doClick();

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
                if (buscarBtn.isSelected())
                    buscarBtn.doClick();

                if (manager.saveChanges()) {
                    JOptionPane.showMessageDialog(window, "El archivo se ha modificado con exito.", "Archivo guardado",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(window,
                            "Ocurrio un error al momento de intentar guardar el archivo, vuelva a intentarlo.",
                            "Error al guardar", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NullPointerException | HeadlessException | IOException e1) {
                JOptionPane.showMessageDialog(window,
                        "Ocurrio un error al momento de intentar guardar el archivo, vuelva a intentarlo.",
                        "Error al guardar", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    private class BuscadorListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JToggleButton btn = (JToggleButton) e.getSource();

            if (btn.isSelected()) {
                String buscar = JOptionPane.showInputDialog(window, "Ingrese la cadena de texto a buscar",
                        "Busqueda de cadenas", JOptionPane.QUESTION_MESSAGE);

                Highlighter highlighter = new Highlighter(textoPrincipal.getText().replace("\r", ""), textoPrincipal);
                highlighter.buscar(buscar);
            } else {
                textoPrincipal.getHighlighter().removeAllHighlights();
            }
        }

    }

    private class ReporteListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Reconocedor recog = new Reconocedor(textoPrincipal.getText().replace("\r", ""));

            if (buscarBtn.isSelected())
                buscarBtn.doClick();

            if (reportWindow == null) {
                reportWindow = new Report();
            }
            reportWindow.setContent(recog);
            reportWindow.setVisible(true);
        }

    }
}
