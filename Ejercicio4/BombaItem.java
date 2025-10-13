package Ejercicio4;

import java.util.List;

/*
 Bomba: daño de área a todos los enemigos vivos.
*/
public class BombaItem extends Item {
    public BombaItem(int daño) { super("Bomba", daño); }

    @Override
    public void usar(Combatiente usuario, Combatiente objetivo, Controller controller) {
        // objetivo ignorado; afecta a todos los enemigos
        List<Combatiente> enemigos = controller.getEnemigos();
        controller.registrarAccion(usuario.getNombre() + " lanza una " + nombre + " causando " + puntosEfecto + " a todos los enemigos.");
        for (Combatiente e : enemigos) {
            if (!e.isMuerto()) {
                e.recibirDaño(puntosEfecto);
                controller.registrarAccion(e.getNombre() + " recibe " + puntosEfecto + " de la Bomba.");
                if (e.isMuerto()) controller.registrarAccion(e.getNombre() + " fue derrotado por la Bomba.");
            }
        }
    }
}
