package Ejercicio5;

/**
 * ProcesoDaemon.java
 * Simula un daemon que hace tareas de fondo (mensajes periódicos).
 */

public class ProcesoDaemon extends Proceso {

    public ProcesoDaemon(int pid, String nombre) {
        super(pid, nombre);
    }

    @Override
    public void ejecutar(UIUpdater updater) throws InterruptedException {
        updater.appendLine(String.format("$ [Daemon] Iniciando PID %d (%s)...", getPid(), getNombre()));
        // Simular pequeñas tareas periódicas (no será infinito: simulamos 3 ciclos)
        for (int i = 1; i <= 3; i++) {
            updater.appendLine(String.format("$ [Daemon] Verificación %d/3: estado OK.", i));
            Thread.sleep(450);
        }
        updater.appendLine(String.format("$ [Daemon] PID %d finalizado (servicio temporal simulado).", getPid()));
    }
}
