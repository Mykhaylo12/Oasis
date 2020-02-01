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
import mate.academy.internetshop.exeption.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.User;

@Dao
public class BucketDaoJdbcImpl extends AbstractDao<Bucket> implements BucketDao {
    public BucketDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Bucket create(Bucket bucket) throws DataProcessingException {
        String query = "INSERT INTO internet_shop.buckets (user_id) VALUES (?);";
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, bucket.getUserId());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            while (rs.next()) {
                bucket.setBucketId(rs.getLong(1));
            }
            addItemsToBucketInDB(bucket);
            return bucket;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to  create bucket: " + e);
        }
    }

    private Optional<Bucket> checkIfExistBucket(Bucket bucket) throws DataProcessingException {
        String query = "SELECT * FROM internet_shop.buckets WHERE user_id=?;";
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query)) {
            if (preparedStatement.execute()) {
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    bucket.setBucketId(rs.getLong("bucket_id"));
                    bucket.setItems(getAllItemsByUserId(bucket.getUserId()));
                }
                return Optional.of(bucket);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to  create bucket: " + e);
        }
    }

    private void addItemsToBucketInDB(Bucket bucket) throws DataProcessingException {
        String query = "INSERT INTO internet_shop.buckets_items (bucket_id, item_id)"
                + " VALUES (?, ?);";
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, bucket.getBucketId());
            for (Item item : bucket.getItems()) {
                preparedStatement.setLong(2, item.getItemId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to  create bucket: " + e);
        }
    }

    @Override
    public Optional<Bucket> get(Long backetId) throws DataProcessingException {
        String query = "SELECT buckets.bucket_id, buckets.user_id, buckets_items.item_id,"
                + " items.name, items.price FROM internet_shop.buckets_items INNER JOIN"
                + " internet_shop.buckets ON buckets_items.bucket_id=buckets.bucket_id INNER JOIN"
                + " internet_shop.items ON items.item_id=buckets_items.item_id"
                + " WHERE buckets.bucket_id=?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, backetId);
            preparedStatement.executeQuery();
            ResultSet rs = preparedStatement.executeQuery();
            List<Item> items = new ArrayList<>();
            Bucket bucket = new Bucket();
            while (rs.next()) {
                bucket.setUserId(rs.getLong("user_id"));
                bucket.setBucketId(rs.getLong("bucket_id"));
                Item item = new Item();
                item.setName(rs.getString("name"));
                item.setPrice(rs.getDouble("price"));
                item.setItemId(rs.getLong("item_id"));
                items.add(item);
            }
            bucket.setItems(items);
            return Optional.of(bucket);
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get bucket: " + e);
        }
    }

    @Override
    public Optional<Bucket> getByUser(User user) throws DataProcessingException {
        String query = "SELECT buckets.bucket_id, buckets.user_id, buckets_items.item_id,"
                + " items.name, items.price FROM internet_shop.buckets_items INNER JOIN"
                + " internet_shop.buckets ON buckets_items.bucket_id=buckets.bucket_id INNER JOIN"
                + " internet_shop.items ON items.item_id=buckets_items.item_id"
                + " WHERE buckets.user_id=?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, user.getUserId());
            preparedStatement.executeQuery();
            ResultSet rs = preparedStatement.executeQuery();
            List<Item> items = new ArrayList<>();
            Bucket bucket = new Bucket();
            while (rs.next()) {
                bucket.setUserId(rs.getLong("user_id"));
                bucket.setBucketId(rs.getLong("bucket_id"));
                Item item = new Item();
                item.setName(rs.getString("name"));
                item.setPrice(rs.getDouble("price"));
                item.setItemId(rs.getLong("item_id"));
                items.add(item);
            }
            bucket.setItems(items);
            return Optional.of(bucket);
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get bucket: " + e);
        }
    }

    @Override
    public Bucket update(Bucket bucket) throws DataProcessingException {
        deleteItemsFromBucket(bucket);
        addItemsToBucketInDB(bucket);
        return bucket;
    }

    private void deleteItemsFromBucket(Bucket bucket) throws DataProcessingException {
        String query = "DELETE FROM internet_shop.buckets_items WHERE bucket_id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, bucket.getBucketId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete bucket: " + e);
        }
    }

    @Override
    public boolean delete(Bucket bucket) throws DataProcessingException {
        String query = "DELETE FROM internet_shop.buckets WHERE bucket_id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, bucket.getBucketId());
            int rs = preparedStatement.executeUpdate();
            return rs > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete bucket: " + e);
        }
    }

    @Override
    public boolean deleteById(Long bucketId) throws DataProcessingException {
        String query = "DELETE FROM internet_shop.buckets WHERE bucket_id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, bucketId);
            int rs = preparedStatement.executeUpdate();
            return rs > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete bucket: " + e);
        }
    }

    @Override
    public Optional<Bucket> getBucketByUserId(Long userId) throws DataProcessingException {
        String query = "SELECT bucket_id FROM internet_shop.buckets WHERE  user_id=?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, userId);
            ResultSet rs = preparedStatement.executeQuery();
            Bucket bucket = new Bucket();
            while (rs.next()) {
                bucket.setUserId(userId);
                bucket.setBucketId(rs.getLong("bucket_id"));
                List<Item> items = getAllItemsByUserId(userId);
                bucket.setItems(items);
            }
            return Optional.of(bucket);
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get All buckets: " + e);
        }
    }

    private List<Item> getAllItemsByUserId(Long userId) throws DataProcessingException {
        List<Item> items = new ArrayList<>();
        String query = "SELECT buckets.bucket_id, buckets.user_id, buckets_items.item_id,"
                + " items.name, items.price FROM internet_shop.buckets_items INNER JOIN"
                + " internet_shop.buckets ON buckets_items.bucket_id=buckets.bucket_id"
                + " INNER JOIN internet_shop.items ON items.item_id=buckets_items.item_id"
                + " WHERE buckets.user_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                item.setName(rs.getString("name"));
                item.setPrice(rs.getDouble("price"));
                item.setItemId(rs.getLong("item_id"));
                items.add(item);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get All Items By UserId: " + e);
        }
        return items;
    }
}
