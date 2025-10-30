import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CitaMedica {
    private int id;
    private String nombrePaciente;
    private MedicoBase medicoAsignado;
    private LocalDate fecha;
    private LocalTime hora;
    private String tipoCita; // consulta general, terapia, diagnóstico...
    private EstadoCita estado;

    public CitaMedica(int id, String nombrePaciente, MedicoBase medicoAsignado,
                      LocalDate fecha, LocalTime hora, String tipoCita) {
        this.id = id;
        this.nombrePaciente = nombrePaciente;
        this.medicoAsignado = medicoAsignado;
        this.fecha = fecha;
        this.hora = hora;
        this.tipoCita = tipoCita;
        this.estado = EstadoCita.PROGRAMADA;
    }

    public int getId() { return id; }
    public String getNombrePaciente() { return nombrePaciente; }
    public MedicoBase getMedicoAsignado() { return medicoAsignado; }
    public LocalDate getFecha() { return fecha; }
    public LocalTime getHora() { return hora; }
    public String getTipoCita() { return tipoCita; }
    public EstadoCita getEstado() { return estado; }

    public void setEstado(EstadoCita e) { this.estado = e; }
    public void setFecha(LocalDate f) { this.fecha = f; }
    public void setHora(LocalTime h) { this.hora = h; }
    public void setMedicoAsignado(MedicoBase m) { this.medicoAsignado = m; }

    public LocalDateTime getFechaHora() {
        return LocalDateTime.of(fecha, hora);
    }

    @Override
    public String toString() {
        return String.format("Cita #%d: %s con %s el %s %s [%s]",
                id, nombrePaciente, medicoAsignado.getNombre(), fecha, hora, estado);
    }
}
