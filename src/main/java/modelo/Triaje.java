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
@Table(name = "triaje")
public class Triaje implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTriaje")
    private int idTriaje;

    @Column(name = "idPaciente", nullable = false)
    private int idPaciente;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha", nullable = false)
    private Date fecha = new Date();

    @Column(name = "hora", length = 10, nullable = false)
    private String hora = "10:00 AM";

    @Column(name = "presion", length = 20)
    private String presion = "120/80 mmHg";

    @Column(name = "temperatura", length = 10)
    private String temperatura = "36.5 °C";

    @Column(name = "frecuenciaCardiaca", length = 10)
    private String frecuenciaCardiaca = "72 lpm";

    @Column(name = "frecuenciaRespiratoria", length = 10)
    private String frecuenciaRespiratoria = "18 rpm";

    @Column(name = "saturacionOxigeno", length = 10)
    private String saturacionOxigeno = "98 %";

    @Column(name = "peso", length = 10)
    private String peso = "68.5 kg";

    @Column(name = "talla", length = 10)
    private String talla = "1.70 m";

    @Column(name = "imc", length = 10)
    private String imc = "23.7";

    @Column(name = "estadoNutricional", length = 50)
    private String estadoNutricional = "Normal";

    @Column(name = "prioridad", length = 40)
    private String prioridad = "Prioridad III (Urgencia Menor)";

    @Column(name = "tipoSeguro", length = 50)
    private String tipoSeguro = "Seguro Integral";

    @Column(name = "codigoAsegurado", length = 30)
    private String codigoAsegurado = "01-004-12345678";

    @Column(name = "motivoConsulta", length = 255)
    private String motivoConsulta = "Control de rutina y signos vitales";

    @Column(name = "destinoConsultorio", length = 100)
    private String destinoConsultorio = "Consultorio 1 - Cardiología";

    @Column(name = "estado", length = 30)
    private String estado = "Triado";

    // Campos auxiliares en memoria para mostrar el paciente
    @Transient
    private String nombrePaciente;

    @Transient
    private String dniPaciente;

    @Transient
    private int edadPaciente;

    @Transient
    private String sexoPaciente;

    public Triaje() {
    }

    public int getIdTriaje() {
        return idTriaje;
    }

    public void setIdTriaje(int idTriaje) {
        this.idTriaje = idTriaje;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getPresion() {
        return presion;
    }

    public void setPresion(String presion) {
        this.presion = presion;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public String getFrecuenciaCardiaca() {
        return frecuenciaCardiaca;
    }

    public void setFrecuenciaCardiaca(String frecuenciaCardiaca) {
        this.frecuenciaCardiaca = frecuenciaCardiaca;
    }

    public String getFrecuenciaRespiratoria() {
        return frecuenciaRespiratoria;
    }

    public void setFrecuenciaRespiratoria(String frecuenciaRespiratoria) {
        this.frecuenciaRespiratoria = frecuenciaRespiratoria;
    }

    public String getSaturacionOxigeno() {
        return saturacionOxigeno;
    }

    public void setSaturacionOxigeno(String saturacionOxigeno) {
        this.saturacionOxigeno = saturacionOxigeno;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getImc() {
        return imc;
    }

    public void setImc(String imc) {
        this.imc = imc;
    }

    public String getEstadoNutricional() {
        return estadoNutricional;
    }

    public void setEstadoNutricional(String estadoNutricional) {
        this.estadoNutricional = estadoNutricional;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getTipoSeguro() {
        return tipoSeguro;
    }

    public void setTipoSeguro(String tipoSeguro) {
        this.tipoSeguro = tipoSeguro;
    }

    public String getCodigoAsegurado() {
        return codigoAsegurado;
    }

    public void setCodigoAsegurado(String codigoAsegurado) {
        this.codigoAsegurado = codigoAsegurado;
    }

    public String getMotivoConsulta() {
        return motivoConsulta;
    }

    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }

    public String getDestinoConsultorio() {
        return destinoConsultorio;
    }

    public void setDestinoConsultorio(String destinoConsultorio) {
        this.destinoConsultorio = destinoConsultorio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public int getEdadPaciente() {
        return edadPaciente;
    }

    public void setEdadPaciente(int edadPaciente) {
        this.edadPaciente = edadPaciente;
    }

    public String getSexoPaciente() {
        return sexoPaciente;
    }

    public void setSexoPaciente(String sexoPaciente) {
        this.sexoPaciente = sexoPaciente;
    }
}
