package mate.academy.internetshop.service.dao;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.exeption.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
    @Inject
    private static OrderDao orderDao;

    @Override
    public Order create(Order order) throws DataProcessingException {
        return orderDao.create(order);
    }

    @Override
    public Order get(Long orderId) throws DataProcessingException {
        return orderDao.get(orderId).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Order update(Order order) throws DataProcessingException {
        return orderDao.update(order);
    }

    @Override
    public boolean deleteById(Long orderId) throws DataProcessingException {
        return orderDao.deleteById(orderId);
    }

    @Override
    public boolean delete(Order order) throws DataProcessingException {
        return orderDao.delete(order);
    }

    @Override
    public Order completeOrder(List<Item> items, User user) throws DataProcessingException {
        Order order = new Order(user, items);
        return create(order);
    }

    @Override
    public List<Order> getUserOrders(User user) throws DataProcessingException {
        orderDao.getUserOrders(user);
        return orderDao.getAll().stream()
                .filter(x -> x.getUserId().equals(user.getUserId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> getAll() throws DataProcessingException {
        return orderDao.getAll();
    }
}
