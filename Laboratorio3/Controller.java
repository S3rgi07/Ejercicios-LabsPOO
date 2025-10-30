import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/** Controla personal, citas y reportes. */
public class Controller {
    private List<MedicoBase> personal = new ArrayList<>();
    private List<CitaMedica> citas = new ArrayList<>();
    private List<String> historialCambios = new ArrayList<>();

    private int nextCitaId = 1;
    private int nextEmpleadoId = 1;

    // ====== Gestión de Personal ======
    public MedicoBase contratarPersonal(MedicoBase m) {
        personal.add(m);
        log("Contratado: " + m);
        return m;
    }

    public int generarNuevoIdEmpleado() { return nextEmpleadoId++; }
    public int generarNuevoIdCita() { return nextCitaId++; }

    public List<MedicoBase> getPersonal() { return Collections.unmodifiableList(personal); }
    public List<CitaMedica> getCitas() { return Collections.unmodifiableList(citas); }
    public List<String> getHistorialCambios() { return Collections.unmodifiableList(historialCambios); }

    // ====== Programación de citas ======
    public CitaMedica programarCita(String paciente, MedicoBase medico, LocalDate fecha,
                                    LocalTime hora, String tipo) {
        CitaMedica c = new CitaMedica(generarNuevoIdCita(), paciente, medico, fecha, hora, tipo);

        Optional<CitaMedica> conflicto = encontrarConflicto(medico, fecha, hora);
        if (conflicto.isPresent()) {
            // Reagendamiento inteligente: busca siguiente slot disponible de 30 en 30 min
            LocalDateTime nuevo = encontrarSiguienteSlotDisponible(medico, LocalDateTime.of(fecha, hora));
            c.setFecha(nuevo.toLocalDate());
            c.setHora(nuevo.toLocalTime());
            c.setEstado(EstadoCita.REAGENDADA);
            log(String.format("Conflicto detectado. Cita #%d reagendada a %s %s.",
                    c.getId(), c.getFecha(), c.getHora()));
            notificar("Reagendamiento", "Se cambió su cita por conflicto: " + c);
        } else {
            log("Cita programada: " + c);
            notificar("Nueva cita", c.toString());
        }

        // Efectos colaterales de atención: ejemplo para salarios (simple)
        if (medico instanceof DoctorGeneral) ((DoctorGeneral) medico).registrarConsulta();
        if (medico instanceof Terapeuta) ((Terapeuta) medico).registrarSesion();

        citas.add(c);
        return c;
    }

    private Optional<CitaMedica> encontrarConflicto(MedicoBase medico, LocalDate fecha, LocalTime hora) {
        return citas.stream().filter(x ->
                x.getMedicoAsignado().equals(medico) &&
                x.getFecha().equals(fecha) &&
                x.getHora().equals(hora) &&
                x.getEstado() != EstadoCita.CANCELADA
        ).findFirst();
    }

    private LocalDateTime encontrarSiguienteSlotDisponible(MedicoBase medico, LocalDateTime desde) {
        LocalDateTime candidato = desde.plusMinutes(30);
        // ventana de búsqueda de 8 a 18h, salta al siguiente día si es necesario
        while (true) {
            if (candidato.getHour() < 8) candidato = candidato.withHour(8).withMinute(0);
            if (candidato.getHour() >= 18) candidato = candidato.plusDays(1).withHour(8).withMinute(0);

            LocalDateTime finalCandidato = candidato;
            boolean libre = citas.stream().noneMatch(x ->
                    x.getMedicoAsignado().equals(medico) &&
                    x.getFechaHora().equals(finalCandidato) &&
                    x.getEstado() != EstadoCita.CANCELADA);

            if (libre) return candidato;
            candidato = candidato.plusMinutes(30);
        }
    }

    // ====== Asignación Inteligente ======
    public Optional<MedicoBase> asignacionInteligente(String tipoCita, LocalDate fecha, LocalTime hora) {
        // Estrategia simple: buscar DoctorGeneral con cupo; si no, cualquier médico del depto
        List<MedicoBase> candidatos = new ArrayList<>(personal);
        if ("consulta general".equalsIgnoreCase(tipoCita)) {
            candidatos = personal.stream()
                    .filter(p -> p instanceof DoctorGeneral)
                    .collect(Collectors.toList());
        }

        for (MedicoBase m : candidatos) {
            boolean libre = !encontrarConflicto(m, fecha, hora).isPresent();
            if (libre) {
                if (m instanceof DoctorGeneral && !((DoctorGeneral) m).tieneCupo())
                    continue;
                return Optional.of(m);
            }
        }
        return Optional.empty();
    }

    // ====== Gestión de conflictos (resuelve choques automáticamente) ======
    public void gestionConflictos() {
        Map<String, List<CitaMedica>> mapa = citas.stream()
                .collect(Collectors.groupingBy(c -> c.getMedicoAsignado().getId() + "-" + c.getFecha() + "-" + c.getHora()));

        mapa.values().stream().filter(list -> list.size() > 1).forEach(list -> {
            // Dejar la primera, reagendar las demás
            for (int i = 1; i < list.size(); i++) {
                CitaMedica c = list.get(i);
                LocalDateTime nuevo = encontrarSiguienteSlotDisponible(c.getMedicoAsignado(), c.getFechaHora());
                registrarCambio(c, c.getFechaHora(), nuevo);
                c.setFecha(nuevo.toLocalDate());
                c.setHora(nuevo.toLocalTime());
                c.setEstado(EstadoCita.REAGENDADA);
                notificar("Conflicto resuelto", "Se reagendó: " + c);
            }
        });
    }

