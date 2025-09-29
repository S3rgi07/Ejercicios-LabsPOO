package Ejercicio4;

import controller.Controller;

public class Jefe extends Enemigo {
    private HabilidadEspecial habilidadExtra;
    private int defensa;

    public Jefe(int id, String nombre, int vida, int ataque, int defensa, HabilidadEspecial habilidad, HabilidadEspecial habilidadExtra) {
        super(id, nombre, vida, ataque, habilidad);
        this.habilidadExtra = habilidadExtra;
        this.defensa = defensa;
    }

    @Override
    public int getDefense() {
        return defensa;
    }

    @Override
    public void usarHabilidad(Combatiente objetivo, Controller controller) {
        // Jefe tiene mayor probabilidad de usar habilidad, puede usar la extra a veces
        double r = Math.random();
        if (r < 0.5 && habilidad != null) {
            habilidad.usar(this, objetivo, controller);
        } else if (habilidadExtra != null) {
            habilidadExtra.usar(this, objetivo, controller);
        } else {
            atacar(objetivo, controller);
        }
    }

    @Override
    public void tomarTurno(Controller controller) {
        // Jefe más agresivo: 70% habilidades
        Combatiente player = controller.getJugador();
        if (player.isMuerto()) {
            pasarTurno(controller);
            return;
        }
        if (Math.random() < 0.7) {
            usarHabilidad(player, controller);
        } else {
            atacar(player, controller);
        }
    }
}
