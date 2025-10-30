import java.util.Objects;

public abstract class MedicoBase {
    protected int id;
    protected String nombre;
    protected String departamento;
    protected int experiencia; // años
    protected double salarioBase;

    public MedicoBase(int id, String nombre, String departamento, int experiencia, double salarioBase) {
        this.id = id;
        this.nombre = nombre;
        this.departamento = departamento;
        this.experiencia = experiencia;
        this.salarioBase = salarioBase;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDepartamento() { return departamento; }
    public int getExperiencia() { return experiencia; }
    public double getSalarioBase() { return salarioBase; }
    public void setDepartamento(String d) { this.departamento = d; }
    public void setSalarioBase(double s) { this.salarioBase = s; }

    /** Polimórfica: cada especialidad calcula distinto. */
    public abstract double calcularSalario();

    /** Helper para reportes. */
    public String getTipo() { return this.getClass().getSimpleName(); }

    @Override
    public String toString() {
        return String.format("[%s] #%d %s (Depto: %s, Exp: %da, Base: Q%.2f)",
                getTipo(), id, nombre, departamento, experiencia, salarioBase);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MedicoBase)) return false;
        return ((MedicoBase)o).id == this.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
