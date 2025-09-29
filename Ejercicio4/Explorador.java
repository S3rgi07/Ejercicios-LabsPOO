package Ejercicio4;

import controller.Controller;

public class Explorador extends Combatiente {

    public Explorador(int id, String nombre) {
        // Explorador: vida y ataque normales, más items
        super(id, nombre, 90, 14);
    }

    @Override
    public String mensaje(String contexto) {
        return nombre + " (Explorador) dice: \"" + contexto + "\"";
    }

    @Override
    public void tomarTurno(Controller controller) {
        controller.jugadorTomarTurno(this);
    }
}
