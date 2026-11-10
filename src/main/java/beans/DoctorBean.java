package beans;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Doctor;
import servicios.PacienteServicio;
import servicios.ReportePDFServicio;

@Named
@SessionScoped
public class DoctorBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private Doctor doctor = new Doctor();

    @Inject
    private PacienteServicio servicio;

    @Inject
    private ReportePDFServicio reportePDFServicio;

    public DoctorBean() {
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void guardar() {
        boolean esNuevo = (doctor.getIdDoctor() == 0);
        servicio.guardarDoctor(doctor);
        String msg = esNuevo ? "Doctor registrado correctamente" : "Doctor actualizado correctamente";
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", msg));
        nuevo();
    }

    public void editar(Doctor d) {
        this.doctor = new Doctor();
        this.doctor.setIdDoctor(d.getIdDoctor());
        this.doctor.setNombre(d.getNombre());
        this.doctor.setEspecialidad(d.getEspecialidad());
        this.doctor.setTelefono(d.getTelefono());
        this.doctor.setEmail(d.getEmail());
    }

    public void eliminar(int id) {
        servicio.eliminarDoctor(id);
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_WARN, "Eliminado", "Doctor eliminado correctamente"));
        nuevo();
    }

    public void nuevo() {
        this.doctor = new Doctor();
    }

    public List<Doctor> getListaDoctores() {
        return servicio != null ? servicio.listarDoctores() : null;
    }

    public int getTotalDoctores() {
        return servicio != null ? servicio.contarDoctores() : 0;
    }

    public void exportarPDF() {
        List<Doctor> lista = getListaDoctores();
        if (lista != null && !lista.isEmpty() && reportePDFServicio != null) {
            reportePDFServicio.generarReporteDoctores(lista);
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "No hay doctores registrados para generar el reporte."));
        }
    }
}
