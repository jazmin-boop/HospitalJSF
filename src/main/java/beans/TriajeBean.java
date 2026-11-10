package beans;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Paciente;
import modelo.Triaje;
import servicios.PacienteServicio;
import servicios.ReportePDFServicio;

@Named
@SessionScoped
public class TriajeBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private Triaje triaje = new Triaje();

    @Inject
    private PacienteServicio servicio;

    @Inject
    private ReportePDFServicio reportePDFServicio;

    @Inject
    private LoginBean loginBean;

    public TriajeBean() {
    }

    @PostConstruct
    public void init() {
        nuevo();
    }

    public Triaje getTriaje() {
        return triaje;
    }

    public void setTriaje(Triaje triaje) {
        this.triaje = triaje;
    }

    public void onPacienteSeleccionado() {
        if (triaje.getIdPaciente() > 0 && servicio != null) {
            Paciente p = servicio.buscarPacientePorId(triaje.getIdPaciente());
            if (p != null) {
                triaje.setCodigoAsegurado("01-004-" + (p.getDni() != null ? p.getDni() : "12345678"));
                if (p.getTalla() != null && !p.getTalla().isEmpty()) triaje.setTalla(p.getTalla());
                if (p.getPeso() != null && !p.getPeso().isEmpty()) triaje.setPeso(p.getPeso());
                if (p.getPresion() != null && !p.getPresion().isEmpty()) triaje.setPresion(p.getPresion());
                if (p.getTemperatura() != null && !p.getTemperatura().isEmpty()) triaje.setTemperatura(p.getTemperatura());
                calcularIMC();
            }
        }
    }

    public void calcularIMC() {
        try {
            if (triaje.getPeso() != null && triaje.getTalla() != null) {
                String pStr = triaje.getPeso().replaceAll("[^0-9.]", "").trim();
                String tStr = triaje.getTalla().replaceAll("[^0-9.]", "").trim();
                if (!pStr.isEmpty() && !tStr.isEmpty()) {
                    double peso = Double.parseDouble(pStr);
                    double talla = Double.parseDouble(tStr);
                    if (talla > 3.0) { // En centímetros
                        talla = talla / 100.0;
                    }
                    if (talla > 0) {
                        double imcVal = peso / (talla * talla);
                        DecimalFormat df = new DecimalFormat("#.0");
                        triaje.setImc(df.format(imcVal));
                        if (imcVal < 18.5) {
                            triaje.setEstadoNutricional("Bajo Peso");
                        } else if (imcVal < 25.0) {
                            triaje.setEstadoNutricional("Normal");
                        } else if (imcVal < 30.0) {
                            triaje.setEstadoNutricional("Sobrepeso");
                        } else {
                            triaje.setEstadoNutricional("Obesidad");
                        }
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }

    public void guardar() {
        if (triaje.getIdPaciente() <= 0) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validación", "Debe seleccionar al paciente para realizar el triaje"));
            return;
        }

        calcularIMC();
        boolean esNuevo = (triaje.getIdTriaje() == 0);
        if (servicio != null) {
            servicio.guardarTriaje(triaje);
        }
        String msg = esNuevo ? "Ficha de Triaje registrada correctamente en la Red Asistencial" : "Ficha de Triaje actualizada";
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito Triaje", msg));
        nuevo();
    }

    public void editar(Triaje t) {
        this.triaje = new Triaje();
        this.triaje.setIdTriaje(t.getIdTriaje());
        this.triaje.setIdPaciente(t.getIdPaciente());
        this.triaje.setFecha(t.getFecha());
        this.triaje.setHora(t.getHora());
        this.triaje.setPresion(t.getPresion());
        this.triaje.setTemperatura(t.getTemperatura());
        this.triaje.setFrecuenciaCardiaca(t.getFrecuenciaCardiaca());
        this.triaje.setFrecuenciaRespiratoria(t.getFrecuenciaRespiratoria());
        this.triaje.setSaturacionOxigeno(t.getSaturacionOxigeno());
        this.triaje.setPeso(t.getPeso());
        this.triaje.setTalla(t.getTalla());
        this.triaje.setImc(t.getImc());
        this.triaje.setEstadoNutricional(t.getEstadoNutricional());
        this.triaje.setPrioridad(t.getPrioridad());
        this.triaje.setTipoSeguro(t.getTipoSeguro());
        this.triaje.setCodigoAsegurado(t.getCodigoAsegurado());
        this.triaje.setMotivoConsulta(t.getMotivoConsulta());
        this.triaje.setDestinoConsultorio(t.getDestinoConsultorio());
        this.triaje.setEstado(t.getEstado());
    }

    public void eliminar(int id) {
        if (servicio != null) {
            servicio.eliminarTriaje(id);
        }
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_WARN, "Eliminado", "Ficha de Triaje eliminada"));
        nuevo();
    }

    public void nuevo() {
        this.triaje = new Triaje();
        this.triaje.setFecha(new Date());
        this.triaje.setHora("10:00 AM");
        this.triaje.setPrioridad("Prioridad III (Urgencia Menor)");
        this.triaje.setTipoSeguro("Seguro Integral");
        this.triaje.setDestinoConsultorio("Consultorio 1 - Cardiología");
        this.triaje.setEstado("Triado");
        this.triaje.setPresion("120/80 mmHg");
        this.triaje.setTemperatura("36.5 °C");
        this.triaje.setFrecuenciaCardiaca("72 lpm");
        this.triaje.setFrecuenciaRespiratoria("18 rpm");
        this.triaje.setSaturacionOxigeno("98 %");
        this.triaje.setTalla("1.70 m");
        this.triaje.setPeso("68.5 kg");
        this.triaje.setImc("23.7");
        this.triaje.setEstadoNutricional("Normal");
    }

    public void exportarPDF() {
        List<Triaje> lista = getListaTriajes();
        if (lista != null && !lista.isEmpty() && reportePDFServicio != null) {
            reportePDFServicio.generarReporteTriaje(lista);
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "No hay fichas de triaje para exportar a PDF"));
        }
    }

    public List<Triaje> getListaTriajes() {
        return servicio != null ? servicio.listarTriajes() : null;
    }

    public List<Paciente> getListaPacientes() {
        return servicio != null ? servicio.listarPacientes() : null;
    }

    public int getTotalTriajes() {
        return servicio != null ? servicio.contarTriajes() : 0;
    }
}
