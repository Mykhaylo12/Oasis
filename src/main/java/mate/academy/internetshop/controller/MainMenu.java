package mate.academy.internetshop.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.lib.Inject;

public class MainMenu extends HttpServlet {
    @Inject
    private static ItemDao itemDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        itemDao.get(1L);
        req.getRequestDispatcher("/WEB-INF/views/mainMenu.jsp").forward(req, resp);
    }
}
