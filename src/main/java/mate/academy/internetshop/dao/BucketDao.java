package mate.academy.internetshop.dao;

import mate.academy.internetshop.model.Bucket;

public interface BucketDao {
    Bucket create(Bucket bucket);

    Bucket get(Long basketId);

    Bucket update(Bucket busket);

    void delete(Bucket bucket);

    void deleteById(Long bucketId);
}
