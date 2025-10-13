package Ejercicio5;
import javax.swing.*;

/**
 * Main.java
 * Punto de entrada del simulador.
 * 
 * Crea el MVC y precarga los procesos iniciales.
 */
public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            VistaConsola vista = new VistaConsola();
            PlanificadorProcesos planificador = new PlanificadorProcesos();

            // Procesos iniciales predefinidos
            planificador.agregarProceso(new ProcesoCPU(1, "calc_main"));
            planificador.agregarProceso(new ProcesoIO(2, "reader"));
            planificador.agregarProceso(new ProcesoDaemon(3, "logger"));
            planificador.agregarProceso(new ProcesoRed(4, "downloader"));
            planificador.agregarProceso(new ProcesoCompresion(5, "zipper"));

            new Controlador(vista, planificador);
            vista.setVisible(true);

            // Mensaje de bienvenida
            vista.appendLine("$ Bienvenido al Simulador de Procesos (modo visual tipo shell).");
            vista.appendLine("$ Procesos iniciales cargados automáticamente.");
            vista.appendLine("$ Use 'Listar procesos' para verlos o 'Ejecutar cola' para simular su ejecución.");
        });
    }
}

