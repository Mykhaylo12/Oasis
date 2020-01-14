package mate.academy.internetshop.service.dao;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.UserService;

@Service
public class BucketServiceImpl implements BucketService {
    @Inject
    private static BucketDao bucketDao;
    @Inject
    private static ItemDao itemDao;
    @Inject
    private static UserService userService;

    @Override
    public Bucket create(Bucket bucket) {
        return bucketDao.create(bucket);
    }

    @Override
    public Bucket get(Long idBucket) {
        Optional<Bucket> bucket = bucketDao.get(idBucket);
        return bucket.orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Bucket update(Bucket bucket) {
        return bucketDao.update(bucket);
    }

    @Override
    public void delete(Bucket bucket) {
        bucketDao.delete(bucket);
    }

    @Override
    public void addItem(Bucket bucket, Item item) {
        Bucket temp = bucketDao.get(bucket.getBucketId()).orElseThrow(NoSuchElementException::new);
        temp.getItems().add(item);
        bucketDao.update(temp);
    }

    @Override
    public void deleteItem(Bucket bucket, Item item) {
        Bucket temp = bucketDao.get(bucket.getBucketId()).orElseThrow(NoSuchElementException::new);
        List<Item> itemOfBucket = temp.getItems();
        itemOfBucket.remove(item);
        bucketDao.update(temp);
    }

    @Override
    public void clear(Bucket bucket) {
        Bucket temp = bucketDao.get(bucket.getBucketId()).orElseThrow(NoSuchElementException::new);
        bucket.getItems().clear();
        bucketDao.update(temp);
    }

    @Override
    public List<Item> getAllItems(Bucket bucket) {
        return bucketDao.get(bucket.getBucketId()).orElseThrow(NoSuchElementException::new).getItems();
    }

    @Override
    public List<Bucket> getAll() {
        return bucketDao.getAll();
    }

    @Override
    public Bucket getByUser(User user) {
        return bucketDao.getByUser(user).orElse(bucketDao.create(new Bucket(user)));
    }

    @Override
    public Bucket getByUserId(Long userId) {
        User user = userService.get(userId);
        return bucketDao.getByUser(user).orElse(bucketDao.create(new Bucket(user)));
    }
}
