package Ejercicio4;

/*
 Jefe: tiene defensa extra y una habilidad adicional.
*/
public class Jefe extends Enemigo {
    private HabilidadEspecial habilidadExtra;
    private int defensa;

    public Jefe(int id, String nombre, int vida, int ataque, int defensa,
                HabilidadEspecial hab, HabilidadEspecial habExtra) {
        super(id, nombre, vida, ataque, hab);
        this.habilidadExtra = habExtra;
        this.defensa = defensa;
    }

    @Override
    public int getDefense() {
        return defensa;
    }

    @Override
    public void usarHabilidad(Combatiente objetivo, Controller controller) {
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
        // Jefe tiene más probabilidad de usar habilidades (70%)
        Combatiente objetivo = controller.getEquipoJugadorVivoAsistido();
        if (objetivo == null) {
            pasarTurno(controller);
            return;
        }
        if (Math.random() < 0.7) {
            usarHabilidad(objetivo, controller);
        } else {
            atacar(objetivo, controller);
        }
    }
}
