package dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.Triaje;

public class TriajeDAO {
    private Conexion cn = new Conexion();

    // 1. CREATE
    public void registrar(Triaje t) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = cn.getConexion();
            String sql = "INSERT INTO triaje(idPaciente, fecha, hora, presion, temperatura, frecuenciaCardiaca, "
                       + "frecuenciaRespiratoria, saturacionOxigeno, peso, talla, imc, estadoNutricional, "
                       + "prioridad, tipoSeguro, codigoAsegurado, motivoConsulta, destinoConsultorio, estado) "
                       + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, t.getIdPaciente());
            ps.setDate(2, new Date(t.getFecha() != null ? t.getFecha().getTime() : System.currentTimeMillis()));
            ps.setString(3, t.getHora());
            ps.setString(4, t.getPresion());
            ps.setString(5, t.getTemperatura());
            ps.setString(6, t.getFrecuenciaCardiaca());
            ps.setString(7, t.getFrecuenciaRespiratoria());
            ps.setString(8, t.getSaturacionOxigeno());
            ps.setString(9, t.getPeso());
            ps.setString(10, t.getTalla());
            ps.setString(11, t.getImc());
            ps.setString(12, t.getEstadoNutricional());
            ps.setString(13, t.getPrioridad());
            ps.setString(14, t.getTipoSeguro());
            ps.setString(15, t.getCodigoAsegurado());
            ps.setString(16, t.getMotivoConsulta());
            ps.setString(17, t.getDestinoConsultorio());
            ps.setString(18, t.getEstado());
            ps.executeUpdate();

            // Sincronizar en la tabla paciente sus signos vitales de triaje
            try (PreparedStatement psPac = con.prepareStatement("UPDATE paciente SET talla=?, peso=?, presion=?, temperatura=? WHERE idPaciente=?")) {
                psPac.setString(1, t.getTalla());
                psPac.setString(2, t.getPeso());
                psPac.setString(3, t.getPresion());
                psPac.setString(4, t.getTemperatura());
                psPac.setInt(5, t.getIdPaciente());
                psPac.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("Error al registrar triaje: " + e);
        } finally {
            cerrar(con, ps, null);
        }
    }

    // 2. READ (Listar todos)
    public List<Triaje> listar() {
        List<Triaje> lista = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = cn.getConexion();
            String sql = "SELECT t.*, p.nombre AS nombrePaciente, p.dni AS dniPaciente, p.edad AS edadPaciente, p.sexo AS sexoPaciente "
                       + "FROM triaje t "
                       + "LEFT JOIN paciente p ON t.idPaciente = p.idPaciente "
                       + "ORDER BY t.fecha DESC, t.idTriaje DESC";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (Exception e) {
            System.out.println("Error al listar triajes: " + e);
        } finally {
            cerrar(con, ps, rs);
        }
        return lista;
    }

    // 3. READ (Buscar por ID)
    public Triaje buscarPorId(int id) {
        Triaje t = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = cn.getConexion();
            String sql = "SELECT t.*, p.nombre AS nombrePaciente, p.dni AS dniPaciente, p.edad AS edadPaciente, p.sexo AS sexoPaciente "
                       + "FROM triaje t "
                       + "LEFT JOIN paciente p ON t.idPaciente = p.idPaciente "
                       + "WHERE t.idTriaje = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                t = mapear(rs);
            }
        } catch (Exception e) {
            System.out.println("Error al buscar triaje: " + e);
        } finally {
            cerrar(con, ps, rs);
        }
        return t;
    }

    // 4. UPDATE
    public void actualizar(Triaje t) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = cn.getConexion();
            String sql = "UPDATE triaje SET idPaciente=?, fecha=?, hora=?, presion=?, temperatura=?, frecuenciaCardiaca=?, "
                       + "frecuenciaRespiratoria=?, saturacionOxigeno=?, peso=?, talla=?, imc=?, estadoNutricional=?, "
                       + "prioridad=?, tipoSeguro=?, codigoAsegurado=?, motivoConsulta=?, destinoConsultorio=?, estado=? "
                       + "WHERE idTriaje=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, t.getIdPaciente());
            ps.setDate(2, new Date(t.getFecha() != null ? t.getFecha().getTime() : System.currentTimeMillis()));
            ps.setString(3, t.getHora());
            ps.setString(4, t.getPresion());
            ps.setString(5, t.getTemperatura());
            ps.setString(6, t.getFrecuenciaCardiaca());
            ps.setString(7, t.getFrecuenciaRespiratoria());
            ps.setString(8, t.getSaturacionOxigeno());
            ps.setString(9, t.getPeso());
            ps.setString(10, t.getTalla());
            ps.setString(11, t.getImc());
            ps.setString(12, t.getEstadoNutricional());
            ps.setString(13, t.getPrioridad());
            ps.setString(14, t.getTipoSeguro());
            ps.setString(15, t.getCodigoAsegurado());
            ps.setString(16, t.getMotivoConsulta());
            ps.setString(17, t.getDestinoConsultorio());
            ps.setString(18, t.getEstado());
            ps.setInt(19, t.getIdTriaje());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al actualizar triaje: " + e);
        } finally {
            cerrar(con, ps, null);
        }
    }

    // 5. DELETE
    public void eliminar(int id) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = cn.getConexion();
            String sql = "DELETE FROM triaje WHERE idTriaje = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al eliminar triaje: " + e);
        } finally {
            cerrar(con, ps, null);
        }
    }

    private Triaje mapear(ResultSet rs) throws Exception {
        Triaje t = new Triaje();
        t.setIdTriaje(rs.getInt("idTriaje"));
        t.setIdPaciente(rs.getInt("idPaciente"));
        t.setFecha(rs.getDate("fecha"));
        t.setHora(rs.getString("hora"));
        t.setPresion(rs.getString("presion"));
        t.setTemperatura(rs.getString("temperatura"));
        t.setFrecuenciaCardiaca(rs.getString("frecuenciaCardiaca"));
        t.setFrecuenciaRespiratoria(rs.getString("frecuenciaRespiratoria"));
        t.setSaturacionOxigeno(rs.getString("saturacionOxigeno"));
        t.setPeso(rs.getString("peso"));
        t.setTalla(rs.getString("talla"));
        t.setImc(rs.getString("imc"));
        t.setEstadoNutricional(rs.getString("estadoNutricional"));
        t.setPrioridad(rs.getString("prioridad"));
        t.setTipoSeguro(rs.getString("tipoSeguro"));
        t.setCodigoAsegurado(rs.getString("codigoAsegurado"));
        t.setMotivoConsulta(rs.getString("motivoConsulta"));
        t.setDestinoConsultorio(rs.getString("destinoConsultorio"));
        t.setEstado(rs.getString("estado"));

        try {
            t.setNombrePaciente(rs.getString("nombrePaciente"));
            t.setDniPaciente(rs.getString("dniPaciente"));
            t.setEdadPaciente(rs.getInt("edadPaciente"));
            t.setSexoPaciente(rs.getString("sexoPaciente"));
        } catch (Exception ignored) {
        }
        return t;
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
