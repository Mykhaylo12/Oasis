package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;

import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.IdGenerator;
import mate.academy.internetshop.model.Order;

@Dao
public class OrderDaoImpl implements OrderDao {

    @Override
    public Order create(Order order) {
        Order temp = order;
        temp.setOrderId(IdGenerator.orderIdGenerator());
        Storage.orders.add(temp);
        return order;
    }

    @Override
    public Order get(Long orderId) {
        return Storage.orders.stream()
                .filter(x -> x.getOrderId().equals(orderId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Order with id "
                        + orderId + " doesn't exist"));
    }

    @Override
    public Order update(Order order) {
        Order temp = Storage.orders.stream()
                .filter(x -> x.equals(order))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("This order doesn't exist"));
        Storage.orders.set(Storage.orders.indexOf(temp), order);
        return order;
    }

    @Override
    public void deleteById(Long orderId) {
        Order temp = Storage.orders.stream()
                .filter(x -> x.getOrderId().equals(orderId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("This order doesn't exist"));
        Storage.orders.remove(temp);
    }

    @Override
    public void delete(Order order) {
        Storage.orders.remove(order);
    }

    @Override
    public List<Order> getAll() {
        return Storage.orders;
    }
}
