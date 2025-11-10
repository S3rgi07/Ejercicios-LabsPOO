package Ejercicio6;

import java.util.Collections;
import java.util.Map;

public class Accion {
    private final AccionTipo tipo;
    private final Map<String, Object> parametros;

    public Accion(AccionTipo tipo) {
        this(tipo, Collections.emptyMap());
    }

    public Accion(AccionTipo tipo, Map<String, Object> parametros) {
        this.tipo = tipo;
        this.parametros = (parametros == null) ? Collections.emptyMap() : Collections.unmodifiableMap(parametros);
    }

    public AccionTipo getTipo() { return tipo; }
    public Map<String, Object> getParametros() { return parametros; }

    @Override
    public String toString() {
        return String.format("%s %s", tipo, parametros);
    }
}

