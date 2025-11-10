package Ejercicio6;

import java.util.Map;

public interface IAccionable {
    void accionar(Accion accion);
    void accionar(Accion accion, Map<String, Object> parametros); // overloading
}

