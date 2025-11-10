package Ejercicio6;

import java.util.Map;

public class ControladorValvulasLegacy extends Dispositivo implements IAccionable {
    private final String modeloAntiguo;

    public ControladorValvulasLegacy(int id, String nombre, String fabricante, double consumo, String modeloAntiguo) {
        super(id, nombre, fabricante, consumo, EstadoDispositivo.ACTIVO);
        this.modeloAntiguo = modeloAntiguo;
    }

    @Override
    public void accionar(Accion accion) { accionar(accion, Map.of()); }

    @Override
    public void accionar(Accion accion, Map<String, Object> parametros) {
        // No registra ni mide, solo simula una acción
        System.out.println("[Legacy] Acción enviada al controlador antiguo: " + accion);
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | ControladorLegacy {modelo=%s}", modeloAntiguo);
    }
}

