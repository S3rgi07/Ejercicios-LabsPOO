package Ejercicio6;

import java.util.ArrayList;
import java.util.List;

public class SensorSuelo extends Dispositivo implements IMedible, IRegistrable {
    private final double profundidadCm;
    private final String tipoSuelo;
    private final List<Registro> historial = new ArrayList<>();

    public SensorSuelo(int id, String nombre, String fabricante, double consumo, double profundidadCm, String tipoSuelo) {
        super(id, nombre, fabricante, consumo, EstadoDispositivo.ACTIVO);
        this.profundidadCm = profundidadCm;
        this.tipoSuelo = tipoSuelo;
    }

    @Override
    public Lectura medir() {
        double humedad = 10 + Math.random() * 60; // porcentaje
        return new Lectura("Humedad del suelo", humedad, Unidad.PORCENTAJE);
    }

    @Override
    public String getUnidad() { return Unidad.PORCENTAJE.name(); }

    @Override
    public void registrar(Registro registro) { historial.add(registro); }

    @Override
    public List<Registro> getHistorial() { return List.copyOf(historial); }

    @Override
    public String toString() {
        return super.toString() + String.format(" | SensorSuelo {%.1fcm, tipo=%s}", profundidadCm, tipoSuelo);
    }
}

