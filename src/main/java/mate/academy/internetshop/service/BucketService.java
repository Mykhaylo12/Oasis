package mate.academy.internetshop.service;

import java.util.List;

import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.User;

public interface BucketService {
    Bucket create(Bucket bucket);

    Bucket get(Long idBucket);

    Bucket update(Bucket bucket);

    void delete(Bucket bucket);

    void addItem(Bucket bucket, Item item);

    void deleteItem(Bucket bucket, Item item);

    void clear(Bucket bucket);

    List<Item> getAllItems(Bucket bucket);

    List<Bucket> getAll();

    Bucket getByUser(User user);
}
