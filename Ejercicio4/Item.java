package Ejercicio4;

/*
 Clase base para items que el jugador puede usar.
*/
public abstract class Item {
    protected int puntosEfecto;
    protected String nombre;

    public Item(String nombre, int puntosEfecto) {
        this.nombre = nombre;
        this.puntosEfecto = puntosEfecto;
    }

    public String getNombre() { return nombre; }

    // Acción del item: usuario es quien lo usa, objetivo puede ser null según item
    public abstract void usar(Combatiente usuario, Combatiente objetivo, Controller controller);

    @Override
    public String toString() {
        return nombre + " (efecto:" + puntosEfecto + ")";
    }
}
