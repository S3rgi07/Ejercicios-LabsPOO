package Ejercicio4;

/*
 Clase base para enemigos. Define usarHabilidad y cómo toman turno.
 Mantiene la lógica original: 50% usar habilidad, 50% atacar (los jefes sobreescriben).
*/
public abstract class Enemigo extends Combatiente {
    protected HabilidadEspecial habilidad;

    public Enemigo(int id, String nombre, int vida, int ataque, HabilidadEspecial habilidad) {
        super(id, nombre, vida, ataque);
        this.habilidad = habilidad;
    }

    // Cada enemigo implementa su habilidad concreta
    public abstract void usarHabilidad(Combatiente objetivo, Controller controller);

    @Override
    public void tomarTurno(Controller controller) {
        // Comportamiento por defecto: si hay habilidad, 50% usarla, si no atacar
        Combatiente objetivo = controller.getEquipoJugadorVivoAsistido(); // método que devuelve 1 target (original)
        if (objetivo == null) {
            pasarTurno(controller);
            return;
        }
        if (habilidad != null && Math.random() < 0.5) {
            usarHabilidad(objetivo, controller);
        } else {
            atacar(objetivo, controller);
        }
    }
}
