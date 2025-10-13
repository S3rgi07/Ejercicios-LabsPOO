package Ejercicio4;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/*
 Controller: mantiene la lógica original (consola) y además soporta GUI.
 He mantenido los métodos originales y añadí los hooks que usa la GUI.
 Comentarios sencillos para que sea fácil de entender.
*/
public class Controller {

    private ViewSwing view; // referencia a la GUI (si se usa)
    private Guerrero guerrero;
    private Explorador explorador;
    private List<Combatiente> enemigos = new ArrayList<>();
    private int nextId = 1;
    private int modePlayers = 1; // 1 o 2 jugadores
    private List<Combatiente> turnoOrden = new ArrayList<>();
    private int turnoIndex = 0;

    // Datos para CSV (puntos UP)
    private List<Item> itemsDisponibles = new ArrayList<>();
    private List<String[]> enemigosDisponibles = new ArrayList<>();

    public Controller(int modePlayers) {
        this.modePlayers = modePlayers;
    }

    // ==================== Inicio de partida ====================
    public void start() {
        // Cargar CSV (si existe) — si no, se usan predeterminados
        cargarItemsDesdeCSV("items.csv");
        cargarEnemigosDesdeCSV("enemigos.csv");

        // Crear equipo del jugador (GUERRERO + EXPLORADOR)
        guerrero = new Guerrero(nextId++, "Guerrero");
        explorador = new Explorador(nextId++, "Explorador");

        // Asignar ítems básicos (desde CSV si se cargaron)
        asignarItemsIniciales();

        // Crear enemigos aleatorios
        crearEnemigosAleatorios();

        // Preparar orden de turnos (original: jugador primero)
        turnoOrden.clear();
        turnoOrden.add(guerrero);
        turnoOrden.add(explorador);
        turnoOrden.addAll(enemigos);
        turnoIndex = 0;

        // Lanzar GUI si se desea (mantengo soporte de consola también)
        SwingUtilities.invokeLater(() -> {
            view = new ViewSwing(this);
            view.mostrarEstado(guerrero, explorador, enemigos);
            registrarAccion(guerrero.mensaje("Listo para la batalla."));
            registrarAccion(explorador.mensaje("Preparado."));
            for (Combatiente e : enemigos) registrarAccion(e.mensaje("¡A pelear!"));
            // empezar con el primer turno: si es enemigo actúa, si es jugador, iniciar turno
            Combatiente actual = turnoOrden.get(turnoIndex);
            if (enemigos.contains(actual)) {
                actual.tomarTurno(this);
            } else {
                iniciarTurnoJugador(actual);
            }
        });
    }

