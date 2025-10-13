package Ejercicio4;

/*
 Habilidad que cura a un objetivo (puede usarse para que el enemigo cure a otro o a sí mismo).
*/
public class CurarHabilidad extends HabilidadEspecial {
    public CurarHabilidad(int cantidad) { super("Curar", cantidad); }

    @Override
    public void usar(Combatiente origen, Combatiente objetivo, Controller controller) {
        if (objetivo == null || objetivo.isMuerto()) {
            controller.registrarAccion(origen.getNombre() + " intentó usar " + nombre + " pero objetivo inválido.");
            return;
        }
        objetivo.vida += valor;
        controller.registrarAccion(origen.getNombre() + " usa " + nombre + " y cura a " + objetivo.getNombre() + " por " + valor);
    }
}
