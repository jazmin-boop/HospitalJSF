package beans;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Consulta;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;
import servicios.PacienteServicio;

@Named
@RequestScoped
public class CalendarioBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private ScheduleModel eventModel;

    @Inject
    private PacienteServicio servicio;

    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();
        if (servicio != null) {
            List<Consulta> lista = servicio.listarConsultas();
            if (lista != null) {
                for (Consulta c : lista) {
                    if (c.getFecha() != null) {
                        try {
                            Date f = c.getFecha();
                            Instant instant = Instant.ofEpochMilli(f.getTime());
                            LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

                            // Ajustar hora según el campo c.getHora() (ej. "10:30 AM" o "02:00 PM")
                            if (c.getHora() != null && c.getHora().contains(":")) {
                                try {
                                    String hStr = c.getHora().trim();
                                    String[] parts = hStr.split(" ");
                                    String[] hm = parts[0].split(":");
                                    int h = Integer.parseInt(hm[0]);
                                    int m = Integer.parseInt(hm[1]);
                                    if (parts.length > 1 && parts[1].equalsIgnoreCase("PM") && h < 12) {
                                        h += 12;
                                    } else if (parts.length > 1 && parts[1].equalsIgnoreCase("AM") && h == 12) {
                                        h = 0;
                                    }
                                    ldt = ldt.withHour(h).withMinute(m);
                                } catch (Exception ignored) {
                                    ldt = ldt.withHour(10).withMinute(0);
                                }
                            } else {
                                ldt = ldt.withHour(10).withMinute(0);
                            }
                            
                            String pac = (c.getNombrePaciente() != null && !c.getNombrePaciente().isEmpty())
                                    ? c.getNombrePaciente() : ("Paciente #" + c.getIdPaciente());
                            String doc = (c.getNombreDoctor() != null && !c.getNombreDoctor().isEmpty())
                                    ? c.getNombreDoctor() : ("Dr. #" + c.getIdDoctor());
                            String diag = (c.getDiagnostico() != null && !c.getDiagnostico().isEmpty())
                                    ? c.getDiagnostico() : "Consulta Médica";

                            String titulo = pac + " (" + doc + ") - " + diag;

                            String styleClass;
                            switch (c.getIdDoctor() % 6) {
                                case 1: styleClass = "cal-event-blue"; break;
                                case 2: styleClass = "cal-event-emerald"; break;
                                case 3: styleClass = "cal-event-amber"; break;
                                case 4: styleClass = "cal-event-rose"; break;
                                case 5: styleClass = "cal-event-purple"; break;
                                default: styleClass = "cal-event-indigo"; break;
                            }

                            DefaultScheduleEvent<?> event = DefaultScheduleEvent.builder()
                                    .title(titulo)
                                    .startDate(ldt)
                                    .endDate(ldt.plusHours(1))
                                    .description(c.getTratamiento() != null ? c.getTratamiento() : "")
                                    .styleClass(styleClass)
                                    .build();
                            eventModel.addEvent(event);
                        } catch (Exception ex) {
                            System.out.println("Error al agendar evento en calendario: " + ex);
                        }
                    }
                }
            }
        }
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public java.time.LocalDate getInitialDate() {
        return java.time.LocalDate.now();
    }

    public List<Consulta> getConsultasAgendadas() {
        return servicio != null ? servicio.listarConsultas() : null;
    }
}
