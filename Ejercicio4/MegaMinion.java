package Ejercicio4;

import controller.Controller;

public class MegaMinion extends Enemigo {

    public MegaMinion(int id, String nombre, HabilidadEspecial habilidad) {
        super(id, nombre, 80, 12, habilidad);
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

