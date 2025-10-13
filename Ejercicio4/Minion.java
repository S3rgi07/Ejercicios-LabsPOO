package Ejercicio4;

/*
 Minion: enemigo sencillo que usa su habilidad (si la tiene) o ataca.
*/
public class Minion extends Enemigo {
    public Minion(int id, String nombre, HabilidadEspecial hab) {
        super(id, nombre, 50, 8, hab);
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
