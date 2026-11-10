package beans;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Consulta;
import modelo.Paciente;
import servicios.PacienteServicio;
import servicios.ReportePDFServicio;

@Named
@SessionScoped
public class HistorialMedicoBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idPacienteSeleccionado;
    private Paciente pacienteSeleccionado;
    private List<Consulta> historialConsultas;

    @Inject
    private PacienteServicio servicio;

    @Inject
    private ReportePDFServicio reportePDFServicio;

    public int getIdPacienteSeleccionado() {
        return idPacienteSeleccionado;
    }

    public void setIdPacienteSeleccionado(int idPacienteSeleccionado) {
        this.idPacienteSeleccionado = idPacienteSeleccionado;
    }

    public Paciente getPacienteSeleccionado() {
        return pacienteSeleccionado;
    }

    public void setPacienteSeleccionado(Paciente pacienteSeleccionado) {
        this.pacienteSeleccionado = pacienteSeleccionado;
    }

    public List<Consulta> getHistorialConsultas() {
        return historialConsultas;
    }

    public void cargarHistorial() {
        if (idPacienteSeleccionado > 0) {
            this.pacienteSeleccionado = servicio.buscarPacientePorId(idPacienteSeleccionado);
            this.historialConsultas = servicio.listarHistorialPorPaciente(idPacienteSeleccionado);
            if (historialConsultas == null || historialConsultas.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "El paciente seleccionado no tiene consultas previas registradas."));
            }
        } else {
            this.pacienteSeleccionado = null;
            this.historialConsultas = null;
        }
    }

    public void seleccionarPaciente(Paciente p) {
        if (p != null) {
            this.idPacienteSeleccionado = p.getIdPaciente();
            cargarHistorial();
        }
    }

    public void exportarHistorialPDF() {
        if (pacienteSeleccionado != null && historialConsultas != null && !historialConsultas.isEmpty()) {
            reportePDFServicio.generarReporteHistorial(pacienteSeleccionado, historialConsultas);
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Seleccione un paciente con consultas para generar el historial en PDF."));
        }
    }

    public List<Paciente> getListaPacientes() {
        return servicio != null ? servicio.listarPacientes() : null;
    }
}
