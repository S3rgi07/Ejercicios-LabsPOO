package Ejercicio4;

import controller.Controller;

public class CuraItem extends Item {

    public CuraItem(int cantidad) {
        super("Poción de Cura", cantidad);
    }

    @Override
    public void usar(Combatiente usuario, Combatiente objetivo, Controller controller) {
        if (objetivo.isMuerto()) {
            controller.log(usuario.getNombre() + " intenta usar " + nombre + " en " + objetivo.getNombre() + " pero está muerto.");
            return;
        }
        objetivo.vida += puntosEfecto;
        controller.log(usuario.getNombre() + " usa " + nombre + " y cura " + objetivo.getNombre() + " por " + puntosEfecto + " pts.");
    }
}

