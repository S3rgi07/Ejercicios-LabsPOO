package Ejercicio4;

import controller.Controller;

public class Guerrero extends Combatiente {

    public Guerrero(int id, String nombre) {
        // Guerrero: bastante vida y ataque, poca capacidad de items
        super(id, nombre, 120, 20);
    }

    @Override
    public String mensaje(String contexto) {
        return nombre + " (Guerrero) dice: \"" + contexto + "\"";
    }

    @Override
    public void tomarTurno(Controller controller) {
        // la lógica de interacción del jugador la maneja Controller/View
        controller.jugadorTomarTurno(this);
    }
}
