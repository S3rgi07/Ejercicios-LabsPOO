/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package laboratorio2;
import javax.swing.*;
import java.awt.*;

public class Controller {

    private Tablero tablero;
    private Jugador jugador1, jugador2;
    private Jugador actual;
    private Ficha primeraFicha = null;
    private JButton primerBoton = null;
    private JFrame frame;

    public Controller(int size) throws Exception {
        tablero = new Tablero(size);
        jugador1 = new Jugador(1);
        jugador2 = new Jugador(2);
        actual = jugador1;
        iniciarGUI();
    }

    private void iniciarGUI() {
        frame = new JFrame("Juego de Memoria");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(tablero.getN(), tablero.getN()));

        for (int i = 0; i < tablero.getN(); i++) {
            for (int j = 0; j < tablero.getN(); j++) {
                JButton boton = new JButton("❓");
                int fila = i, col = j;
                boton.addActionListener(e -> jugar(fila, col, boton));
                frame.add(boton);
            }
        }

        frame.pack();
        frame.setVisible(true);
    }

    public void jugar(int fila, int col, JButton boton) {
        try {
            Ficha ficha = tablero.getFicha(fila, col);

            if (ficha.estaEscogida() || ficha.estaEncontrada()) {
                throw new Exception("Ficha inválida, ya fue escogida o encontrada.");
            }

            ficha.escoger();
            boton.setText(ficha.toString());

            if (primeraFicha == null) {
                primeraFicha = ficha;
                primerBoton = boton;
            } else {
                if (primeraFicha.getSimbolo().equals(ficha.getSimbolo())) {
                    primeraFicha.encontrada();
                    ficha.encontrada();
                    actual.setParesDescubiertos(actual.getParesDescubiertos() + 1);
                } else {
                    Ficha copiaPrimera = primeraFicha;
                    Ficha copiaSegunda = ficha;
                    JButton botonPrimero = primerBoton;
                    JButton botonSegundo = boton;

                    Timer timer = new Timer(1000, ev -> {
                        copiaPrimera.ocultar();
                        copiaSegunda.ocultar();
                        botonPrimero.setText("❓");
                        botonSegundo.setText("❓");
                        cambiarTurno();
                    });
                    timer.setRepeats(false);
                    timer.start();
                }
                primeraFicha = null;
                primerBoton = null;

            }

            if (tablero.todasEncontradas()) {
                mostrarGanador();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cambiarTurno() {
        actual = (actual == jugador1) ? jugador2 : jugador1;
        JOptionPane.showMessageDialog(frame, "Turno del Jugador " + actual.getId());
    }

    private void mostrarGanador() {
        String mensaje = "Juego terminado\n"
                + "Jugador 1: " + jugador1.getParesDescubiertos() + " pares\n"
                + "Jugador 2: " + jugador2.getParesDescubiertos() + " pares\n";

        if (jugador1.getParesDescubiertos() > jugador2.getParesDescubiertos()) {
            mensaje += "Ganador: Jugador 1 🎉";
        } else if (jugador2.getParesDescubiertos() > jugador1.getParesDescubiertos()) {
            mensaje += "Ganador: Jugador 2 🎉";
        } else {
            mensaje += "Empate 🤝";
        }

        JOptionPane.showMessageDialog(frame, mensaje);
        System.exit(0);
    }
}
