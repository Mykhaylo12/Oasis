package mate.academy.internetshop.controller;

import mate.academy.internetshop.exeption.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.ItemService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteItemFromBucketController extends HttpServlet {
    private static Logger logger = LogManager.getLogger(DeleteItemFromBucketController.class);
    @Inject
    private static BucketService bucketService;
    @Inject
    private static ItemService itemService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String userId = String.valueOf(req.getSession(true).getAttribute("userId"));
        String itemId = req.getParameter("itemId");
        try {
            bucketService.deleteItem(bucketService.getByUserId(Long.parseLong(userId)),
                    itemService.get(Long.parseLong(itemId)));
        } catch (DataProcessingException e) {
            logger.error(e);
            req.setAttribute("msg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/dbError.jsp").forward(req, resp);
        }
        resp.sendRedirect(req.getContextPath() + "/servlet/bucketController");
    }
}
