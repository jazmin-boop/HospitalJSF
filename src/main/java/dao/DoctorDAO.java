package dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.Doctor;

public class DoctorDAO {
    private Conexion cn = new Conexion();

    // 1. CREATE
    public void registrar(Doctor d) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = cn.getConexion();
            String sql = "INSERT INTO doctor(nombre, especialidad, telefono, email) VALUES(?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, d.getNombre());
            ps.setString(2, d.getEspecialidad());
            ps.setString(3, d.getTelefono());
            ps.setString(4, d.getEmail());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al registrar doctor: " + e);
        } finally {
            cerrar(con, ps, null);
        }
    }

    // 2. READ (Listar)
    public List<Doctor> listar() {
        List<Doctor> lista = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = cn.getConexion();
            ps = con.prepareStatement("SELECT * FROM doctor ORDER BY idDoctor DESC");
            rs = ps.executeQuery();
            while (rs.next()) {
                Doctor d = new Doctor();
                d.setIdDoctor(rs.getInt("idDoctor"));
                d.setNombre(rs.getString("nombre"));
                d.setEspecialidad(rs.getString("especialidad"));
                d.setTelefono(rs.getString("telefono"));
                d.setEmail(rs.getString("email"));
                lista.add(d);
            }
        } catch (Exception e) {
            System.out.println("Error al listar doctores: " + e);
        } finally {
            cerrar(con, ps, rs);
        }
        return lista;
    }

    // 3. READ (Buscar por ID)
    public Doctor buscarPorId(int id) {
        Doctor d = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = cn.getConexion();
            ps = con.prepareStatement("SELECT * FROM doctor WHERE idDoctor = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                d = new Doctor();
                d.setIdDoctor(rs.getInt("idDoctor"));
                d.setNombre(rs.getString("nombre"));
                d.setEspecialidad(rs.getString("especialidad"));
                d.setTelefono(rs.getString("telefono"));
                d.setEmail(rs.getString("email"));
            }
        } catch (Exception e) {
            System.out.println("Error al buscar doctor: " + e);
        } finally {
            cerrar(con, ps, rs);
        }
        return d;
    }

    // 4. UPDATE
    public void actualizar(Doctor d) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = cn.getConexion();
            String sql = "UPDATE doctor SET nombre=?, especialidad=?, telefono=?, email=? WHERE idDoctor=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, d.getNombre());
            ps.setString(2, d.getEspecialidad());
            ps.setString(3, d.getTelefono());
            ps.setString(4, d.getEmail());
            ps.setInt(5, d.getIdDoctor());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al actualizar doctor: " + e);
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
            ps = con.prepareStatement("DELETE FROM doctor WHERE idDoctor = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al eliminar doctor: " + e);
        } finally {
            cerrar(con, ps, null);
        }
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
