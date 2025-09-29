/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package laboratorio2;

/**
 *
 * @author sergi
 */
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            String input = JOptionPane.showInputDialog("Ingrese el tamaño del tablero (ej. 4, 6, 8):");
            int n = Integer.parseInt(input);
            new Controller(n);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
}

