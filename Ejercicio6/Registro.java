package Ejercicio6;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

public class Registro {
    private final LocalDateTime timestamp;
    private final String evento;
    private final Map<String, Object> datos;

    public Registro(String evento, Map<String, Object> datos) {
        this.timestamp = LocalDateTime.now();
        this.evento = evento;
        this.datos = (datos == null) ? Collections.emptyMap() : Collections.unmodifiableMap(datos);
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public String getEvento() { return evento; }
    public Map<String, Object> getDatos() { return datos; }

    @Override
    public String toString() {
        return String.format("%s | %s | %s", timestamp, evento, datos);
    }
}

