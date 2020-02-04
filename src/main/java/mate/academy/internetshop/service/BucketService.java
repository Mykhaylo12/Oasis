package mate.academy.internetshop.service;

import mate.academy.internetshop.exeption.DataProcessingException;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.User;

public interface BucketService extends GenericService<Bucket, Long> {
    void addItem(Bucket bucket, Item item) throws DataProcessingException;

    void deleteItem(Bucket bucket, Item item) throws DataProcessingException;

    Bucket getByUser(User user) throws DataProcessingException;

    Bucket getByUserId(Long userId) throws DataProcessingException;
}
