package mate.academy.internetshop.controller;

import mate.academy.internetshop.exeption.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CompleteOrderController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(CompleteOrderController.class);
    @Inject
    private static BucketService bucketService;
    @Inject
    private static OrderService orderService;
    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String userId = String.valueOf(req.getSession(true).getAttribute("userId"));
            Bucket bucket = bucketService.getByUserId(Long.valueOf(userId));
            User user = userService.get(Long.valueOf(userId));
            orderService.completeOrder(bucket.getItems(), user);
            bucketService.delete(bucket);
            resp.sendRedirect(req.getContextPath() + "/servlet/orders?user_id="
                    + bucket.getUserId());
        } catch (DataProcessingException e) {
            LOGGER.error(e);
            req.setAttribute("msg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/dbError.jsp").forward(req, resp);
        }
    }
}
