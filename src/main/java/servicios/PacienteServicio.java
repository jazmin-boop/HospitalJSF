package servicios;

import dao.ConsultaDAO;
import dao.DoctorDAO;
import dao.PacienteDAO;
import dao.TriajeDAO;
import dao.UsuarioDAO;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import modelo.Consulta;
import modelo.Doctor;
import modelo.Paciente;
import modelo.Triaje;
import modelo.Usuario;

@ApplicationScoped
public class PacienteServicio implements Serializable {
    private static final long serialVersionUID = 1L;

    private PacienteDAO pacienteDAO = new PacienteDAO();
    private DoctorDAO doctorDAO = new DoctorDAO();
    private ConsultaDAO consultaDAO = new ConsultaDAO();
    private TriajeDAO triajeDAO = new TriajeDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    // ================= PACIENTES (CRUD) =================
    public void guardarPaciente(Paciente p) {
        if (p.getIdPaciente() > 0) {
            pacienteDAO.actualizar(p);
        } else {
            pacienteDAO.registrar(p);
        }
    }

    public List<Paciente> listarPacientes() {
        return pacienteDAO.listar();
    }

    public Paciente buscarPacientePorId(int id) {
        return pacienteDAO.buscarPorId(id);
    }

    public void eliminarPaciente(int id) {
        pacienteDAO.eliminar(id);
    }

    public int contarPacientes() {
        List<Paciente> lista = pacienteDAO.listar();
        return lista != null ? lista.size() : 0;
    }

    // ================= DOCTORES (CRUD) =================
    public void guardarDoctor(Doctor d) {
        if (d.getIdDoctor() > 0) {
            doctorDAO.actualizar(d);
        } else {
            doctorDAO.registrar(d);
        }
    }

    public List<Doctor> listarDoctores() {
        return doctorDAO.listar();
    }

    public Doctor buscarDoctorPorId(int id) {
        return doctorDAO.buscarPorId(id);
    }

    public void eliminarDoctor(int id) {
        doctorDAO.eliminar(id);
    }

    public int contarDoctores() {
        List<Doctor> lista = doctorDAO.listar();
        return lista != null ? lista.size() : 0;
    }

    // ================= CONSULTAS & HISTORIAL MÉDICO (CRUD) =================
    public void guardarConsulta(Consulta c) {
        if (c.getIdConsulta() > 0) {
            consultaDAO.actualizar(c);
        } else {
            consultaDAO.registrar(c);
        }
    }

    public List<Consulta> listarConsultas() {
        return consultaDAO.listar();
    }

    public List<Consulta> listarHistorialPorPaciente(int idPaciente) {
        return consultaDAO.listarPorPaciente(idPaciente);
    }

    public Consulta obtenerProximaConsulta() {
        return consultaDAO.obtenerProximaConsulta();
    }

    public void eliminarConsulta(int id) {
        consultaDAO.eliminar(id);
    }

    public int contarConsultas() {
        List<Consulta> lista = consultaDAO.listar();
        return lista != null ? lista.size() : 0;
    }

    // ================= TRIAJE Y SIGNOS VITALES (CRUD) =================
    public void guardarTriaje(Triaje t) {
        if (t.getIdTriaje() > 0) {
            triajeDAO.actualizar(t);
        } else {
            triajeDAO.registrar(t);
        }
    }

    public List<Triaje> listarTriajes() {
        return triajeDAO.listar();
    }

    public Triaje buscarTriajePorId(int id) {
        return triajeDAO.buscarPorId(id);
    }

    public void eliminarTriaje(int id) {
        triajeDAO.eliminar(id);
    }

    public int contarTriajes() {
        List<Triaje> lista = triajeDAO.listar();
        return lista != null ? lista.size() : 0;
    }

    // ================= SEGURIDAD Y LOGIN =================
    public Usuario autenticar(String username, String password) {
        return usuarioDAO.autenticar(username, password);
    }
}
