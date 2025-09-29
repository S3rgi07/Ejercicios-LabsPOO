package Ejercicio4;

import controller.Controller;

public class Minion extends Enemigo {

    public Minion(int id, String nombre, HabilidadEspecial habilidad) {
        super(id, nombre, 50, 8, habilidad);
    }

    @Override
    public void usarHabilidad(Combatiente objetivo, Controller controller) {
        if (habilidad != null) {
            habilidad.usar(this, objetivo, controller);
        } else {
            atacar(objetivo, controller);
        }
    }
}
