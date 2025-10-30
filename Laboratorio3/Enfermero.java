public class Enfermero extends MedicoBase {
    private String tipoTurno;         // diurno/nocturno
    private String nivelCertificacion;
    private int horasTurno;           // horas trabajadas del periodo
    private static final double BONO_NOCTURNO = 600.0; // ejemplo

    public Enfermero(int id, String nombre, String departamento, int experiencia, double salarioBase,
                     String tipoTurno, String nivelCertificacion, int horasTurno) {
        super(id, nombre, departamento, experiencia, salarioBase);
        this.tipoTurno = tipoTurno;
        this.nivelCertificacion = nivelCertificacion;
        this.horasTurno = horasTurno;
    }

    @Override
    public double calcularSalario() {
        // Base + bonificación nocturna si aplica (simples)
        double total = salarioBase;
        if ("nocturno".equalsIgnoreCase(tipoTurno)) total += BONO_NOCTURNO;
        return total;
    }
}
