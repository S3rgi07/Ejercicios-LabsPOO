package Ejercicio4;

/*
 Guerrero: más vida y ataque. Se usa para el rol "Guerrero".
*/
public class Guerrero extends Combatiente {

    public Guerrero(int id, String nombre) {
        super(id, nombre, 130, 22); // valores de ejemplo
    }

    @Override
    public String mensaje(String contexto) {
        return nombre + " (Guerrero): " + contexto;
    }

    @Override
    public void tomarTurno(Controller controller) {
        // El Controller maneja la interfaz para que el jugador elija la acción.
        controller.iniciarTurnoJugador(this);
    }
}
