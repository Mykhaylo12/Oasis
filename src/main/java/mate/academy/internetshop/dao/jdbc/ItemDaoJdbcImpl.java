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
import mate.academy.internetshop.exeption.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Item;

@Dao
public class ItemDaoJdbcImpl extends AbstractDao<Item> implements ItemDao {
    public ItemDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Item create(Item item) throws DataProcessingException {
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
            return item;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to create item: " + e);
        }
    }

    @Override
    public Optional<Item> get(Long id) throws DataProcessingException {
        String query = "SELECT * FROM internet_shop.items WHERE item_id=?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            Item item = new Item();
            while (rs.next()) {
                long itemId = rs.getLong("item_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                item.setName(name);
                item.setPrice(price);
                item.setItemId(itemId);
            }
            return Optional.of(item);
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get item By Id : " + e);
        }
    }

    @Override
    public Item update(Item item) throws DataProcessingException {
        String query = "update internet_shop.items set name= ? ,price= ? where item_id= ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, item.getName());
            preparedStatement.setDouble(2, item.getPrice());
            preparedStatement.setLong(3, item.getItemId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to update item : " + e);
        }
        return item;
    }

    @Override
    public boolean deleteById(Long id) throws DataProcessingException {
        String query = "delete from internet_shop.items where item_id=?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete item By Id : " + e);
        }
    }

    @Override
    public boolean delete(Item item) throws DataProcessingException {
        String query = "delete from internet_shop.items where item_id=?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, item.getItemId());
            preparedStatement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete item: " + e);
        }
    }

    @Override
    public List<Item> getAll() throws DataProcessingException {
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
            throw new DataProcessingException("Failed to get all item: " + e);
        }
    }
}
