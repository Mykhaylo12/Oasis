package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;
import org.apache.log4j.Logger;

@Dao
public class OrderDaoJdbcImpl extends AbstractDao<Order> implements OrderDao {
    private static Logger LOGGER = Logger.getLogger(OrderDaoJdbcImpl.class);

    public OrderDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Order create(Order order) {
        Order newOrder = order;
        String query = "INSERT INTO internet_shop.orders (user_id) VALUES (?);";
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, order.getUserId());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            while (rs.next()) {
                newOrder.setOrderId(rs.getLong(1));
            }
            addItemsToOrderInDB(newOrder, order.getItems());
            return newOrder;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    private void addItemsToOrderInDB(Order order, List<Item> items) {
        String query = "INSERT INTO internet_shop.orders_items_id (order_id, item_id)"
                + " VALUES (?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (Item item : items) {
                statement.setLong(1, order.getOrderId());
                statement.setLong(2, item.getItemId());
                int rows = statement.executeUpdate();
                LOGGER.info(rows + " rows were affected");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Order> get(Long orderId) {
        String query = "SELECT * FROM internet_shop.orders WHERE order_id = ?;";
        Order order = new Order();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, orderId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                order.setOrderId(rs.getLong("order_id"));
                order.setUserId(rs.getLong("user_id"));
            }
            order.setItems(addItemsToOrder(orderId));
            return Optional.of(order);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Item> addItemsToOrder(Long orderId) {
        List<Item> items = new ArrayList<>();
        String query = "SELECT internet_shop.items.item_id ,internet_shop.items.name,"
                + " internet_shop.items.price FROM internet_shop.items INNER JOIN"
                + " internet_shop.orders_items_id ON "
                + "internet_shop.orders_items_id.item_id=internet_shop.items.item_id"
                + " WHERE order_id =?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, orderId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                item.setItemId(rs.getLong("item_id"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getDouble("price"));
                items.add(item);
            }
            return items;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Order update(Order order) {
        List<Item> items = order.getItems();
        String query = "DELETE FROM internet_shop.orders_items_id WHERE order_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, order.getOrderId());
            statement.executeUpdate();
            addItemsToOrderInDB(order, items);
            return order;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(Long orderId) {
        String query = "DELETE FROM internet_shop.orders_items_id WHERE order_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, orderId);
            if (statement.executeUpdate() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Order order) {
        String query = "DELETE FROM internet_shop.orders_items_id WHERE order_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, order.getOrderId());
            if (statement.executeUpdate() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM internet_shop.orders;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getLong("order_id"));
                order.setUserId(rs.getLong("user_id"));
                order.setItems(addItemsToOrder(rs.getLong("order_id")));
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> getUserOrders(User user) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM internet_shop.orders WHERE user_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, user.getUserId());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getLong("order_id"));
                order.setUserId(rs.getLong("user_id"));
                order.setItems(addItemsToOrder(rs.getLong("order_id")));
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
