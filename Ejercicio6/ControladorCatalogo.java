package Ejercicio6;

import java.util.List;

public class ControladorCatalogo {
    private final CatalogoDispositivos catalogo;

    public ControladorCatalogo(CatalogoDispositivos catalogo) {
        this.catalogo = catalogo;
    }

    public List<Dispositivo> listar() {
        return catalogo.listarTodos();
    }

    public List<Dispositivo> buscarPorNombre(String nombre) {
        return catalogo.buscar(nombre);
    }

    public Dispositivo buscarPorId(int id) {
        return catalogo.buscar(id);
    }

    public List<Dispositivo> ordenarPorConsumo() {
        return catalogo.ordenarPorConsumo();
    }
}

