import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class View extends JFrame {
    private final Controller c;

    // ==== Componentes ====
    private DefaultTableModel mdlPersonal = new DefaultTableModel(
            new Object[]{"ID","Nombre","Tipo","Depto","Exp","Salario (calc)"}, 0);
    private JTable tblPersonal = new JTable(mdlPersonal);

    private DefaultTableModel mdlCitas = new DefaultTableModel(
            new Object[]{"ID","Paciente","Médico","Fecha","Hora","Tipo","Estado"}, 0);
    private JTable tblCitas = new JTable(mdlCitas);

    private JTextArea txtReportes = new JTextArea(18, 60);

    // ==== Form Personal ====
    private JTextField txtNom = new JTextField(10);
    private JComboBox<String> cboTipo = new JComboBox<>(new String[]{"DoctorGeneral","Enfermero","Farmaceutico","Terapeuta"});
    private JTextField txtDepto = new JTextField(8);
    private JSpinner spExp = new JSpinner(new SpinnerNumberModel(3,0,60,1));
    private JSpinner spBase = new JSpinner(new SpinnerNumberModel(4000,0,20000,100));

    // Campos específicos rápidos
    private JTextField txtExtra1 = new JTextField(10); // certif / turno / limite / terapia
    private JTextField txtExtra2 = new JTextField(10); // capacidad / nivel / licencia(T/F) / duración
    private JTextField txtExtra3 = new JTextField(10); // tarifa / horasTurno / - / -

    // ==== Form Citas ====
    private JTextField txtPaciente = new JTextField(10);
    private JTextField txtMedicoId = new JTextField(5);
    private JTextField txtFecha = new JTextField(10); // yyyy-MM-dd
    private JTextField txtHora = new JTextField(5);   // HH:mm
    private JTextField txtTipoCita = new JTextField(12);

    public View(Controller controller) {
        super("Sistema de Gestión Hospitalaria - Laboratorio 3 (MVC + GUI)");
        this.c = controller;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(980, 700);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Personal", panelPersonal());
        tabs.add("Citas", panelCitas());
        tabs.add("Reportes", panelReportes());

        setContentPane(tabs);
        refrescarTablas();
    }

    private JPanel panelPersonal() {
        JPanel p = new JPanel(new BorderLayout(10,10));
        JPanel form = new JPanel(new GridLayout(0,4,8,8));

        form.add(new JLabel("Nombre:"));
        form.add(txtNom);
        form.add(new JLabel("Tipo:"));
        form.add(cboTipo);

        form.add(new JLabel("Departamento:"));
        form.add(txtDepto);
        form.add(new JLabel("Experiencia (años):"));
        form.add(spExp);

        form.add(new JLabel("Salario base:"));
        form.add(spBase);
        form.add(new JLabel("Extra1:"));
        form.add(txtExtra1);

        form.add(new JLabel("Extra2:"));
        form.add(txtExtra2);
        form.add(new JLabel("Extra3:"));
        form.add(txtExtra3);

        JButton btnAdd = new JButton("Contratar");
        btnAdd.addActionListener(e -> contratar());
        JButton btnMasivo = new JButton("Contratación Masiva");
        btnMasivo.addActionListener(e -> { c.contratacionMasiva(); refrescarTablas(); });

        JPanel south = new JPanel();
        south.add(btnAdd);
        south.add(btnMasivo);

        p.add(form, BorderLayout.NORTH);
        p.add(new JScrollPane(tblPersonal), BorderLayout.CENTER);
        p.add(south, BorderLayout.SOUTH);
        return p;
    }

    private JPanel panelCitas() {
        JPanel p = new JPanel(new BorderLayout(10,10));
        JPanel form = new JPanel(new GridLayout(0,6,8,8));

        form.add(new JLabel("Paciente:")); form.add(txtPaciente);
        form.add(new JLabel("Médico ID (opcional):")); form.add(txtMedicoId);
        form.add(new JLabel("Fecha (yyyy-MM-dd):")); form.add(txtFecha);
        form.add(new JLabel("Hora (HH:mm):")); form.add(txtHora);
        form.add(new JLabel("Tipo cita:")); form.add(txtTipoCita);

        JButton btnAsignar = new JButton("Asignación Inteligente + Programar");
        btnAsignar.addActionListener(e -> programarConAI());

        JButton btnSaturar = new JButton("Agenda Saturada (20)");
        btnSaturar.addActionListener(e -> {
            c.agendaSaturada(LocalDate.now().plusDays(1));
            refrescarTablas();
            JOptionPane.showMessageDialog(this, "Se programaron 20 citas y se resolvieron choques.");
        });

        JButton btnConflictos = new JButton("Resolver Conflictos");
        btnConflictos.addActionListener(e -> { c.gestionConflictos(); refrescarTablas(); });

        JPanel south = new JPanel();
        south.add(btnAsignar); south.add(btnConflictos); south.add(btnSaturar);

        p.add(form, BorderLayout.NORTH);
        p.add(new JScrollPane(tblCitas), BorderLayout.CENTER);
        p.add(south, BorderLayout.SOUTH);
        return p;
    }

    private JPanel panelReportes() {
        JPanel p = new JPanel(new BorderLayout(10,10));
        txtReportes.setEditable(false);
        JButton btnGenerar = new JButton("Generar Reportes");
        btnGenerar.addActionListener(e -> {
            String rep = c.generarReportes()
                    + "\n" + c.analisisUtilizacion()
                    + "\n" + c.analisisFinancieroPorDepartamento()
                    + "\n=== Historial de Reagendamientos ===\n"
                    + String.join("\n", c.getHistorialCambios());
            txtReportes.setText(rep);
        });
        p.add(new JScrollPane(txtReportes), BorderLayout.CENTER);
        p.add(btnGenerar, BorderLayout.SOUTH);
        return p;
    }

    private void contratar() {
        try {
            String nombre = txtNom.getText().trim();
            String tipo = (String)cboTipo.getSelectedItem();
            String depto = txtDepto.getText().trim();
            int exp = (int)spExp.getValue();
            double base = ((Number)spBase.getValue()).doubleValue();
            int id = c.generarNuevoIdEmpleado();

            MedicoBase m;
            switch (tipo) {
                case "DoctorGeneral":
                    String cert = txtExtra1.getText().trim();
                    int capacidad = Integer.parseInt(txtExtra2.getText().trim());
                    double tarifa = Double.parseDouble(txtExtra3.getText().trim());
                    m = new DoctorGeneral(id, nombre, depto, exp, base, cert, capacidad, tarifa);
                    break;
                case "Enfermero":
                    String turno = txtExtra1.getText().trim();            // diurno/nocturno
                    String nivel = txtExtra2.getText().trim();
                    int horas = Integer.parseInt(txtExtra3.getText().trim());
                    m = new Enfermero(id, nombre, depto, exp, base, turno, nivel, horas);
                    break;
                case "Farmaceutico":
                    int limite = Integer.parseInt(txtExtra1.getText().trim());
                    boolean lic = Boolean.parseBoolean(txtExtra2.getText().trim()); // true/false
                    m = new Farmaceutico(id, nombre, depto, exp, base, limite, lic);
                    break;
                default: // Terapeuta
                    String terapia = txtExtra1.getText().trim();
                    int dur = Integer.parseInt(txtExtra2.getText().trim());
                    m = new Terapeuta(id, nombre, depto, exp, base, terapia, dur);
            }
            c.contratarPersonal(m);
            refrescarTablas();
            JOptionPane.showMessageDialog(this, "Personal contratado: " + m.getNombre());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Datos inválidos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void programarConAI() {
        try {
            String paciente = txtPaciente.getText().trim();
            String tipo = txtTipoCita.getText().trim();
            LocalDate fecha = LocalDate.parse(txtFecha.getText().trim());
            LocalTime hora = LocalTime.parse(txtHora.getText().trim());

            MedicoBase medico = null;
            if (!txtMedicoId.getText().trim().isEmpty()) {
                int id = Integer.parseInt(txtMedicoId.getText().trim());
                for (MedicoBase m : c.getPersonal()) if (m.getId() == id) { medico = m; break; }
            }
            if (medico == null) {
                medico = c.asignacionInteligente(tipo, fecha, hora)
                        .orElseThrow(() -> new RuntimeException("No hay personal disponible para ese horario."));
            }

            c.programarCita(paciente, medico, fecha, hora, tipo);
            c.gestionConflictos();
            refrescarTablas();
            JOptionPane.showMessageDialog(this, "Cita programada (con resolución de conflictos).");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al programar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refrescarTablas() {
        // Personal
        mdlPersonal.setRowCount(0);
        for (MedicoBase m : c.getPersonal()) {
            mdlPersonal.addRow(new Object[]{
                    m.getId(), m.getNombre(), m.getTipo(), m.getDepartamento(),
                    m.getExperiencia(), String.format("Q%.2f", m.calcularSalario())
            });
        }

        // Citas
        mdlCitas.setRowCount(0);
        for (CitaMedica ci : c.getCitas()) {
            mdlCitas.addRow(new Object[]{
                    ci.getId(), ci.getNombrePaciente(), ci.getMedicoAsignado().getNombre(),
                    ci.getFecha().toString(), ci.getHora().toString(), ci.getTipoCita(), ci.getEstado().name()
            });
        }
    }
}
