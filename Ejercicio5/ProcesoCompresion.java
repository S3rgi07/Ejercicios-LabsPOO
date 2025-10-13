package Ejercicio5;

/**
 * ProcesoCompresion.java
 * Simula compresión de datos (operaciones sencillas).
 */

public class ProcesoCompresion extends Proceso {

    public ProcesoCompresion(int pid, String nombre) {
        super(pid, nombre);
    }

    @Override
    public void ejecutar(UIUpdater updater) throws InterruptedException {
        updater.appendLine(String.format("$ [Comp] Iniciando PID %d (%s)...", getPid(), getNombre()));
        updater.appendLine("$ [Comp] Analizando bloques para compresión...");
        Thread.sleep(300);
        updater.appendLine("$ [Comp] Aplicando algoritmo simulado: ratio 2:1 (estimado)...");
        Thread.sleep(400);
        updater.appendLine(String.format("$ [Comp] PID %d finalizado. Archivo comprimido (simulado).", getPid()));
    }
}
