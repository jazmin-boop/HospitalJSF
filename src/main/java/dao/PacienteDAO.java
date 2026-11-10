package dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.Paciente;

public class PacienteDAO {
    private Conexion cn = new Conexion();

    // 1. CREATE
    public void registrar(Paciente p) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = cn.getConexion();
            String sql = "INSERT INTO paciente(nombre, edad, dni, sexo, telefono, direccion, talla, peso, presion, temperatura, alergias) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getNombre());
            ps.setInt(2, p.getEdad());
            ps.setString(3, p.getDni());
            ps.setString(4, p.getSexo());
            ps.setString(5, p.getTelefono());
            ps.setString(6, p.getDireccion() != null ? p.getDireccion() : "Av. Principal 123");
            ps.setString(7, p.getTalla() != null ? p.getTalla() : "1.70 m");
            ps.setString(8, p.getPeso() != null ? p.getPeso() : "68.5 kg");
            ps.setString(9, p.getPresion() != null ? p.getPresion() : "120/80 mmHg");
            ps.setString(10, p.getTemperatura() != null ? p.getTemperatura() : "36.5 °C");
            ps.setString(11, p.getAlergias() != null ? p.getAlergias() : "Ninguna conocida");
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al registrar paciente: " + e);
        } finally {
            cerrar(con, ps, null);
        }
    }

    // 2. READ (Listar todos)
    public List<Paciente> listar() {
        List<Paciente> lista = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = cn.getConexion();
            String sql = "SELECT * FROM paciente ORDER BY idPaciente DESC";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (Exception e) {
            System.out.println("Error al listar pacientes: " + e);
        } finally {
            cerrar(con, ps, rs);
        }
        return lista;
    }

    // 3. READ (Buscar por ID)
    public Paciente buscarPorId(int id) {
        Paciente p = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = cn.getConexion();
            String sql = "SELECT * FROM paciente WHERE idPaciente = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                p = mapear(rs);
            }
        } catch (Exception e) {
            System.out.println("Error al buscar paciente: " + e);
        } finally {
            cerrar(con, ps, rs);
        }
        return p;
    }

    // 4. UPDATE
    public void actualizar(Paciente p) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = cn.getConexion();
            String sql = "UPDATE paciente SET nombre=?, edad=?, dni=?, sexo=?, telefono=?, direccion=?, talla=?, peso=?, presion=?, temperatura=?, alergias=? WHERE idPaciente=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getNombre());
            ps.setInt(2, p.getEdad());
            ps.setString(3, p.getDni());
            ps.setString(4, p.getSexo());
            ps.setString(5, p.getTelefono());
            ps.setString(6, p.getDireccion());
            ps.setString(7, p.getTalla());
            ps.setString(8, p.getPeso());
            ps.setString(9, p.getPresion());
            ps.setString(10, p.getTemperatura());
            ps.setString(11, p.getAlergias());
            ps.setInt(12, p.getIdPaciente());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al actualizar paciente: " + e);
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
            String sql = "DELETE FROM paciente WHERE idPaciente = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al eliminar paciente: " + e);
        } finally {
            cerrar(con, ps, null);
        }
    }

    private Paciente mapear(ResultSet rs) throws Exception {
        Paciente p = new Paciente();
        p.setIdPaciente(rs.getInt("idPaciente"));
        p.setNombre(rs.getString("nombre"));
        p.setEdad(rs.getInt("edad"));
        p.setDni(rs.getString("dni"));
        p.setSexo(rs.getString("sexo"));
        p.setTelefono(rs.getString("telefono"));
        try {
            p.setDireccion(rs.getString("direccion"));
            p.setTalla(rs.getString("talla"));
            p.setPeso(rs.getString("peso"));
            p.setPresion(rs.getString("presion"));
            p.setTemperatura(rs.getString("temperatura"));
            p.setAlergias(rs.getString("alergias"));
        } catch (Exception ignored) {
        }
        return p;
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
