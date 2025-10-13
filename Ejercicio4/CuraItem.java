package Ejercicio4;

/*
 Poción de cura simple: restaura vida al objetivo.
*/
public class CuraItem extends Item {
    public CuraItem(int cantidad) { super("Poción de Cura", cantidad); }

    @Override
    public void usar(Combatiente usuario, Combatiente objetivo, Controller controller) {
        if (objetivo == null || objetivo.isMuerto()) {
            controller.registrarAccion(usuario.getNombre() + " intentó usar " + nombre + " pero el objetivo no es válido.");
            return;
        }
        objetivo.vida += puntosEfecto;
        controller.registrarAccion(usuario.getNombre() + " usa " + nombre + " y cura a " + objetivo.getNombre() + " por " + puntosEfecto);
    }
}
