package mate.academy.internetshop.controller;

import mate.academy.internetshop.exeption.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.service.ItemService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetAlllItemsController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(GetAlllItemsController.class);
    @Inject
    private static ItemService itemService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            List items = itemService.getAll();
            req.setAttribute("items", items);
            req.getRequestDispatcher("/WEB-INF/views/allItems.jsp").forward(req, resp);
        } catch (DataProcessingException e) {
            LOGGER.error(e);
            req.setAttribute("msg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/dbError.jsp").forward(req, resp);
        }
    }
}
