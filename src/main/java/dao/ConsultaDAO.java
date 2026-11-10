package dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.Consulta;

public class ConsultaDAO {
    private Conexion cn = new Conexion();

    // 1. CREATE
    public void registrar(Consulta c) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = cn.getConexion();
            String sql = "INSERT INTO consulta(diagnostico, tratamiento, analisis, fecha, idPaciente, idDoctor, estado, hora) VALUES(?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, c.getDiagnostico());
            ps.setString(2, c.getTratamiento());
            ps.setString(3, c.getAnalisis());
            ps.setDate(4, c.getFecha() != null ? new java.sql.Date(c.getFecha().getTime()) : new java.sql.Date(System.currentTimeMillis()));
            ps.setInt(5, c.getIdPaciente() > 0 ? c.getIdPaciente() : 1);
            ps.setInt(6, c.getIdDoctor() > 0 ? c.getIdDoctor() : 1);
            ps.setString(7, c.getEstado() != null ? c.getEstado() : "Atendida");
            ps.setString(8, c.getHora() != null ? c.getHora() : "10:00 AM");
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al registrar consulta: " + e);
        } finally {
            cerrar(con, ps, null);
        }
    }

    // 2. READ (Listar todas con nombres de Paciente y Doctor)
    public List<Consulta> listar() {
        List<Consulta> lista = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = cn.getConexion();
            String sql = "SELECT c.*, p.nombre AS nombrePaciente, p.dni AS dniPaciente, "
                       + "d.nombre AS nombreDoctor, d.especialidad AS especialidadDoctor "
                       + "FROM consulta c "
                       + "LEFT JOIN paciente p ON c.idPaciente = p.idPaciente "
                       + "LEFT JOIN doctor d ON c.idDoctor = d.idDoctor "
                       + "ORDER BY c.fecha DESC, c.idConsulta DESC";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearConsulta(rs));
            }
        } catch (Exception e) {
            System.out.println("Error al listar consultas: " + e);
        } finally {
            cerrar(con, ps, rs);
        }
        return lista;
    }

    // 3. READ (Historial Médico por Paciente)
    public List<Consulta> listarPorPaciente(int idPaciente) {
        List<Consulta> lista = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = cn.getConexion();
            String sql = "SELECT c.*, p.nombre AS nombrePaciente, p.dni AS dniPaciente, "
                       + "d.nombre AS nombreDoctor, d.especialidad AS especialidadDoctor "
                       + "FROM consulta c "
                       + "LEFT JOIN paciente p ON c.idPaciente = p.idPaciente "
                       + "LEFT JOIN doctor d ON c.idDoctor = d.idDoctor "
                       + "WHERE c.idPaciente = ? "
                       + "ORDER BY c.fecha DESC, c.idConsulta DESC";
            ps = con.prepareStatement(sql);
            ps.setInt(1, idPaciente);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearConsulta(rs));
            }
        } catch (Exception e) {
            System.out.println("Error al obtener historial médico: " + e);
        } finally {
            cerrar(con, ps, rs);
        }
        return lista;
    }

    // 4. READ (Próxima Consulta para Dashboard)
    public Consulta obtenerProximaConsulta() {
        Consulta c = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = cn.getConexion();
            String sql = "SELECT c.*, p.nombre AS nombrePaciente, p.dni AS dniPaciente, "
                       + "d.nombre AS nombreDoctor, d.especialidad AS especialidadDoctor "
                       + "FROM consulta c "
                       + "LEFT JOIN paciente p ON c.idPaciente = p.idPaciente "
                       + "LEFT JOIN doctor d ON c.idDoctor = d.idDoctor "
                       + "ORDER BY c.idConsulta DESC LIMIT 1";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                c = mapearConsulta(rs);
            }
        } catch (Exception e) {
            System.out.println("Error al obtener próxima consulta: " + e);
        } finally {
            cerrar(con, ps, rs);
        }
        return c;
    }

    // 5. UPDATE
    public void actualizar(Consulta c) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = cn.getConexion();
            String sql = "UPDATE consulta SET diagnostico=?, tratamiento=?, analisis=?, fecha=?, idPaciente=?, idDoctor=?, estado=?, hora=? WHERE idConsulta=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, c.getDiagnostico());
            ps.setString(2, c.getTratamiento());
            ps.setString(3, c.getAnalisis());
            ps.setDate(4, c.getFecha() != null ? new java.sql.Date(c.getFecha().getTime()) : new java.sql.Date(System.currentTimeMillis()));
            ps.setInt(5, c.getIdPaciente() > 0 ? c.getIdPaciente() : 1);
            ps.setInt(6, c.getIdDoctor() > 0 ? c.getIdDoctor() : 1);
            ps.setString(7, c.getEstado() != null ? c.getEstado() : "Atendida");
            ps.setString(8, c.getHora() != null ? c.getHora() : "10:00 AM");
            ps.setInt(9, c.getIdConsulta());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al actualizar consulta: " + e);
        } finally {
            cerrar(con, ps, null);
        }
    }

    // 6. DELETE
    public void eliminar(int id) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = cn.getConexion();
            ps = con.prepareStatement("DELETE FROM consulta WHERE idConsulta = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al eliminar consulta: " + e);
        } finally {
            cerrar(con, ps, null);
        }
    }

    private Consulta mapearConsulta(ResultSet rs) throws Exception {
        Consulta c = new Consulta();
        c.setIdConsulta(rs.getInt("idConsulta"));
        c.setDiagnostico(rs.getString("diagnostico"));
        c.setTratamiento(rs.getString("tratamiento"));
        c.setAnalisis(rs.getString("analisis"));
        c.setFecha(rs.getDate("fecha"));
        c.setIdPaciente(rs.getInt("idPaciente"));
        c.setIdDoctor(rs.getInt("idDoctor"));
        c.setEstado(rs.getString("estado"));
        c.setHora(rs.getString("hora"));
        c.setNombrePaciente(rs.getString("nombrePaciente"));
        c.setDniPaciente(rs.getString("dniPaciente"));
        c.setNombreDoctor(rs.getString("nombreDoctor"));
        c.setEspecialidadDoctor(rs.getString("especialidadDoctor"));
        return c;
    }

    private void cerrar(Connection con, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (Exception e) {
            System.out.println("Error al cerrar recursos: " + e);
        }
    }
}
