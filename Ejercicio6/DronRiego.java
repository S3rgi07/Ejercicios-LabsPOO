package Ejercicio6;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DronRiego extends Dispositivo implements IAccionable, IRegistrable {
    private final double capacidadTanqueL;
    private final double alturaMaximaM;
    private final List<Registro> historial = new ArrayList<>();

    public DronRiego(int id, String nombre, String fabricante, double consumo,
                     double capacidadTanqueL, double alturaMaximaM) {
        super(id, nombre, fabricante, consumo, EstadoDispositivo.ACTIVO);
        this.capacidadTanqueL = capacidadTanqueL;
        this.alturaMaximaM = alturaMaximaM;
    }

    @Override
    public void accionar(Accion accion) { accionar(accion, Map.of()); }

    @Override
    public void accionar(Accion accion, Map<String, Object> parametros) {
        switch (accion.getTipo()) {
            case ROCIAR -> registrar(new Registro("Dron Rociando", parametros));
            case REGRESAR_BASE -> registrar(new Registro("Dron regresando a base", parametros));
            default -> registrar(new Registro("Accion no soportada", Map.of("accion", accion.getTipo())));
        }
    }

    @Override
    public void registrar(Registro registro) { historial.add(registro); }

    @Override
    public List<Registro> getHistorial() { return List.copyOf(historial); }

    @Override
    public String toString() {
        return super.toString() + String.format(" | DronRiego {tanque=%.1fL, altura=%.1fm}", capacidadTanqueL, alturaMaximaM);
    }
}

