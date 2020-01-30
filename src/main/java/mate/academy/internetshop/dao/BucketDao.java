package mate.academy.internetshop.dao;

import java.util.Optional;

import mate.academy.internetshop.exeption.DataProcessingException;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.User;

public interface BucketDao {
    Bucket create(Bucket bucket) throws DataProcessingException;

    Optional<Bucket> get(Long basketId) throws DataProcessingException;

    Optional<Bucket> getByUser(User user) throws DataProcessingException;

    Bucket update(Bucket bucket) throws DataProcessingException;

    boolean delete(Bucket bucket) throws DataProcessingException;

    boolean deleteById(Long bucketId) throws DataProcessingException;

    boolean deleteBucketByUser(User user) throws DataProcessingException;

    Optional<Bucket> getBucketByUserId(Long userId) throws DataProcessingException;
}
