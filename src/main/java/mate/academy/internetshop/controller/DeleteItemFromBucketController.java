package mate.academy.internetshop.controller;

import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
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
        String bucketId = req.getParameter("bucket_id");
        String itemId = req.getParameter("itemId");
        Bucket bucket = bucketService.get(Long.parseLong(bucketId));
        Item item = itemService.get(Long.parseLong(itemId));
        bucketService.deleteItem(bucket, item);
        resp.sendRedirect(req.getContextPath() + "/bucketController");
    }
}
