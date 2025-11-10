package Ejercicio6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Vista extends JFrame {
    private final ControladorCatalogo controlador;
    private final JTextArea areaTexto;
    private final JTextField campoBusqueda;

    public Vista(ControladorCatalogo controlador) {
        this.controlador = controlador;
        setTitle("Catálogo de Dispositivos - Cooperativa AgroTech");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel();
        JButton btnListar = new JButton("Listar todos");
        JButton btnBuscar = new JButton("Buscar por nombre");
        JButton btnOrdenar = new JButton("Ordenar por consumo");

        campoBusqueda = new JTextField(15);
        panelSuperior.add(campoBusqueda);
        panelSuperior.add(btnBuscar);
        panelSuperior.add(btnListar);
        panelSuperior.add(btnOrdenar);
        add(panelSuperior, BorderLayout.NORTH);

        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        add(new JScrollPane(areaTexto), BorderLayout.CENTER);

        // Acciones
        btnListar.addActionListener(e -> mostrarLista(controlador.listar()));
        btnOrdenar.addActionListener(e -> mostrarLista(controlador.ordenarPorConsumo()));
        btnBuscar.addActionListener(e -> {
            String texto = campoBusqueda.getText();
            if (!texto.isBlank()) mostrarLista(controlador.buscarPorNombre(texto));
            else JOptionPane.showMessageDialog(this, "Ingrese un nombre para buscar");
        });
    }

    private void mostrarLista(java.util.List<Dispositivo> lista) {
        areaTexto.setText("");
        if (lista.isEmpty()) {
            areaTexto.append("No se encontraron dispositivos.\n");
        } else {
            lista.forEach(d -> areaTexto.append(d.toString() + "\n"));
        }
    }

    public void iniciar() {
        setVisible(true);
    }
}