    // ==================== CSV helpers ====================
    private void cargarItemsDesdeCSV(String archivo) {
        itemsDisponibles.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String line;
            br.readLine(); // saltar cabecera
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length < 3) continue;
                String tipo = p[1].trim();
                int val = Integer.parseInt(p[2].trim());
                switch (tipo) {
                    case "Cura" -> itemsDisponibles.add(new CuraItem(val));
                    case "Furia" -> itemsDisponibles.add(new FuriaItem(val));
                    case "Bomba" -> itemsDisponibles.add(new BombaItem(val));
                }
            }
            registrarAccion("Items cargados desde CSV.");
        } catch (IOException ex) {
            // valores por defecto
            itemsDisponibles.add(new CuraItem(30));
            itemsDisponibles.add(new FuriaItem(4));
            itemsDisponibles.add(new BombaItem(15));
            registrarAccion("No se encontró items.csv; se usaron valores por defecto.");
        }
    }

    private void cargarEnemigosDesdeCSV(String archivo) {
        enemigosDisponibles.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String line;
            br.readLine(); // cabecera
            while ((line = br.readLine()) != null) {
                enemigosDisponibles.add(line.split(","));
            }
            registrarAccion("Enemigos cargados desde CSV.");
        } catch (IOException ex) {
            // valores por defecto
            enemigosDisponibles.add(new String[]{"Goblin", "Minion", "50", "8", "8"});
            enemigosDisponibles.add(new String[]{"Orco", "MegaMinion", "80", "12", "4"});
            enemigosDisponibles.add(new String[]{"Orc Warlord", "Jefe", "140", "18", "6"});
            registrarAccion("No se encontró enemigos.csv; se usaron valores por defecto.");
        }
    }

    private void asignarItemsIniciales() {
        if (itemsDisponibles.isEmpty()) return;
        // Guerrero recibe 2 items (clonados)
        guerrero.agregarItem(clonarItem(itemsDisponibles.get(0)));
        if (itemsDisponibles.size() > 1) guerrero.agregarItem(clonarItem(itemsDisponibles.get(1)));
        // Explorador recibe hasta 3
        for (int i = 0; i < Math.min(3, itemsDisponibles.size()); i++) {
            explorador.agregarItem(clonarItem(itemsDisponibles.get(i)));
        }
    }

    private Item clonarItem(Item base) {
        if (base instanceof CuraItem) return new CuraItem(base.puntosEfecto);
        if (base instanceof FuriaItem) return new FuriaItem(base.puntosEfecto);
        if (base instanceof BombaItem) return new BombaItem(base.puntosEfecto);
        return null;
    }

    private void crearEnemigosAleatorios() {
        Random r = new Random();
        int cantidad = 1 + r.nextInt(3);
        for (int i = 0; i < cantidad; i++) {
            String[] d = enemigosDisponibles.get(r.nextInt(enemigosDisponibles.size()));
            String nombre = d[0];
            String tipo = d[1];
            int vida = Integer.parseInt(d[2]);
            int atk = Integer.parseInt(d[3]);
            int val = Integer.parseInt(d[4]);
            switch (tipo) {
                case "Minion" -> enemigos.add(new Minion(nextId++, nombre, new CurarHabilidad(val)));
                case "MegaMinion" -> enemigos.add(new MegaMinion(nextId++, nombre, new SlashHabilidad(val)));
                case "Jefe" -> enemigos.add(new Jefe(nextId++, nombre, vida, atk, 4,
                        new SlashHabilidad(val), new MultiSlashHabilidad(val + 2)));
            }
        }
    }

    // ==================== Acciones y flujo (lógica original) ====================
    public void registrarAccion(String texto) {
        if (view != null) view.appendLog(texto);
        else System.out.println(texto);
    }

    public List<Combatiente> getEnemigos() { return enemigos; }
    public Guerrero getGuerrero() { return guerrero; }
    public Explorador getExplorador() { return explorador; }

    // Original: devuelve un solo objetivo del equipo (por ejemplo, primero vivo).
    // Mantengo este helper con el nombre que usan las clases Enemigo/Jefe.
    public Combatiente getEquipoJugadorVivoAsistido() {
        if (!guerrero.isMuerto()) return guerrero;
        if (!explorador.isMuerto()) return explorador;
        return null;
    }

    // Devuelve lista de vivos (útil para GUI)
    public List<Combatiente> getEnemigosVivos() {
        List<Combatiente> vivos = new ArrayList<>();
        for (Combatiente e : enemigos) if (!e.isMuerto()) vivos.add(e);
        return vivos;
    }

    // Iniciar turno para jugador: mantiene la lógica original (se muestra estado y se espera la acción del jugador).
    public void iniciarTurnoJugador(Combatiente jugador) {
        // Refrescar vista/estado
        if (view != null) {
            view.mostrarEstado(guerrero, explorador, enemigos);
            registrarAccion("Turno de " + jugador.getNombre());
            // Habilitar acciones en la GUI para este jugador
            view.habilitarAccionesJugador(jugador);
        } else {
            // En versión consola (original) aquí se pediría la acción por Scanner.
            registrarAccion("Turno de " + jugador.getNombre() + " (modo consola).");
        }
    }

    // Métodos que ejecuta la GUI cuando el jugador presiona botones:
    public void accionAtacar(Combatiente actor, int objetivoIdxEnVivos) {
        List<Combatiente> vivos = getEnemigosVivos();
        if (vivos.isEmpty()) return;
        if (objetivoIdxEnVivos < 0 || objetivoIdxEnVivos >= vivos.size()) return;
        actor.atacar(vivos.get(objetivoIdxEnVivos), this);
        if (checkFin()) return;
        avanzarTurnoOriginal();
    }

    public void accionPasar(Combatiente actor) {
        actor.pasarTurno(this);
        if (checkFin()) return;
        avanzarTurnoOriginal();
    }

    public void accionUsarItem(Combatiente actor, int idxItem, int idxObjetivoEnVivos, boolean enSelf) {
        if (idxItem < 0 || idxItem >= actor.getItems().size()) return;
        Item it = actor.getItems().get(idxItem);
        Combatiente objetivo = enSelf ? actor : null;
        if (!enSelf) {
            List<Combatiente> vivos = getEnemigosVivos();
            if (vivos.isEmpty()) return;
            if (idxObjetivoEnVivos < 0 || idxObjetivoEnVivos >= vivos.size()) return;
            objetivo = vivos.get(idxObjetivoEnVivos);
        }
        it.usar(actor, objetivo, this);
        actor.getItems().remove(idxItem);
        if (checkFin()) return;
        avanzarTurnoOriginal();
    }

    // Avanza el orden de turnos (original)
    private void avanzarTurnoOriginal() {
        // Avanza index hasta un combatiente vivo
        do {
            turnoIndex = (turnoIndex + 1) % turnoOrden.size();
        } while (turnoOrden.get(turnoIndex).isMuerto());

        Combatiente actual = turnoOrden.get(turnoIndex);
        if (enemigos.contains(actual)) {
            // enemigo actúa automáticamente
            actual.tomarTurno(this);
            if (checkFin()) return;
            // una vez actúa el enemigo, avanzamos de nuevo
            avanzarTurnoOriginal();
        } else {
            // si es jugador, iniciamos su turno (esto habilita botones en GUI)
            iniciarTurnoJugador(actual);
        }
    }

    // Comprobaciones de fin
    private boolean checkFin() {
        if (checkVictoria()) {
            registrarAccion("¡Victoria! Todos los enemigos han sido derrotados.");
            if (view != null) view.disableAllActions();
            return true;
        }
        if (checkDerrota()) {
            registrarAccion("Derrota... ambos combatientes murieron.");
            if (view != null) view.disableAllActions();
            return true;
        }
        return false;
    }

    private boolean checkVictoria() {
        for (Combatiente e : enemigos) if (!e.isMuerto()) return false;
        return true;
    }

    private boolean checkDerrota() {
        return guerrero.isMuerto() && explorador.isMuerto();
    }

    // ==================== Métodos auxiliares para la GUI ====================

    // Devuelve la lista de miembros del equipo que están vivos (para mostrar en GUI)
    public List<Combatiente> getEquipoJugadorVivo() {
        List<Combatiente> vivos = new ArrayList<>();
        if (!guerrero.isMuerto()) vivos.add(guerrero);
        if (!explorador.isMuerto()) vivos.add(explorador);
        return vivos;
    }

    // Indica quién tiene el turno (para la vista)
    public Combatiente getCombatienteConTurno() {
        return turnoOrden.get(turnoIndex);
    }

    // Actualiza la vista indicando de quién es el turno
    public void actualizarTurnoEnVista() {
        if (view != null) {
            Combatiente actual = turnoOrden.get(turnoIndex);
            view.marcarTurno(actual.getNombre(), actual == guerrero, actual == explorador, modePlayers);
        }
    }
}
