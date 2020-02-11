package mate.academy.internetshop.dao;

import java.util.Optional;

import mate.academy.internetshop.exeption.DataProcessingException;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.User;

public interface BucketDao extends GenericDao<Bucket, Long> {
    Optional<Bucket> getByUser(User user) throws DataProcessingException;

    Optional<Bucket> getBucketByUserId(Long userId) throws DataProcessingException;
}
