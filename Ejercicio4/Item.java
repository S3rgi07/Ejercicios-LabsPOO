package Ejercicio4;

import controller.Controller;

public abstract class Item {
    protected int puntosEfecto;
    protected String nombre;

    public Item(String nombre, int puntosEfecto) {
        this.nombre = nombre;
        this.puntosEfecto = puntosEfecto;
    }

    public String getNombre() { return nombre; }

    public abstract void usar(Combatiente usuario, Combatiente objetivo, Controller controller);

    @Override
    public String toString() {
        return nombre + " (efecto: " + puntosEfecto + ")";
    }
}

