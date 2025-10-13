package Ejercicio4;

/*
 Furia: aumenta ataque del objetivo (implementado de forma simple y permanente aquí).
 Si quieres duración limitada, habría que llevar un sistema de buffs con tiempo.
*/
public class FuriaItem extends Item {
    public FuriaItem(int aumento) { super("Furia", aumento); }

    @Override
    public void usar(Combatiente usuario, Combatiente objetivo, Controller controller) {
        if (objetivo == null || objetivo.isMuerto()) {
            controller.registrarAccion(usuario.getNombre() + " no puede usar " + nombre + " en objetivo inválido.");
            return;
        }
        objetivo.ataque += puntosEfecto;
        controller.registrarAccion(usuario.getNombre() + " usa " + nombre + " y aumenta ataque de " + objetivo.getNombre() + " en " + puntosEfecto);
    }
}
