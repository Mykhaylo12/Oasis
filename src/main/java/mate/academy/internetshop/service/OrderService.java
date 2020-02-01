package mate.academy.internetshop.service;

import java.util.List;

import mate.academy.internetshop.exeption.DataProcessingException;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;

public interface OrderService {
    Order create(Order order) throws DataProcessingException;

    Order get(Long orderId) throws DataProcessingException;

    Order update(Order order) throws DataProcessingException;

    void deleteById(Long orderId) throws DataProcessingException;

    void delete(Order order) throws DataProcessingException;

    Order completeOrder(List<Item> items, User user) throws DataProcessingException;

    List<Order> getUserOrders(User user) throws DataProcessingException;

    List<Order> getAll() throws DataProcessingException;
}
