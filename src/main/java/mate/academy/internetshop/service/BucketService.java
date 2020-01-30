package mate.academy.internetshop.service;

import java.util.List;

import mate.academy.internetshop.exeption.DataProcessingException;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.User;

public interface BucketService {
    Bucket create(Bucket bucket) throws DataProcessingException;

    Bucket get(Long idBucket) throws DataProcessingException;

    Bucket update(Bucket bucket) throws DataProcessingException;

    void delete(Bucket bucket) throws DataProcessingException;

    void addItem(Bucket bucket, Item item) throws DataProcessingException;

    void deleteItem(Bucket bucket, Item item) throws DataProcessingException;

    void clear(Bucket bucket) throws DataProcessingException;

    List<Item> getAllItems(Bucket bucket) throws DataProcessingException;

    Bucket getByUser(User user) throws DataProcessingException;

    Bucket getByUserId(Long userId) throws DataProcessingException;
}
