package Ejercicio6;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DronCamara extends Dispositivo implements IMedible, IAccionable, IRegistrable {
    private final String resolucion;
    private final boolean multiespectral;
    private final List<Registro> historial = new ArrayList<>();

    public DronCamara(int id, String nombre, String fabricante, double consumo,
                      String resolucion, boolean multiespectral) {
        super(id, nombre, fabricante, consumo, EstadoDispositivo.ACTIVO);
        this.resolucion = resolucion;
        this.multiespectral = multiespectral;
    }

    @Override
    public Lectura medir() {
        double ndvi = 0.1 + Math.random() * 0.8;
        return new Lectura("NDVI", ndvi, Unidad.NDVI);
    }

    @Override
    public String getUnidad() { return Unidad.NDVI.name(); }

    @Override
    public void accionar(Accion accion) { accionar(accion, Map.of()); }

    @Override
    public void accionar(Accion accion, Map<String, Object> parametros) {
        switch (accion.getTipo()) {
            case CAPTURAR_IMAGEN, PATRULLAR -> registrar(new Registro("DronCamara " + accion.getTipo(), parametros));
            default -> registrar(new Registro("Accion no soportada", Map.of("accion", accion.getTipo())));
        }
    }

    @Override
    public void registrar(Registro registro) { historial.add(registro); }

    @Override
    public List<Registro> getHistorial() { return List.copyOf(historial); }

    @Override
    public String toString() {
        return super.toString() + String.format(" | DronCamara {res=%s, multi=%s}", resolucion, multiespectral);
    }
}

