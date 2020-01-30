package mate.academy.internetshop.controller;

import mate.academy.internetshop.exeption.DataProcessingException;
import mate.academy.internetshop.exeption.LoginExistExeption;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegistrationController extends HttpServlet {
    private static Logger logger = LogManager.getLogger(AddItemToBucketController.class);
    @Inject
    public static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            User newUser = new User();
            newUser.setLogin(req.getParameter("login"));
            newUser.setPassword(req.getParameter("psw"));
            newUser.setEmail(req.getParameter("email"));
            newUser.setName(req.getParameter("name"));
            newUser.addRole(Role.of("USER"));
            User user = userService.create(newUser);

            HttpSession session = req.getSession(true);
            session.setAttribute("userId", user.getUserId());

            Cookie cookie = new Cookie("MATE", user.getToken());
            resp.addCookie(cookie);
        } catch (LoginExistExeption e) {
            logger.error(e);
            req.setAttribute("errorMsg", "Login already exist");
            req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
        } catch (DataProcessingException e) {
            logger.error(e);
            req.setAttribute("msg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/dbError.jsp").forward(req, resp);
        }
        resp.sendRedirect(req.getContextPath() + "/mainMenu");
    }
}
