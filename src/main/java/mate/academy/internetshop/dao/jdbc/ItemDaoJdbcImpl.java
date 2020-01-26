package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Item;
import org.apache.log4j.Logger;

@Dao
public class ItemDaoJdbcImpl extends AbstractDao<Item> implements ItemDao {
    private static Logger LOGGER = Logger.getLogger(ItemDaoJdbcImpl.class);

    public ItemDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Item create(Item item) {
        String query = "INSERT INTO internet_shop.items (name, price) VALUES(?, ?)";

        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, item.getName());
            preparedStatement.setDouble(2, item.getPrice());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                item.setItemId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return item;
    }

    @Override
    public Optional<Item> get(Long id) {
        String query = "SELECT * FROM internet_shop.items WHERE item_id=?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                long itemId = rs.getLong("item_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                Item item = new Item();
                item.setName(name);
                item.setPrice(price);
                item.setItemId(itemId);
                return Optional.of(item);
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return Optional.empty();
    }

    @Override
    public Item update(Item item) {
        String query = "update internet_shop.items set name= ? ,price= ? where item_id= ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, item.getName());
            preparedStatement.setDouble(2, item.getPrice());
            preparedStatement.setLong(3, item.getItemId());
            int res = preparedStatement.executeUpdate();
            LOGGER.info(res + " row(s) was effected");
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return item;
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "delete from internet_shop.items where item_id=?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            int res = preparedStatement.executeUpdate();
            LOGGER.info(res + " row(s) was effected");
            return true;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public boolean delete(Item item) {
        String query = "delete from internet_shop.items where item_id=?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, item.getItemId());
            int res = preparedStatement.executeUpdate(query);
            LOGGER.info(res + " row(s) was(ware) effected");
            return true;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Item> getAll() {
        List<Item> allItems = new ArrayList<>();
        String query = "select * from internet_shop.items";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet rs = preparedStatement.executeQuery(query);
            while (rs.next()) {
                long itemId = rs.getLong("item_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                Item item = new Item();
                item.setName(name);
                item.setPrice(price);
                item.setItemId(itemId);
                allItems.add(item);
            }
            return allItems;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
