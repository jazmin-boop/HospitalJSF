package filtro;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Usuario;

@WebFilter(filterName = "SeguridadFilter", urlPatterns = {"*.xhtml"})
public class SeguridadFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String requestURI = req.getRequestURI();

        boolean esLoginPage = requestURI.contains("login.xhtml");
        boolean esRecurso = requestURI.contains("javax.faces.resource") || requestURI.contains("/resources/");
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuario") : null;

        if (esLoginPage || esRecurso) {
            chain.doFilter(request, response);
            return;
        }

        if (usuario == null) {
            res.sendRedirect(req.getContextPath() + "/login.xhtml");
            return;
        }

        // Control de Acceso por Roles (RBAC): Sólo ADMIN puede gestionar doctores
        if (requestURI.contains("doctor.xhtml") && !"ADMIN".equalsIgnoreCase(usuario.getRol())) {
            res.sendRedirect(req.getContextPath() + "/index.xhtml");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
