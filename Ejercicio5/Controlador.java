package Ejercicio5;

import javax.swing.*;

/**
 * Controlador.java
 * Coordina la vista y el planificador.
 * 
 * Ahora ya no permite agregar procesos desde la UI.
 * Solo permite listar, ejecutar y limpiar la consola.
 */
public class Controlador {
    private final VistaConsola vista;
    private final PlanificadorProcesos planificador;

    public Controlador(VistaConsola vista, PlanificadorProcesos planificador) {
        this.vista = vista;
        this.planificador = planificador;
        init();
    }

    private void init() {
        // Listar procesos existentes
        vista.addListarListener(e -> {
            if (planificador.getListaProcesos().isEmpty()) {
                vista.appendLine("$ Planificador: cola vacía.");
            } else {
                vista.appendLine("$ Planificador: lista de procesos en cola:");
                for (Proceso p : planificador.getListaProcesos()) {
                    vista.appendLine(String.format("  - PID %d, nombre='%s'", p.getPid(), p.getNombre()));
                }
            }
        });

        // Ejecutar cola
        vista.addEjecutarListener(e -> {
            if (planificador.getListaProcesos().isEmpty()) {
                vista.appendLine("$ Planificador: no hay procesos para ejecutar.");
                return;
            }
            vista.appendLine("$ Planificador: iniciando ejecución de la cola...");

            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    planificador.ejecutarProcesos(vista);
                    return null;
                }

                @Override
                protected void done() {
                    vista.appendLine("$ Planificador: ejecución finalizada.");
                }
            };
            worker.execute();
        });

        // Limpiar consola
        vista.addLimpiarListener(e -> {
            SwingUtilities.invokeLater(() -> {
                vista.appendLine("$ Terminal: limpiando salida...");
                try {
                    Thread.sleep(120);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                javax.swing.JTextArea area = (javax.swing.JTextArea)
                        ((javax.swing.JScrollPane) vista.getContentPane().getComponent(0)).getViewport().getView();
                area.setText("");
                area.setCaretPosition(0);
            });
        });
    }
}
