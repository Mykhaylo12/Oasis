package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.exeption.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.service.OrderService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class DeleteUserOrderController extends HttpServlet {
    private static Logger logger = LogManager.getLogger(DeleteUserOrderController.class);
    @Inject
    private static OrderService orderService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String orderId = req.getParameter("order_id");
            Order order = orderService.get(Long.valueOf(orderId));
            orderService.deleteById(Long.valueOf(orderId));
            resp.sendRedirect(req.getContextPath() + "/servlet/orders?user_id="
                    + order.getUserId());
        } catch (DataProcessingException e) {
            logger.error(e);
            req.setAttribute("msg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/dbError.jsp").forward(req, resp);
        }
    }
}
