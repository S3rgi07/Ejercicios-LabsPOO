package Ejercicio6;

import java.util.Objects;

public abstract class Dispositivo implements Comparable<Dispositivo> {
    protected final int id;
    protected final String nombre;
    protected final String fabricante;
    protected final double consumoElectrico;
    protected EstadoDispositivo estado;

    public Dispositivo(int id, String nombre, String fabricante, double consumoElectrico, EstadoDispositivo estado) {
        this.id = id;
        this.nombre = nombre;
        this.fabricante = fabricante;
        this.consumoElectrico = consumoElectrico;
        this.estado = estado == null ? EstadoDispositivo.ACTIVO : estado;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getFabricante() { return fabricante; }
    public double getConsumoElectrico() { return consumoElectrico; }
    public EstadoDispositivo getEstado() { return estado; }
    public void setEstado(EstadoDispositivo estado) { this.estado = estado; }

    @Override
    public int compareTo(Dispositivo o) {
        return Double.compare(this.consumoElectrico, o.consumoElectrico);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Dispositivo d)) return false;
        return this.id == d.id;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return String.format("#%d %s (%s) - %.1fW - %s", id, nombre, fabricante, consumoElectrico, estado);
    }
}

