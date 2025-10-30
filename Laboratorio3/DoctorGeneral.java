public class DoctorGeneral extends MedicoBase {
    private String certificacion;
    private int capacidadPacientes; // por día
    private double tarifaConsulta;  // por consulta
    private int consultasDelDia;    // contador simple para simulación

    public DoctorGeneral(int id, String nombre, String departamento, int experiencia, double salarioBase,
                         String certificacion, int capacidadPacientes, double tarifaConsulta) {
        super(id, nombre, departamento, experiencia, salarioBase);
        this.certificacion = certificacion;
        this.capacidadPacientes = capacidadPacientes;
        this.tarifaConsulta = tarifaConsulta;
    }

    public boolean tieneCupo() { return consultasDelDia < capacidadPacientes; }
    public void registrarConsulta() { consultasDelDia++; }
    public void resetDia() { consultasDelDia = 0; }

    @Override
    public double calcularSalario() {
        // Base + (consultas * tarifa)
        return salarioBase + (consultasDelDia * tarifaConsulta);
    }
}
