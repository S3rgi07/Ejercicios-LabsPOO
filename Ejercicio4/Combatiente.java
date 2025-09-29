package Ejercicio4;

import java.util.ArrayList;
import java.util.List;

import controller.Controller;

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

    // Mensaje al iniciar batalla / morir / ganar
    public String mensaje(String contexto) {
        return nombre + " dice: \"" + contexto + "\"";
    }

    // cada combatiente debe tomar turno
    public abstract void tomarTurno(Controller controller);

    public void pasarTurno(Controller controller) {
        controller.log(nombre + " pasó su turno.");
    }

    public void atacar(Combatiente objetivo, Controller controller) {
        if (this.isMuerto() || objetivo.isMuerto()) {
            controller.log(this.nombre + " no puede atacar (muerto o objetivo muerto).");
            return;
        }
        int daño = Math.max(0, this.ataque - objetivo.getDefense());
        objetivo.recibirDaño(daño);
        controller.log(this.nombre + " ataca a " + objetivo.nombre + " por " + daño + " pts.");
        if (objetivo.isMuerto()) {
            controller.log(objetivo.nombre + " ha muerto!");
            controller.log(objetivo.mensaje("He sido derrotado..."));
        }
    }

    public void recibirDaño(int daño) {
        this.vida -= daño;
        if (this.vida < 0) this.vida = 0;
    }

    public boolean isMuerto() {
        return vida <= 0;
    }

    public int getDefense() {
        // por defecto sin defensa; jefes pueden sobreescribir
        return 0;
    }

    public void agregarItem(Item i) {
        items.add(i);
    }

    public List<Item> getItems() {
        return items;
    }

    public String listarItems() {
        if (items.isEmpty()) return "Sin items";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            sb.append("[").append(i).append("] ").append(items.get(i).getNombre()).append(" ");
        }
        return sb.toString();
    }

    // setters & getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public int getVida() { return vida; }
    public int getAtaque() { return ataque; }

    @Override
    public String toString() {
        return String.format("%s (HP:%d, ATK:%d)", nombre, vida, ataque);
    }
}

