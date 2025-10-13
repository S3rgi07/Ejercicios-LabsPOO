package Ejercicio4;

/*
 Habilidad especial que usan los enemigos.
*/
public abstract class HabilidadEspecial {
    protected String nombre;
    protected int valor;

    public HabilidadEspecial(String nombre, int valor) {
        this.nombre = nombre;
        this.valor = valor;
    }

    // Origen es el enemigo que la usa; objetivo es generalmente un combatiente del equipo contrario
    public abstract void usar(Combatiente origen, Combatiente objetivo, Controller controller);

    @Override
    public String toString() { return nombre + "(" + valor + ")"; }
}
