package mate.academy.internetshop.dao;

import java.util.List;

import mate.academy.internetshop.model.Order;

public interface OrderDao {
    Order create(Order order);

    Order get(Long orderId);

    Order update(Order order);

    void deleteById(Long orderId);

    void delete(Order order);

    List<Order> getAll();
}
