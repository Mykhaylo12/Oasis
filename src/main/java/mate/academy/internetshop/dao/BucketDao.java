package mate.academy.internetshop.dao;

import java.util.List;
import java.util.Optional;

import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.User;

public interface BucketDao {
    Bucket create(Bucket bucket);

    Optional<Bucket> get(Long basketId);

    Optional<Bucket> getByUser(User user);

    Bucket update(Bucket bucket);

    boolean delete(Bucket bucket);

    boolean deleteById(Long bucketId);

    boolean deleteBucketByUser(User user);

    List<Bucket> getAll();
}
