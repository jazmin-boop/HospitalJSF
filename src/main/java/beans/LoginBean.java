package beans;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Usuario;
import servicios.PacienteServicio;

@Named
@SessionScoped
public class LoginBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private Usuario usuarioLogueado;

    @Inject
    private PacienteServicio servicio;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }

    public void setUsuarioLogueado(Usuario usuarioLogueado) {
        this.usuarioLogueado = usuarioLogueado;
    }

    public boolean isAutenticado() {
        return usuarioLogueado != null;
    }

    public boolean isAdmin() {
        return usuarioLogueado != null && "ADMIN".equalsIgnoreCase(usuarioLogueado.getRol());
    }

    public boolean isDoctor() {
        return usuarioLogueado != null && "DOCTOR".equalsIgnoreCase(usuarioLogueado.getRol());
    }

    public String iniciarSesion() {
        Usuario u = servicio.autenticar(username, password);
        if (u != null) {
            this.usuarioLogueado = u;
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", u);
            return "index?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de Acceso", "Usuario o contraseña incorrectos. (Prueba: admin / admin123)"));
            return null;
        }
    }

    public String cerrarSesion() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        this.usuarioLogueado = null;
        return "login?faces-redirect=true";
    }
}
