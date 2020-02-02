package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.exeption.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.Logger;

public class InjectDefaultAdminController extends HttpServlet {
    @Inject
    private static UserService userService;

    private Logger logger = Logger.getLogger(RegistrationController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User admin = new User();
        admin.setLogin("admin");
        admin.setName("admin");
        admin.setEmail("email");
        admin.setPassword("1");
        admin.addRole(Role.of("ADMIN"));
        try {
            userService.create(admin);
        } catch (DataProcessingException e) {
            logger.error(e);
            req.setAttribute("errorMsg", "Login already exist");
            req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
        }
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }
}

