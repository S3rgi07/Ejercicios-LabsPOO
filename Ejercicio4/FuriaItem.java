package Ejercicio4;

import controller.Controller;

public class FuriaItem extends Item {

    public FuriaItem(int aumento) {
        super("Furia", aumento);
    }

    @Override
    public void usar(Combatiente usuario, Combatiente objetivo, Controller controller) {
        // aumenta ataque del objetivo por un turno: implementamos como aumento permanente sencillo para demo
        objetivo.ataque += puntosEfecto;
        controller.log(usuario.getNombre() + " usa " + nombre + " sobre " + objetivo.getNombre() + ". Ataque + " + puntosEfecto + " por turno.");
        // Nota: para implementación más fiel, habría que trackear duración y revertir después.
    }
}

}
