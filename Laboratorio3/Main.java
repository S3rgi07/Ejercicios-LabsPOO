import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Siempre lanzar GUI en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            Controller controller = new Controller();
            View vista = new View(controller);
            vista.setVisible(true);
        });
    }
}
