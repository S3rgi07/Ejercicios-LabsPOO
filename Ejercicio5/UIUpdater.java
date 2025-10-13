package Ejercicio5;

/**
 * UIUpdater.java
 * Interfaz simple que permite al modelo/procesos enviar texto a la vista
 * sin acoplarse a Swing directamente (parte del patrón MVC).
 */

public interface UIUpdater {
    void appendLine(String line);
}
