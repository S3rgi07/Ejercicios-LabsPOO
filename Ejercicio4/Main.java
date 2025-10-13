package Ejercicio4;

import javax.swing.*;

/*
 Clase Main: pregunta modo (1 o 2 jugadores) con un diálogo y arranca Controller.
*/
public class Main {
    public static void main(String[] args) {
        // Pedir modo: 1 jugador o 2 jugadores (usando JOptionPane)
        String[] opciones = {"1 Jugador", "2 Jugadores"};
        int seleccion = JOptionPane.showOptionDialog(null,
                "Elige modo de juego",
                "Modo",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]);
        int modo = (seleccion == 1) ? 2 : 1;
        Controller controller = new Controller(modo);
        controller.start();
    }
}
