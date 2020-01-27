package mate.academy.internetshop.controller;

import mate.academy.internetshop.exeption.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteUserController extends HttpServlet {
    private static Logger logger = LogManager.getLogger(DeleteUserController.class);
    @Inject
    public static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String userId = req.getParameter("user_id");
            userService.deleteById(Long.parseLong(userId));
        } catch (DataProcessingException e) {
            logger.error(e);
            req.setAttribute("msg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/dbError.jsp").forward(req, resp);
        }
        resp.sendRedirect(req.getContextPath() + "/servlet/allUsers");
    }
}
