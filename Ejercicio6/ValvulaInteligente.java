package Ejercicio6;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ValvulaInteligente extends Dispositivo implements IAccionable, IRegistrable {
    private final String sector;
    private double aperturaActual;
    private final List<Registro> historial = new ArrayList<>();

    public ValvulaInteligente(int id, String nombre, String fabricante, double consumo, String sector) {
        super(id, nombre, fabricante, consumo, EstadoDispositivo.ACTIVO);
        this.sector = sector;
        this.aperturaActual = 0;
    }

    @Override
    public void accionar(Accion accion) {
        accionar(accion, Map.of());
    }

    @Override
    public void accionar(Accion accion, Map<String, Object> parametros) {
        switch (accion.getTipo()) {
            case ABRIR -> {
                double pct = (Double) parametros.getOrDefault("porcentaje", 100.0);
                aperturaActual = Math.max(0, Math.min(100, pct));
                registrar(new Registro("Valvula ABRIR", Map.of("sector", sector, "apertura", aperturaActual)));
            }
            case CERRAR -> {
                aperturaActual = 0;
                registrar(new Registro("Valvula CERRAR", Map.of("sector", sector)));
            }
            default -> registrar(new Registro("Accion no soportada", Map.of("accion", accion.getTipo())));
        }
    }

    @Override
    public void registrar(Registro registro) { historial.add(registro); }

    @Override
    public List<Registro> getHistorial() { return List.copyOf(historial); }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Valvula {sector=%s, apertura=%.0f%%}", sector, aperturaActual);
    }
}

