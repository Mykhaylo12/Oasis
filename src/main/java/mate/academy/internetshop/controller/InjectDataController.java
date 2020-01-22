package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;

public class InjectDataController extends HttpServlet {
    @Inject
    private static UserService userService;
    @Inject
    private static ItemDao itemDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new User();
        user.addRole(Role.of("USER"));
        user.setLogin("user");
        user.setPassword("1");
        user.setEmail("email");
        user.setName("user");
        userService.create(user);

        User admin = new User();
        admin.addRole(Role.of("ADMIN"));
        admin.setLogin("admin");
        admin.setPassword("1");
        admin.setEmail("email");
        admin.setName("admin");
        userService.create(admin);

        Item item = new Item();
        item.setItemId(1L);
        item.setPrice(800.0);
        item.setName("HTC");
        itemDao.update(item);

        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
