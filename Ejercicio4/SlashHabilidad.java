package Ejercicio4;

/*
 Habilidad de daño extra (slash).
*/
public class SlashHabilidad extends HabilidadEspecial {
    public SlashHabilidad(int extra) { super("Slash", extra); }

    @Override
    public void usar(Combatiente origen, Combatiente objetivo, Controller controller) {
        if (objetivo == null || objetivo.isMuerto()) {
            controller.registrarAccion(origen.getNombre() + " intentó usar " + nombre + " pero objetivo inválido.");
            return;
        }
        int daño = Math.max(0, origen.getAtaque() + valor - objetivo.getDefense());
        objetivo.recibirDaño(daño);
        controller.registrarAccion(origen.getNombre() + " usa " + nombre + " en " + objetivo.getNombre() + " por " + daño);
        if (objetivo.isMuerto()) controller.registrarAccion(objetivo.getNombre() + " fue derrotado.");
    }
}
