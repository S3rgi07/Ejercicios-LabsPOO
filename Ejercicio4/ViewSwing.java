package Ejercicio4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/*
 Vista en Swing. Interfaz sencilla pero funcional:
 - Muestra estado del Guerrero, Explorador y enemigos.
 - Botones para Atacar, Pasar y Usar ítem.
 - ComboBox para elegir objetivo entre enemigos vivos.
 - Log en JTextArea (se añaden líneas nuevas).
*/
public class ViewSwing extends JFrame {

    private Controller controller;
    private JLabel lblGuerrero, lblExplorador;
    private DefaultListModel<String> listaEnemigosModel;
    private JList<String> listaEnemigos;
    private JComboBox<String> comboObjetivos;
    private JComboBox<String> comboItems;
    private JButton btnAtacar, btnPasar, btnUsarItem;
    private JTextArea logArea;
    private JLabel lblTurnoInfo;

    public ViewSwing(Controller controller) {
        this.controller = controller;
        setTitle("Juego de Batalla - GUI");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Inicializa componentes y layout
    private void initComponents() {
        lblGuerrero = new JLabel();
        lblExplorador = new JLabel();
        lblTurnoInfo = new JLabel("Turno: -");

        listaEnemigosModel = new DefaultListModel<>();
        listaEnemigos = new JList<>(listaEnemigosModel);
        comboObjetivos = new JComboBox<>();
        comboItems = new JComboBox<>();

        btnAtacar = new JButton("Atacar");
        btnPasar = new JButton("Pasar");
        btnUsarItem = new JButton("Usar Ítem");

        logArea = new JTextArea(8, 40);
        logArea.setEditable(false);
        JScrollPane logPane = new JScrollPane(logArea);

        // Panel izquierdo: estado
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.add(lblGuerrero);
        left.add(Box.createVerticalStrut(6));
        left.add(lblExplorador);
        left.add(Box.createVerticalStrut(6));
        left.add(new JLabel("Enemigos:"));
        left.add(new JScrollPane(listaEnemigos));
        left.add(Box.createVerticalStrut(6));
        left.add(new JLabel("Seleccionar objetivo:"));
        left.add(comboObjetivos);

        // Panel derecho: acciones y log
        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.add(lblTurnoInfo);
        right.add(Box.createVerticalStrut(6));
        right.add(new JLabel("Ítems (seleccione en quién usar si aplica):"));
        right.add(comboItems);
        right.add(Box.createVerticalStrut(6));

        JPanel botones = new JPanel();
        botones.add(btnAtacar);
        botones.add(btnPasar);
        botones.add(btnUsarItem);
        right.add(botones);
        right.add(Box.createVerticalStrut(10));
        right.add(new JLabel("Registro de acciones:"));
        right.add(logPane);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(left, BorderLayout.WEST);
        getContentPane().add(right, BorderLayout.CENTER);

        // Acciones de botones
        btnAtacar.addActionListener(e -> onAtacar());
        btnPasar.addActionListener(e -> onPasar());
        btnUsarItem.addActionListener(e -> onUsarItem());
    }

    // Método que la Controller usa para mostrar estado actual
    public void mostrarEstado(Guerrero g, Explorador ex, List<Combatiente> enemigos) {
        lblGuerrero.setText(g.toString() + " | Items: " + g.listarItems());
        lblExplorador.setText(ex.toString() + " | Items: " + ex.listarItems());

        // rellenar lista de enemigos y combo de objetivos
        listaEnemigosModel.clear();
        comboObjetivos.removeAllItems();
        for (Combatiente e : enemigos) {
            String line = e.toString() + (e.isMuerto() ? " [MUERTO]" : "");
            listaEnemigosModel.addElement(line);
            if (!e.isMuerto()) comboObjetivos.addItem(e.getNombre());
        }

        // rellenar items según quién tiene el turno
        actualizarItemsSegunTurno();
    }

    // Añade una línea al log (Controller llama registrarAccion que llega aquí)
    public void appendLog(String linea) {
        logArea.append(linea + "\n");
        // autoscroll
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    // Indica en la vista a quién le pertenece el turno y habilita botones apropiados
    // turnoGuerrero y turnoExplorador indican si es turno de esos personajes
    public void marcarTurno(String nombreTurno, boolean turnoGuerrero, boolean turnoExplorador, int modoJugadores) {
        lblTurnoInfo.setText("Turno: " + nombreTurno);
        // Si es turno del guerrero, las acciones las hace jugador 1.
        // Si es turno del explorador y modo 2, las hace jugador 2; si modo 1, las hace el mismo jugador.
        boolean habilitar = !((nombreTurno.equals("Guerrero") && turnoGuerrero && false)); // placeholder
        // Habilitar botones sólo si el combatiente no está muerto
        Combatiente current = controller.getCombatienteConTurno();
        boolean vivo = current != null && !current.isMuerto();
        btnAtacar.setEnabled(vivo && comboObjetivos.getItemCount() > 0);
        btnPasar.setEnabled(vivo);
        comboItems.removeAllItems();
        if (current != null) {
            for (Item it : current.getItems()) comboItems.addItem(it.getNombre());
        }
    }

    // Deshabilita todos los botones (fin de partida)
    public void disableAllActions() {
        btnAtacar.setEnabled(false);
        btnPasar.setEnabled(false);
        btnUsarItem.setEnabled(false);
    }

    // Acción: Atacar (usa el combatiente que tiene el turno)
    private void onAtacar() {
        Combatiente current = controller.getCombatienteConTurno();
        if (current == null) return;
        int objetivoIdx = comboObjetivos.getSelectedIndex();
        if (objetivoIdx < 0) {
            appendLog("Selecciona un objetivo válido.");
            return;
        }
        controller.accionAtacar(current, objetivoIdx);
        // Actualizar items (posible cambio de items de quien tenga turno siguiente)
        mostrarEstado(controller.getGuerrero(), controller.getExplorador(), controller.getEnemigos());
    }

    private void onPasar() {
        Combatiente current = controller.getCombatienteConTurno();
        if (current == null) return;
        controller.accionPasar(current);
        mostrarEstado(controller.getGuerrero(), controller.getExplorador(), controller.getEnemigos());
    }

    private void onUsarItem() {
        Combatiente current = controller.getCombatienteConTurno();
        if (current == null) return;
        int idxItem = comboItems.getSelectedIndex();
        if (idxItem < 0) {
            appendLog("Selecciona un item.");
            return;
        }
        // Preguntar si usar en self o en enemigo (si hay enemigos)
        boolean usarEnSelf = true;
        if (comboObjetivos.getItemCount() > 0) {
            int resp = JOptionPane.showConfirmDialog(this, "¿Usar en ti? (No = en enemigo seleccionado)", "Usar Item", JOptionPane.YES_NO_OPTION);
            usarEnSelf = (resp == JOptionPane.YES_OPTION);
        }
        int objetivoIdx = comboObjetivos.getSelectedIndex();
        controller.accionUsarItem(current, idxItem, objetivoIdx, usarEnSelf);
        mostrarEstado(controller.getGuerrero(), controller.getExplorador(), controller.getEnemigos());
    }

    // Actualiza la lista de items visible en base a quién tenga el turno
    private void actualizarItemsSegunTurno() {
        comboItems.removeAllItems();
        Combatiente current = controller.getCombatienteConTurno();
        if (current != null) {
            for (Item it : current.getItems()) comboItems.addItem(it.getNombre());
        }
    }

// Habilita o deshabilita botones según el combatiente que tenga el turno y su estado (muerto o vivo).
public void habilitarAccionesJugador(Combatiente jugador) {
    // mostrar de quién es el turno
    lblTurnoInfo.setText("Turno: " + jugador.getNombre());
    // actualizar items para el combatiente actual
    comboItems.removeAllItems();
    if (jugador != null) {
        for (Item it : jugador.getItems()) comboItems.addItem(it.getNombre());
    }
    // habilitar botones si el combatiente está vivo
    boolean vivo = jugador != null && !jugador.isMuerto();
    btnAtacar.setEnabled(vivo && comboObjetivos.getItemCount() > 0);
    btnPasar.setEnabled(vivo);
    btnUsarItem.setEnabled(vivo && comboItems.getItemCount() > 0);
}

}
