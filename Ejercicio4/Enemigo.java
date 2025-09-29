package Ejercicio4;

import controller.Controller;

public abstract class Enemigo extends Combatiente {
    protected HabilidadEspecial habilidad;

    public Enemigo(int id, String nombre, int vida, int ataque, HabilidadEspecial habilidad) {
        super(id, nombre, vida, ataque);
        this.habilidad = habilidad;
    }

    public abstract void usarHabilidad(Combatiente objetivo, Controller controller);

    @Override
    public void tomarTurno(Controller controller) {
        // comportamiento por defecto: si tiene habilidad y el jugador está vivo, 50% habilidad 50% atacar
        Combatiente player = controller.getJugador();
        if (player.isMuerto()) {
            pasarTurno(controller);
            return;
        }
        if (this.habilidad != null && Math.random() < 0.5) {
            usarHabilidad(player, controller);
        } else {
            atacar(player, controller);
        }
    }
}
