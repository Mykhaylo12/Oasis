package mate.academy.internetshop.service.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.exeption.DataProcessingException;
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
    public Bucket create(Bucket bucket) throws DataProcessingException {
        return bucketDao.create(bucket);
    }

    @Override
    public Bucket get(Long idBucket) throws DataProcessingException {
        Optional<Bucket> bucket = bucketDao.get(idBucket);
        return bucket.orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Bucket update(Bucket bucket) throws DataProcessingException {
        return bucketDao.update(bucket);
    }

    @Override
    public void delete(Bucket bucket) throws DataProcessingException {
        bucketDao.delete(bucket);
    }

    @Override
    public void addItem(Bucket bucket, Item item) throws DataProcessingException {
        List<Item> items = new ArrayList<>();
        Optional<Bucket> temp = getAll()
                .stream()
                .filter(b -> b.getUserId().equals(bucket.getUserId()))
                .findFirst();
        Bucket bucketForUpdate = temp.orElse(create(new Bucket(bucket.getUserId(),
                bucket.getUserId(), items)));
        bucketForUpdate.getItems().add(item);
        bucketDao.update(bucketForUpdate);
    }

    @Override
    public void deleteItem(Bucket bucket, Item item) throws DataProcessingException {
        Bucket temp = bucketDao.get(bucket.getBucketId()).orElseThrow(NoSuchElementException::new);
        List<Item> itemOfBucket = temp.getItems();
        itemOfBucket.remove(item);
        bucketDao.update(temp);
    }

    @Override
    public void clear(Bucket bucket) throws DataProcessingException {
        Bucket temp = bucketDao.get(bucket.getBucketId()).orElseThrow(NoSuchElementException::new);
        bucket.getItems().clear();
        bucketDao.update(temp);
    }

    @Override
    public List<Item> getAllItems(Bucket bucket) throws DataProcessingException {
        return get(bucket.getBucketId()).getItems();
    }

    @Override
    public List<Bucket> getAll() throws DataProcessingException {
        return bucketDao.getAll();
    }

    @Override
    public Bucket getByUser(User user) throws DataProcessingException {
        return bucketDao.getByUser(user).orElse(bucketDao.create(new Bucket(user)));
    }

    @Override
    public Bucket getByUserId(Long userId) throws DataProcessingException {
        List<Item> items = new ArrayList<>();
        Optional<Bucket> bucket = getAll()
                .stream()
                .filter(b -> b.getUserId().equals(userId))
                .findFirst();
        return bucket.orElse(create(new Bucket(userId, userId, items)));
    }
}
