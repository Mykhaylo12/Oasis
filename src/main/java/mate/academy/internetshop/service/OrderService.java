package mate.academy.internetshop.service;

import java.util.List;

import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;

public interface OrderService {
    Order create(Order order);

    Order get(Long orderId);

    Order update(Order order);

    void deleteById(Long orderId);

    void delete(Order order);

    Order completeOrder(List<Item> items, User user);

    List<Order> getUserOrders(User user);
}
