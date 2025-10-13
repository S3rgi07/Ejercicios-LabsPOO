package Ejercicio4;

import java.util.ArrayList;
import java.util.List;

/*
 Clase base para todos los personajes (jugador y enemigos).
 Comentarios simples para explicar los métodos más importantes.
*/
public abstract class Combatiente {
    protected int id;
    protected String nombre;
    protected int vida;
    protected int ataque;
    protected List<Item> items = new ArrayList<>();

    public Combatiente(int id, String nombre, int vida, int ataque) {
        this.id = id;
        this.nombre = nombre;
        this.vida = vida;
        this.ataque = ataque;
    }

    // Mensaje simple para inicio, victoria o derrota
    public String mensaje(String contexto) {
        return nombre + ": " + contexto;
    }

    // Cada tipo de combatiente define cómo toma su turno
    public abstract void tomarTurno(Controller controller);

    // Pasar turno sin hacer nada
    public void pasarTurno(Controller controller) {
        controller.registrarAccion(nombre + " pasó su turno.");
    }

    // Atacar a otro combatiente. Se respeta defensa por sobreescritura.
    public void atacar(Combatiente objetivo, Controller controller) {
        if (this.isMuerto() || objetivo == null || objetivo.isMuerto()) {
            controller.registrarAccion(nombre + " no puede atacar (objetivo inválido o muerto).");
            return;
        }
        int danio = Math.max(0, this.ataque - objetivo.getDefense());
        objetivo.recibirDaño(danio);
        controller.registrarAccion(nombre + " ataca a " + objetivo.getNombre() + " por " + danio + " puntos.");
        if (objetivo.isMuerto()) {
            controller.registrarAccion(objetivo.getNombre() + " fue derrotado.");
        }
    }

    // Restar vida al recibir daño
    public void recibirDaño(int danio) {
        this.vida -= danio;
        if (this.vida < 0) this.vida = 0;
    }

    public boolean isMuerto() { return vida <= 0; }

    // Defensa por defecto (0). Jefes pueden tener defensa mayor.
    public int getDefense() { return 0; }

    public void agregarItem(Item i) { items.add(i); }
    public List<Item> getItems() { return items; }

    public String listarItems() {
        if (items.isEmpty()) return "Sin items";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            sb.append("[").append(i).append("] ").append(items.get(i).getNombre()).append(" ");
        }
        return sb.toString();
    }

    // Getters simples
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public int getVida() { return vida; }
    public int getAtaque() { return ataque; }

    @Override
    public String toString() {
        return nombre + " (HP:" + vida + ", ATK:" + ataque + ")";
    }
}
