public class Terapeuta extends MedicoBase {
    private String tipoTerapia;
    private int duracionPromedioSesionMin; // minutos
    private int sesionesAtendidas;         // contador
    private static final double TARIFA_POR_SESION = 150.0;

    public Terapeuta(int id, String nombre, String departamento, int experiencia, double salarioBase,
                     String tipoTerapia, int duracionPromedioSesionMin) {
        super(id, nombre, departamento, experiencia, salarioBase);
        this.tipoTerapia = tipoTerapia;
        this.duracionPromedioSesionMin = duracionPromedioSesionMin;
    }

    public void registrarSesion() { sesionesAtendidas++; }

    @Override
    public double calcularSalario() {
        // Base + comisión por procedimiento (sesión)
        return salarioBase + (sesionesAtendidas * TARIFA_POR_SESION);
    }
}
