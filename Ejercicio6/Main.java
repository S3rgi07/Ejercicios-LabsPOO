package Ejercicio6;

public class Main {
    public static void main(String[] args) {
        CatalogoDispositivos catalogo = new CatalogoDispositivos();
        ControladorCatalogo controlador = new ControladorCatalogo(catalogo);
        Vista vista = new Vista(controlador);
        vista.iniciar();
    }
}

