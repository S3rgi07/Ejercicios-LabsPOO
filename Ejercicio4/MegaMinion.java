package Ejercicio4;

/*
 MegaMinion: versión más fuerte de Minion.
*/
public class MegaMinion extends Enemigo {
    public MegaMinion(int id, String nombre, HabilidadEspecial hab) {
        super(id, nombre, 80, 12, hab);
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
