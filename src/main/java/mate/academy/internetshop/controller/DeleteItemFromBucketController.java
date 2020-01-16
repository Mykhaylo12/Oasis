package mate.academy.internetshop.controller;

import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.ItemService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteItemFromBucketController extends HttpServlet {
    @Inject
    private static BucketService bucketService;
    @Inject
    private static ItemService itemService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String userId = String.valueOf(req.getSession(true).getAttribute("userId"));
        String itemId = req.getParameter("itemId");
        bucketService.deleteItem(bucketService.getByUserId(Long.parseLong(userId)),
                itemService.get(Long.parseLong(itemId)));
        resp.sendRedirect(req.getContextPath() + "/servlet/bucketController");
    }
}
