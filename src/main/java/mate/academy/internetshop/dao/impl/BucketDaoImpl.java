package mate.academy.internetshop.dao.impl;

import java.util.NoSuchElementException;
import java.util.Optional;

import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.IdGenerator;

@Dao
public class BucketDaoImpl implements BucketDao {
    @Override
    public Bucket create(Bucket bucket) {
        bucket.setBucketId(IdGenerator.bucketIdGenerator());
        Storage.buckets.add(bucket);
        return bucket;
    }

    @Override
    public Optional<Bucket> get(Long bucketId) {
        return Optional.ofNullable(Storage.buckets.stream()
                .filter(x -> x.getBucketId().equals(bucketId))
                .findFirst()
                .orElseThrow(() ->
                        new NoSuchElementException("Can not find bucket with id " + bucketId)));
    }

    @Override
    public Bucket update(Bucket busket) {
        Bucket temp = Storage.buckets.stream()
                .filter(x -> x.getBucketId().equals(busket.getBucketId()))
                .findFirst().orElseThrow(() -> new NoSuchElementException("Can not update bucket."
                        + " Bucket doesn't exist"));
        Storage.buckets.set(Storage.buckets.indexOf(temp), busket);
        return busket;
    }

    @Override
    public void delete(Bucket bucket) {
        Storage.buckets.remove(bucket);
    }

    @Override
    public boolean deleteById(Long bucketId) {
        Bucket temp = Storage.buckets.stream()
                .filter(x -> x.getBucketId().equals(bucketId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Bucket with id "
                        + bucketId + " doesn't exist"));
        return Storage.buckets.remove(temp);
    }
}