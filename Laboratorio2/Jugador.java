/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Laboratorio2;

public class Jugador {

    private int id;
    private int paresDescubiertos;

    public Jugador(int id) {
        this.id = id;
        this.paresDescubiertos = 0;
    }

    public int getId() {
        return id;
    }

    public int getParesDescubiertos() {
        return paresDescubiertos;
    }

    public void setParesDescubiertos(int pares) {
        this.paresDescubiertos = pares;
    }
}
