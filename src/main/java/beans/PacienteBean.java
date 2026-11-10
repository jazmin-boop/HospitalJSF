package beans;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Doctor;
import modelo.Paciente;
import servicios.PacienteServicio;
import servicios.ReportePDFServicio;

@Named
@SessionScoped
public class PacienteBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private Paciente paciente = new Paciente();

    @Inject
    private PacienteServicio servicio;

    @Inject
    private ReportePDFServicio reportePDFServicio;

    public PacienteBean() {
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    // CRUD: CREATE / UPDATE
    public void guardar() {
        boolean esNuevo = (paciente.getIdPaciente() == 0);
        servicio.guardarPaciente(paciente);
        String mensaje = esNuevo ? "Paciente registrado correctamente en MySQL" : "Paciente actualizado correctamente";
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", mensaje));
        nuevo();
    }

    // CRUD: PREPARAR EDICIÓN
    public void editar(Paciente p) {
        this.paciente = new Paciente();
        this.paciente.setIdPaciente(p.getIdPaciente());
        this.paciente.setNombre(p.getNombre());
        this.paciente.setEdad(p.getEdad());
        this.paciente.setDni(p.getDni());
        this.paciente.setSexo(p.getSexo());
        this.paciente.setTelefono(p.getTelefono());
        this.paciente.setDireccion(p.getDireccion());
        this.paciente.setTalla(p.getTalla());
        this.paciente.setPeso(p.getPeso());
        this.paciente.setPresion(p.getPresion());
        this.paciente.setTemperatura(p.getTemperatura());
        this.paciente.setAlergias(p.getAlergias());
    }

    // CRUD: DELETE
    public void eliminar(int id) {
        servicio.eliminarPaciente(id);
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_WARN, "Eliminado", "Paciente eliminado de la base de datos"));
        nuevo();
    }

    // LIMPIAR FORMULARIO
    public void nuevo() {
        this.paciente = new Paciente();
    }

    // REPORTES PDF
    public void exportarPDF() {
        List<Paciente> lista = getListaPacientes();
        if (lista != null && !lista.isEmpty()) {
            reportePDFServicio.generarReportePacientes(lista);
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "No hay pacientes para exportar a PDF"));
        }
    }

    // READ
    public List<Paciente> getListaPacientes() {
        return servicio != null ? servicio.listarPacientes() : null;
    }

    public int getTotalPacientes() {
        return servicio != null ? servicio.contarPacientes() : 0;
    }

    public int getTotalDoctores() {
        return servicio != null ? servicio.contarDoctores() : 0;
    }

    public int getTotalConsultas() {
        return servicio != null ? servicio.contarConsultas() : 0;
    }

    public List<Doctor> getListaDoctores() {
        return servicio != null ? servicio.listarDoctores() : null;
    }
}
