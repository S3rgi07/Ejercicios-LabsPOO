/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package laboratorio2;

import java.util.*;

public class Tablero {

    private int n;
    private Ficha[][] fichas;

    public Tablero(int n) throws Exception {
        if (n < 2 || n > 8 || (n * n) % 2 != 0) {
            throw new Exception("Dimensiones inválidas: deben ser entre 2 y 8, y pares.");
        }
        this.n = n;
        this.fichas = new Ficha[n][n];
        initTablero();
    }

    public void initTablero() {
        colocarFichas();
    }

    public void colocarFichas() {
        String[] simbolos = {"🍎", "🍌", "🍇", "🍓", "🍒", "🍍", "🥝", "🍑", "🍉", "🥥", "🍋", "🍊"};
        ArrayList<String> pool = new ArrayList<>();

        int total = n * n;
        for (int i = 0; i < total / 2; i++) {
            pool.add(simbolos[i % simbolos.length]);
            pool.add(simbolos[i % simbolos.length]);
        }
        Collections.shuffle(pool);

        int k = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                fichas[i][j] = new Ficha(pool.get(k++));
            }
        }
    }

    public int getN() {
        return n;
    }

    public Ficha getFicha(int i, int j) {
        return fichas[i][j];
    }

    public boolean todasEncontradas() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (!fichas[i][j].estaEncontrada()) {
                    return false;
                }
            }
        }
        return true;
    }
}
