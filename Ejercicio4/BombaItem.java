package Ejercicio4;

package model;

import controller.Controller;

import java.util.List;

public class BombaItem extends Item {

    public BombaItem(int daño) {
        super("Bomba", daño);
    }

    @Override
    public void usar(Combatiente usuario, Combatiente objetivo, Controller controller) {
        // Bomba da daño a todos los enemigos (área)
        List<Combatiente> enemigos = controller.getEnemigos();
        controller.log(usuario.getNombre() + " lanza una " + nombre + " causando " + puntosEfecto + " a todos los enemigos.");
        for (Combatiente e : enemigos) {
            if (!e.isMuerto()) {
                e.recibirDaño(puntosEfecto);
                controller.log(e.getNombre() + " recibe " + puntosEfecto + " de la " + nombre + ".");
                if (e.isMuerto()) controller.log(e.getNombre() + " ha muerto por la Bomba!");
            }
        }
    }
}

