package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.User;
import org.apache.log4j.Logger;

@Dao
public class BucketDaoJdbcImpl extends AbstractDao<Bucket> implements BucketDao {
    private static Logger LOGGER = Logger.getLogger(ItemDaoJdbcImpl.class);

    public BucketDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Bucket create(Bucket bucket) {
        bucket.setBucketId(bucket.getUserId());
        String query = "INSERT INTO internet_shop.buckets (user_id, item_id) VALUES (?, ?);";
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            for (Item item : bucket.getItems()) {
                preparedStatement.setLong(1, bucket.getUserId());
                preparedStatement.setLong(2, item.getItemId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.error("Can`t create bucket", e);
            return bucket;
        }
        return bucket;
    }

    @Override
    public Optional<Bucket> get(Long basketId) {
        long userId = basketId;
        String query = "SELECT * FROM internet_shop.buckets INNER JOIN internet_shop.items ON"
                + " internet_shop.buckets.item_id=internet_shop.items.item_id WHERE user_id=?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.executeQuery();
            ResultSet rs = preparedStatement.executeQuery();
            List<Item> items = new ArrayList<>();
            Bucket bucket = new Bucket();
            bucket.setBucketId(basketId);
            bucket.setUserId(userId);
            while (rs.next()) {
                bucket.setUserId(rs.getLong("user_id"));
                bucket.setBucketId(rs.getLong("user_id"));
                Item item = new Item();
                item.setName(rs.getString("name"));
                item.setPrice(rs.getDouble("price"));
                item.setItemId(rs.getLong("item_id"));
                items.add(item);
            }
            bucket.setItems(items);
            return Optional.of(bucket);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Optional<Bucket> getByUser(User user) {
        long userId = user.getUserId();
        String query = "SELECT user_id, items.item_id, name, price FROM internet_shop.buckets"
                + " INNER JOIN internet_shop.items ON"
                + " internet_shop.buckets.item_id=internet_shop.items.item_id WHERE user_id=?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.executeQuery();
            ResultSet rs = preparedStatement.executeQuery();
            List<Item> items = new ArrayList<>();
            Bucket bucket = new Bucket();
            while (rs.next()) {
                Item item = new Item();
                bucket.setUserId(rs.getLong("user_id"));
                bucket.setBucketId(rs.getLong("user_id"));

                item.setName(rs.getString("name"));
                item.setPrice(rs.getDouble("price"));
                item.setItemId(rs.getLong("item_id"));
                items.add(item);
            }
            bucket.setItems(items);
            return Optional.of(bucket);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Bucket update(Bucket bucket) {
        List<Item> items = bucket.getItems();
        deleteOldBucket(bucket.getBucketId());
        String query = "INSERT INTO internet_shop.buckets (user_id, item_id) VALUES (?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (Item item : items) {
                statement.setLong(1, bucket.getUserId());
                statement.setLong(2, item.getItemId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bucket;
    }

    private void deleteOldBucket(Long bucketId) {
        long userId = bucketId;
        String query = "DELETE FROM internet_shop.buckets WHERE user_id=?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public boolean delete(Bucket bucket) {
        long userId = bucket.getUserId();
        String query = "DELETE FROM internet_shop.buckets WHERE user_id=?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, userId);
            int rs = preparedStatement.executeUpdate();
            if (rs > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public boolean deleteById(Long bucketId) {
        long userId = bucketId;
        String query = "DELETE FROM internet_shop.buckets WHERE user_id=?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, userId);
            int rs = preparedStatement.executeUpdate();
            if (rs > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public boolean deleteBucketByUser(User user) {
        long userId = user.getUserId();
        String query = "DELETE FROM internet_shop.buckets WHERE user_id=?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, userId);
            int rs = preparedStatement.executeUpdate();
            if (rs > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Bucket> getAll() {
        List<Bucket> buckets = new ArrayList<>();
        String query = "SELECT DISTINCT user_id FROM internet_shop.buckets;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Bucket bucket = new Bucket();
                List<Item> items = new ArrayList<>();
                bucket.setUserId(rs.getLong("user_id"));
                bucket.setBucketId(rs.getLong("user_id"));
                items = getAllItemsByUserId(rs.getLong("user_id"));
                bucket.setItems(items);
                buckets.add(bucket);
            }
            return buckets;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    private List<Item> getAllItemsByUserId(Long userId) {
        List<Item> items = new ArrayList<>();
        String query = "SELECT internet_shop.items.item_id, name ,price FROM"
                + " internet_shop.items INNER JOIN internet_shop.buckets ON"
                + "  internet_shop.items.item_id=internet_shop.buckets.item_id WHERE user_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Long itemId = rs.getLong("item_id");
                String itemName = rs.getString("name");
                Double itemPrice = rs.getDouble("price");
                Item item = new Item();
                item.setName(itemName);
                item.setPrice(itemPrice);
                item.setItemId(itemId);
                items.add(item);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return items;
    }
}
