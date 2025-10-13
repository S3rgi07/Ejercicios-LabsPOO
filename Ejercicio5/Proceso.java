package Ejercicio5;

/**
 * Proceso.java
 * Clase abstracta base para todos los procesos.
 * 
 * Define atributos comunes como PID y nombre, y declara el método abstracto
 * ejecutar(UIUpdater), que cada tipo de proceso implementa según su comportamiento.
 * 
 * Principios aplicados:
 * - Encapsulación mediante atributos privados y getters/setters.
 * - Polimorfismo: cada subclase implementa ejecutar() con su propia lógica.
 * - Herencia: esta clase es la superclase para todos los tipos de proceso.
 */

public abstract class Proceso {
    private final int pid;
    private String nombre;

    /**
     * Constructor base de la clase Proceso.
     * @param pid identificador único del proceso.
     * @param nombre nombre descriptivo del proceso.
     */
    public Proceso(int pid, String nombre) {
        this.pid = pid;
        this.nombre = nombre;
    }

    // ------------------------
    // Getters y Setters
    // ------------------------

    public int getPid() {
        return pid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre != null) this.nombre = nombre;
    }

    // ------------------------
    // Métodos abstractos
    // ------------------------

    /**
     * Método abstracto que define el comportamiento del proceso.
     * Cada subclase debe implementarlo de manera diferente.
     * 
     * @param updater interfaz que permite enviar mensajes a la interfaz gráfica.
     * @throws InterruptedException cuando se interrumpe la simulación.
     */
    public abstract void ejecutar(UIUpdater updater) throws InterruptedException;

    // ------------------------
    // Sobrescrituras estándar
    // ------------------------

    @Override
    public String toString() {
        // Eliminamos referencia al tipo concreto para mantener el polimorfismo oculto
        return String.format("Proceso{pid=%d, nombre='%s'}", pid, nombre);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Proceso)) return false;
        Proceso other = (Proceso) obj;
        return this.pid == other.pid;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(pid);
    }
}

