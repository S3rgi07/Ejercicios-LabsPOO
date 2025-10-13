package Ejercicio5;

import javax.swing.*;
import java.awt.*;

/**
 * VistaConsola.java
 * Ventana principal tipo shell. Ahora sin controles para agregar procesos;
 * solo permite listar, ejecutar y limpiar.
 */
public class VistaConsola extends JFrame implements UIUpdater {
    private final JTextArea consoleArea;
    private final JButton ejecutarBtn;
    private final JButton limpiarBtn;
    private final JButton listarBtn;

    public VistaConsola() {
        super("Simulador de Procesos - Terminal Simulada");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 520);
        setLocationRelativeTo(null);

        consoleArea = new JTextArea();
        consoleArea.setEditable(false);
        consoleArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        consoleArea.setBackground(Color.BLACK);
        consoleArea.setForeground(new Color(0x7CFC00)); // verde tipo terminal
        consoleArea.setMargin(new Insets(8, 8, 8, 8));

        JScrollPane scroll = new JScrollPane(consoleArea);

        // Panel superior de controles
        JPanel control = new JPanel(new FlowLayout(FlowLayout.LEFT));

        listarBtn = new JButton("Listar procesos");
        ejecutarBtn = new JButton("Ejecutar cola");
        limpiarBtn = new JButton("Limpiar consola");

        control.add(listarBtn);
        control.add(ejecutarBtn);
        control.add(limpiarBtn);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scroll, BorderLayout.CENTER);
        getContentPane().add(control, BorderLayout.NORTH);
    }

    // Implementación de UIUpdater
    @Override
    public void appendLine(String line) {
        SwingUtilities.invokeLater(() -> {
            consoleArea.append(line + "\n");
            consoleArea.setCaretPosition(consoleArea.getDocument().getLength());
        });
    }

    // Listeners para el controlador
    public void addEjecutarListener(java.awt.event.ActionListener al) {
        ejecutarBtn.addActionListener(al);
    }

    public void addLimpiarListener(java.awt.event.ActionListener al) {
        limpiarBtn.addActionListener(al);
    }

    public void addListarListener(java.awt.event.ActionListener al) {
        listarBtn.addActionListener(al);
    }
}


