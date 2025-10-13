package Ejercicio5;

/**
 * ProcesoRed.java
 * Simula un proceso relacionado con red (descarga/latencia).
 */

public class ProcesoRed extends Proceso {

    public ProcesoRed(int pid, String nombre) {
        super(pid, nombre);
    }

    @Override
    public void ejecutar(UIUpdater updater) throws InterruptedException {
        updater.appendLine(String.format("$ [Red] Iniciando PID %d (%s)...", getPid(), getNombre()));
        updater.appendLine("$ [Red] Estableciendo conexión...");
        Thread.sleep(400);
        updater.appendLine("$ [Red] Descargando paquete 1/3...");
        Thread.sleep(550);
        updater.appendLine("$ [Red] Descargando paquete 2/3...");
        Thread.sleep(550);
        updater.appendLine("$ [Red] Descargando paquete 3/3...");
        Thread.sleep(550);
        updater.appendLine(String.format("$ [Red] PID %d finalizado.", getPid()));
    }
}
