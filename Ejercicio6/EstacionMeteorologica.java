package Ejercicio6;

import java.util.ArrayList;
import java.util.List;

public class EstacionMeteorologica extends Dispositivo implements IMedible, IRegistrable {
    private final boolean tienePluviometro;
    private final boolean tieneAnemometro;
    private final List<Registro> historial = new ArrayList<>();

    public EstacionMeteorologica(int id, String nombre, String fabricante, double consumo,
                                 boolean pluviometro, boolean anemometro) {
        super(id, nombre, fabricante, consumo, EstadoDispositivo.ACTIVO);
        this.tienePluviometro = pluviometro;
        this.tieneAnemometro = anemometro;
    }

    @Override
    public Lectura medir() {
        double temp = 15 + Math.random() * 18;
        return new Lectura("Temperatura ambiente", temp, Unidad.C);
    }

    @Override
    public String getUnidad() { return Unidad.C.name(); }

    @Override
    public void registrar(Registro registro) { historial.add(registro); }

    @Override
    public List<Registro> getHistorial() { return List.copyOf(historial); }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Estacion {pluv=%s, anem=%s}", tienePluviometro, tieneAnemometro);
    }
}

