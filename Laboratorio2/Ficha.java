/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package laboratorio2;

public class Ficha {

    private String simbolo;
    private boolean escogida;
    private boolean encontrada;

    public Ficha(String simbolo) {
        this.simbolo = simbolo;
        this.escogida = false;
        this.encontrada = false;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public boolean estaEscogida() {
        return escogida;
    }

    public void escoger() {
        this.escogida = true;
    }

    public boolean estaEncontrada() {
        return encontrada;
    }

    public void encontrada() {
        this.encontrada = true;
    }

    public void ocultar() {
        this.escogida = false;
    }

    @Override
    public String toString() {
        return (escogida || encontrada) ? simbolo : "❓";
    }
}
