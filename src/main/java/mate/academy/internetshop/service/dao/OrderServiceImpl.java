package mate.academy.internetshop.service.dao;

import java.util.ArrayList;
import java.util.List;

import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
    @Inject
    public static OrderDao orderDao;

    @Override
    public Order create(Order order) {
        return orderDao.create(order);
    }

    @Override
    public Order get(Long orderId) {
        return orderDao.get(orderId).get();
    }

    @Override
    public Order update(Order order) {
        return orderDao.update(order);
    }

    @Override
    public void deleteById(Long orderId) {
        orderDao.deleteById(orderId);
    }

    @Override
    public void delete(Order order) {
        orderDao.delete(order);
    }

    @Override
    public Order completeOrder(List<Item> items, User user) {
        Order order = new Order(user, items);
        return create(order);
    }

    @Override
    public List<Order> getUserOrders(User user) {
        List<Order> orderList = new ArrayList<>();
        for (Order order : orderDao.getAll()) {
            if (order.getUserId().equals(user.getUserId())) {
                orderList.add(order);
            }
        }
        return orderList;
    }
}
