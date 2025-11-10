package Ejercicio6;

import java.time.LocalDateTime;

public class Lectura {
    private final String tipo;
    private final double valor;
    private final Unidad unidad;
    private final LocalDateTime timestamp;

    public Lectura(String tipo, double valor, Unidad unidad) {
        this.tipo = tipo;
        this.valor = valor;
        this.unidad = unidad;
        this.timestamp = LocalDateTime.now();
    }

    public String getTipo() { return tipo; }
    public double getValor() { return valor; }
    public Unidad getUnidad() { return unidad; }
    public LocalDateTime getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return String.format("[%s] %.2f %s @ %s", tipo, valor, unidad, timestamp);
    }
}

