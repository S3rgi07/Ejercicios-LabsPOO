package Ejercicio6;

import java.util.*;

public class CatalogoDispositivos {
    private final List<Dispositivo> dispositivos = new ArrayList<>();

    public CatalogoDispositivos() {
        inicializarDatos();
    }

    public void inicializarDatos() {
        dispositivos.add(new SensorSuelo(1, "Sensor A", "AgroTech", 15, 20, "Arcilloso"));
        dispositivos.add(new EstacionMeteorologica(2, "Estación Norte", "Metrix", 30, true, true));
        dispositivos.add(new ValvulaInteligente(3, "Válvula 1", "HydroX", 10, "Sector A"));
        dispositivos.add(new DronRiego(4, "Dron Verde", "FlyAgro", 50, 20, 100));
        dispositivos.add(new DronCamara(5, "Dron Vision", "SkyEyes", 45, "4K", true));
        dispositivos.add(new SensorSuelo(6, "Sensor B", "AgroTech", 12, 25, "Arenoso"));
        dispositivos.add(new ValvulaInteligente(7, "Válvula 2", "HydroX", 9, "Sector B"));
        dispositivos.add(new DronRiego(8, "Dron Azul", "FlyAgro", 55, 25, 80));
        dispositivos.add(new EstacionMeteorologica(9, "Estación Sur", "Metrix", 32, false, true));
        dispositivos.add(new ControladorValvulasLegacy(10, "Controlador 80s", "RetroHydro", 5, "R-2080"));
    }

    public List<Dispositivo> listarTodos() {
        return List.copyOf(dispositivos);
    }

    public Dispositivo buscar(int id) {
        return dispositivos.stream()
                .filter(d -> d.getId() == id)
                .findFirst().orElse(null);
    }

    public List<Dispositivo> buscar(String nombre) {
        return dispositivos.stream()
                .filter(d -> d.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .toList();
    }

    public List<Dispositivo> ordenarPorConsumo() {
        return dispositivos.stream().sorted().toList();
    }

    public void agregar(Dispositivo d) {
        dispositivos.add(d);
    }
}

