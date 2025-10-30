public class Farmaceutico extends MedicoBase {
    private int limitePrescripcionesDia;
    private boolean licenciaSustanciasControladas;

    private int prescripcionesRealizadas; // contador simple para simulación
    private static final double COMISION_POR_RECETA = 5.0;

    public Farmaceutico(int id, String nombre, String departamento, int experiencia, double salarioBase,
                        int limitePrescripcionesDia, boolean licenciaSustanciasControladas) {
        super(id, nombre, departamento, experiencia, salarioBase);
        this.limitePrescripcionesDia = limitePrescripcionesDia;
        this.licenciaSustanciasControladas = licenciaSustanciasControladas;
    }

    public boolean puedePrescribir() { return prescripcionesRealizadas < limitePrescripcionesDia; }
    public void registrarPrescripcion() { prescripcionesRealizadas++; }

    @Override
    public double calcularSalario() {
        // Base + comisión simple
        return salarioBase + (prescripcionesRealizadas * COMISION_POR_RECETA);
    }
}
