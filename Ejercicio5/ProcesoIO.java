package Ejercicio5;

/**
 * ProcesoIO.java
 * Simula un proceso de entrada/salida que 'espera' por dispositivos.
 */

public class ProcesoIO extends Proceso {

    public ProcesoIO(int pid, String nombre) {
        super(pid, nombre);
    }

    @Override
    public void ejecutar(UIUpdater updater) throws InterruptedException {
        updater.appendLine(String.format("$ [I/O] Iniciando PID %d (%s)...", getPid(), getNombre()));
        updater.appendLine("$ [I/O] Abriendo /dev/fakeDevice...");
        Thread.sleep(600);
        updater.appendLine("$ [I/O] Leyendo bloques... (simulado)");
        Thread.sleep(700);
        updater.appendLine("$ [I/O] Escribiendo buffer en disco (simulado)...");
        Thread.sleep(500);
        updater.appendLine(String.format("$ [I/O] PID %d finalizado.", getPid()));
    }
}

