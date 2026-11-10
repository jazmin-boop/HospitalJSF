package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Consulta;
import modelo.Doctor;
import modelo.Paciente;
import servicios.PacienteServicio;
import servicios.ReportePDFServicio;

@Named
@SessionScoped
public class ConsultaBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private Consulta consulta = new Consulta();

    @Inject
    private PacienteServicio servicio;

    @Inject
    private LoginBean loginBean;

    @Inject
    private ReportePDFServicio reportePDFServicio;

    public ConsultaBean() {
        this.consulta.setFecha(new Date());
    }

    @PostConstruct
    public void init() {
        nuevo();
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public void guardarConsulta() {
        if (consulta.getIdPaciente() <= 0) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validación", "Debe seleccionar un paciente para la consulta"));
            return;
        }

        // Si es médico, forzar que sea su propio id de doctor
        if (loginBean != null && loginBean.isDoctor() && loginBean.getUsuarioLogueado() != null && loginBean.getUsuarioLogueado().getIdDoctor() != null) {
            consulta.setIdDoctor(loginBean.getUsuarioLogueado().getIdDoctor());
        }

        if (consulta.getIdDoctor() <= 0) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validación", "Debe seleccionar el médico tratante"));
            return;
        }

        boolean esNueva = (consulta.getIdConsulta() == 0);
        if (servicio != null) {
            servicio.guardarConsulta(consulta);
        }
        String msg = esNueva ? "Consulta médica registrada con éxito en MySQL" : "Consulta médica actualizada";
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", msg));
        nuevo();
    }

    public void editar(Consulta c) {
        this.consulta = new Consulta();
        this.consulta.setIdConsulta(c.getIdConsulta());
        this.consulta.setDiagnostico(c.getDiagnostico());
        this.consulta.setTratamiento(c.getTratamiento());
        this.consulta.setAnalisis(c.getAnalisis());
        this.consulta.setFecha(c.getFecha());
        this.consulta.setIdPaciente(c.getIdPaciente());
        this.consulta.setIdDoctor(c.getIdDoctor());
        this.consulta.setEstado(c.getEstado());
        this.consulta.setHora(c.getHora());
    }

    public void eliminar(int id) {
        if (servicio != null) {
            servicio.eliminarConsulta(id);
        }
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_WARN, "Eliminado", "Consulta médica eliminada del sistema"));
        nuevo();
    }

    public void nuevo() {
        this.consulta = new Consulta();
        this.consulta.setFecha(new Date());
        this.consulta.setEstado("Atendida");
        this.consulta.setHora("10:00 AM");

        // Si el usuario logueado es doctor, preseleccionar automáticamente su ID de doctor
        if (loginBean != null && loginBean.isDoctor() && loginBean.getUsuarioLogueado() != null && loginBean.getUsuarioLogueado().getIdDoctor() != null) {
            this.consulta.setIdDoctor(loginBean.getUsuarioLogueado().getIdDoctor());
        }
    }

    public Consulta getProximaConsulta() {
        return servicio != null ? servicio.obtenerProximaConsulta() : null;
    }

    public List<Consulta> getListaConsultas() {
        List<Consulta> todas = servicio != null ? servicio.listarConsultas() : null;
        // Si es médico, ver sólo sus consultas o todas
        return todas;
    }

    public List<Doctor> getListaDoctores() {
        if (servicio == null) return null;
        List<Doctor> todos = servicio.listarDoctores();

        // REQUISITO: Si el doctor se loguea, en consultas el médico tratante SOLO debe ser ese Dr. que se logueó
        if (loginBean != null && loginBean.isDoctor() && loginBean.getUsuarioLogueado() != null && loginBean.getUsuarioLogueado().getIdDoctor() != null) {
            int miDoctorId = loginBean.getUsuarioLogueado().getIdDoctor();
            List<Doctor> soloYo = new ArrayList<>();
            if (todos != null) {
                for (Doctor d : todos) {
                    if (d.getIdDoctor() == miDoctorId) {
                        soloYo.add(d);
                        break;
                    }
                }
            }
            return soloYo;
        }

        // Si es ADMIN, puede ver y seleccionar cualquiera
        return todos;
    }

    public List<Paciente> getListaPacientes() {
        return servicio != null ? servicio.listarPacientes() : null;
    }

    public int getTotalConsultas() {
        return servicio != null ? servicio.contarConsultas() : 0;
    }

    public void exportarPDF() {
        List<Consulta> lista = getListaConsultas();
        if (lista != null && !lista.isEmpty() && reportePDFServicio != null) {
            reportePDFServicio.generarReporteConsultas(lista);
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "No hay consultas registradas para generar el reporte."));
        }
    }
}
