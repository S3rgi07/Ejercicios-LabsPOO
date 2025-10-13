package Ejercicio5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * PlanificadorProcesos.java
 * 
 * Administra una lista de procesos y los ejecuta de manera polimórfica.
 * No conoce ni manipula los tipos concretos de procesos.
 * 
 * Responsabilidad:
 * - Mantener la cola de procesos.
 * - Ejecutarlos secuencialmente (o de la forma deseada) usando polimorfismo.
 * 
 * Principios aplicados:
 * - Polimorfismo total: el planificador no sabe qué tipo de proceso ejecuta.
 * - Encapsulación: no se manipulan detalles internos de los procesos.
 */
public class PlanificadorProcesos {
    private final List<Proceso> lista;

    public PlanificadorProcesos() {
        this.lista = Collections.synchronizedList(new ArrayList<>());
    }

    // -----------------------------------
    // Métodos públicos
    // -----------------------------------

    /**
     * Agrega un proceso a la cola del planificador.
     * (El proceso debe haberse creado externamente.)
     *
     * @param p instancia de un proceso ya creado.
     */
    public void agregarProceso(Proceso p) {
        if (p != null) lista.add(p);
    }

    /**
     * Devuelve una vista de la lista de procesos.
     * 
     * @return lista de procesos registrados.
     */
    public List<Proceso> getListaProcesos() {
        return lista;
    }

    /**
     * Limpia la lista de procesos en espera.
     */
    public void limpiarProcesos() {
        lista.clear();
    }

    /**
     * Ejecuta todos los procesos registrados de manera polimórfica.
     * 
     * @param updater interfaz que permite mostrar salida en la GUI.
     */
    public void ejecutarProcesos(UIUpdater updater) {
        List<Proceso> copia;
        synchronized (lista) {
            copia = new ArrayList<>(lista);
        }

        for (Proceso p : copia) {
            try {
                updater.appendLine(String.format("$ Planificador: despachando PID %d", p.getPid()));
                p.ejecutar(updater); // polimorfismo puro
                updater.appendLine(String.format("$ Planificador: PID %d completado.", p.getPid()));
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                updater.appendLine(String.format("$ Planificador: ejecución interrumpida para PID %d.", p.getPid()));
                break;
            } catch (Exception ex) {
                updater.appendLine(String.format("$ Planificador: error en PID %d -> %s", p.getPid(), ex.getMessage()));
            }
        }
        updater.appendLine("$ Planificador: ejecución de la cola finalizada.");
    }
}

