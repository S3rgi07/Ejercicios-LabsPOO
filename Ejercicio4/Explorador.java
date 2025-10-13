package Ejercicio4;

/*
 Explorador: vida/ataque moderados, más items disponibles.
*/
public class Explorador extends Combatiente {
    public Explorador(int id, String nombre) {
        super(id, nombre, 95, 14);
    }

    @Override
    public String mensaje(String contexto) {
        return nombre + " (Explorador): " + contexto;
    }

    @Override
    public void tomarTurno(Controller controller) {
        controller.iniciarTurnoJugador(this);
    }
}
