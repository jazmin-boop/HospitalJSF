package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "consulta")
public class Consulta implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idConsulta")
    private int idConsulta;

    @Column(name = "diagnostico", length = 200)
    private String diagnostico;

    @Column(name = "tratamiento", length = 200)
    private String tratamiento;

    @Column(name = "analisis", length = 200)
    private String analisis;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "idPaciente")
    private int idPaciente;

    @Column(name = "idDoctor")
    private int idDoctor;

    @Column(name = "estado", length = 50)
    private String estado = "Atendida";

    @Column(name = "hora", length = 10)
    private String hora = "10:00 AM";

    // Campos enriquecidos para relaciones en tiempo de consulta
    @Transient
    private String nombrePaciente;

    @Transient
    private String dniPaciente;

    @Transient
    private String nombreDoctor;

    @Transient
    private String especialidadDoctor;

    public Consulta() {
        this.fecha = new Date();
    }

    public Consulta(int idConsulta, String diagnostico, String tratamiento, String analisis, Date fecha, int idPaciente, int idDoctor) {
        this.idConsulta = idConsulta;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.analisis = analisis;
        this.fecha = fecha;
        this.idPaciente = idPaciente;
        this.idDoctor = idDoctor;
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getAnalisis() {
        return analisis;
    }

    public void setAnalisis(String analisis) {
        this.analisis = analisis;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    public String getEstado() {
        return estado != null ? estado : "Atendida";
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getHora() {
        return hora != null ? hora : "10:00 AM";
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getDniPaciente() {
        return dniPaciente;
    }

    public void setDniPaciente(String dniPaciente) {
        this.dniPaciente = dniPaciente;
    }

    public String getNombreDoctor() {
        return nombreDoctor;
    }

    public void setNombreDoctor(String nombreDoctor) {
        this.nombreDoctor = nombreDoctor;
    }

    public String getEspecialidadDoctor() {
        return especialidadDoctor;
    }

    public void setEspecialidadDoctor(String especialidadDoctor) {
        this.especialidadDoctor = especialidadDoctor;
    }
}
