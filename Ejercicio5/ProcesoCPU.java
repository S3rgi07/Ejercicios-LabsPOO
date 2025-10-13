package Ejercicio5;

/**
 * ProcesoCPU.java
 * Simula un proceso intensivo de CPU (cálculo simple).
 */

public class ProcesoCPU extends Proceso {

    public ProcesoCPU(int pid, String nombre) {
        super(pid, nombre);
    }

    @Override
    public void ejecutar(UIUpdater updater) throws InterruptedException {
        updater.appendLine(String.format("$ [CPU] Iniciando PID %d (%s)...", getPid(), getNombre()));
        // Simulación de cálculo (ejemplo: cálculo de una serie simple)
        int a = 1, b = 1;
        for (int i = 0; i < 6; i++) {
            int c = a + b; // operación aritmética sencilla por iteración
            updater.appendLine(String.format("$ [CPU] %d + %d = %d", a, b, c));
            a = b;
            b = c;
            Thread.sleep(350); // pequeño retardo para simular trabajo
        }
        updater.appendLine(String.format("$ [CPU] PID %d finalizado.", getPid()));
    }
}