    // ====== Reportes ======
    public String reportePersonal() {
        StringBuilder sb = new StringBuilder("=== Personal Médico ===\n");
        for (MedicoBase m : personal) {
            sb.append(m).append("\n");
        }
        return sb.toString();
    }

    public String reporteCitas() {
        StringBuilder sb = new StringBuilder("=== Citas ===\n");
        citas.stream()
                .sorted(Comparator.comparing(CitaMedica::getFecha).thenComparing(CitaMedica::getHora))
                .forEach(c -> sb.append(c).append("\n"));
        return sb.toString();
    }

    public String generarReportes() {
        return reportePersonal() + "\n" + reporteCitas();
    }

    public String analisisFinancieroPorDepartamento() {
        Map<String, Double> nominas = personal.stream().collect(
                Collectors.groupingBy(MedicoBase::getDepartamento,
                        Collectors.summingDouble(MedicoBase::calcularSalario)));
        StringBuilder sb = new StringBuilder("=== Nómina por departamento ===\n");
        nominas.forEach((d, q) -> sb.append(d).append(": Q").append(String.format("%.2f", q)).append("\n"));
        return sb.toString();
    }

    public String analisisUtilizacion() {
        // Proxy simple: contar citas por médico
        Map<MedicoBase, Long> conteo = citas.stream()
                .collect(Collectors.groupingBy(CitaMedica::getMedicoAsignado, Collectors.counting()));
        StringBuilder sb = new StringBuilder("=== Utilización (citas por médico) ===\n");
        personal.forEach(m ->
                sb.append(String.format("%s -> %d citas\n", m.getNombre(), conteo.getOrDefault(m, 0L))));
        return sb.toString();
    }

    // ====== Escenarios sugeridos ======
    public void contratacionMasiva() {
        // Crea 15+ trabajadores variados
        for (int i = 0; i < 6; i++) {
            contratarPersonal(new DoctorGeneral(generarNuevoIdEmpleado(), "Dr. General " + (i+1),
                    "Medicina", 5+i, 6000, "Médico y Cirujano", 12, 150));
        }
        for (int i = 0; i < 5; i++) {
            contratarPersonal(new Enfermero(generarNuevoIdEmpleado(), "Enfermero " + (i+1),
                    "Enfermería", 2+i, 3500, (i%2==0?"diurno":"nocturno"), "Nivel "+(char)('A'+i), 40));
        }
        contratarPersonal(new Farmaceutico(generarNuevoIdEmpleado(), "Farmacéutico 1",
                "Farmacia", 4, 3800, 60, true));
        contratarPersonal(new Terapeuta(generarNuevoIdEmpleado(), "Terapeuta 1",
                "Terapia", 3, 4200, "Física", 45));
        contratarPersonal(new Terapeuta(generarNuevoIdEmpleado(), "Terapeuta 2",
                "Terapia", 7, 4800, "Respiratoria", 40));
    }

    public void agendaSaturada(LocalDate fechaBase) {
        // Programa 20 citas el mismo día con potenciales choques
        Random r = new Random();
        List<MedicoBase> docs = personal.stream().filter(p -> p instanceof DoctorGeneral).collect(Collectors.toList());
        if (docs.isEmpty()) return;
        for (int i = 0; i < 20; i++) {
            MedicoBase m = docs.get(r.nextInt(docs.size()));
            int hora = 8 + r.nextInt(10); // 8..17
            int min = (r.nextBoolean() ? 0 : 30);
            programarCita("Paciente " + (i+1), m, fechaBase, LocalTime.of(hora, min), "consulta general");
        }
        // Resolver choques
        gestionConflictos();
    }

    public void crisisPersonal(Set<MedicoBase> ausentes) {
        // Reasigna citas del día de hoy de los ausentes al primer disponible
        LocalDate hoy = LocalDate.now();
        for (CitaMedica c : citas) {
            if (c.getFecha().equals(hoy) && ausentes.contains(c.getMedicoAsignado())
                    && c.getEstado() != EstadoCita.CANCELADA) {
                Optional<MedicoBase> nuevo = asignacionInteligente(c.getTipoCita(), c.getFecha(), c.getHora());
                if (nuevo.isPresent()) {
                    registrarCambio(c, c.getFechaHora(), c.getFechaHora());
                    c.setMedicoAsignado(nuevo.get());
                    c.setEstado(EstadoCita.REAGENDADA);
                    notificar("Reasignación por crisis", "Nueva asignación: " + c);
                }
            }
        }
    }

    // ====== Utilidades ======
    private void registrarCambio(CitaMedica c, LocalDateTime antes, LocalDateTime despues) {
        String entrada = String.format("Cita #%d: %s -> %s", c.getId(), antes, despues);
        historialCambios.add(entrada);
        log("Historial: " + entrada);
    }

    private void log(String msg) { System.out.println("[LOG] " + msg); }
    private void notificar(String titulo, String cuerpo) {
        System.out.println("[NOTIFICACIÓN] " + titulo + " - " + cuerpo);
    }
}
