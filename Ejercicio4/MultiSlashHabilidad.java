package Ejercicio4;

/*
 Habilidad que hace daño extra al objetivo principal (ideas de área se mantienen simples).
*/
public class MultiSlashHabilidad extends HabilidadEspecial {
    public MultiSlashHabilidad(int daño) { super("MultiSlash", daño); }

    @Override
    public void usar(Combatiente origen, Combatiente objetivo, Controller controller) {
        if (objetivo == null || objetivo.isMuerto()) {
            controller.registrarAccion(origen.getNombre() + " intentó usar " + nombre + " sin objetivo válido.");
            return;
        }
        int daño = Math.max(0, origen.getAtaque() + valor - objetivo.getDefense());
        objetivo.recibirDaño(daño);
        controller.registrarAccion(origen.getNombre() + " usa " + nombre + " en " + objetivo.getNombre() + " por " + daño);
        if (objetivo.isMuerto()) controller.registrarAccion(objetivo.getNombre() + " fue derrotado.");
    }
}
