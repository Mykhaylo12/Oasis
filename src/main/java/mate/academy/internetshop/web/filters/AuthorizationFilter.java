package mate.academy.internetshop.web.filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;

import static mate.academy.internetshop.model.Role.RoleName.ADMIN;
import static mate.academy.internetshop.model.Role.RoleName.USER;

public class AuthorizationFilter implements Filter {
    @Inject
    private static UserService userService;
    Map<String, Role.RoleName> protectedUrls = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        protectedUrls.put("/servlet/allUsers", ADMIN);
        protectedUrls.put("/servlet/addItem", ADMIN);
        protectedUrls.put("/servlet/deleteItem", ADMIN);
        protectedUrls.put("/servlet/bucketController", USER);
        protectedUrls.put("/servlet/orders", USER);
        protectedUrls.put("/servlet/allOrders", USER);
        protectedUrls.put("/servlet/addToBucket", USER);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            procesUnAuthentificaded(req, resp);
            return;
        }
        String requestedUrl = req.getServletPath();
        Role.RoleName roleName = protectedUrls.get(requestedUrl);
        if (roleName == null) {
            procesAuthentificaded(filterChain, req, resp);
            return;
        }
        String token = null;
        for (Cookie cookie : req.getCookies()) {
            if (cookie.getName().equals("MATE")) {
                token = cookie.getValue();
                break;
            }
        }
        if (token == null) {
            procesUnAuthentificaded(req, resp);
            return;
        } else {
            Optional<User> user = userService.getByToken(token);
            if (user.isPresent()) {
                if (verifyRole(user.get(), roleName)) {
                    procesAuthentificaded(filterChain, req, resp);
                } else {
                    proccesDenied(req, resp);
                    return;
                }
            } else {
                procesUnAuthentificaded(req, resp);
                return;
            }
        }
    }

    private boolean verifyRole(User user, Role.RoleName roleName) {
        return user.getRoles().stream().anyMatch(role -> role.getRoleName().equals(roleName));
    }

    private void proccesDenied(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/accessDenied.jsp").forward(req, resp);
    }

    private void procesAuthentificaded(FilterChain filterChain, HttpServletRequest req,
                                       HttpServletResponse resp)
            throws IOException, ServletException {
        filterChain.doFilter(req, resp);
    }

    private void procesUnAuthentificaded(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    public void destroy() {

    }
}
