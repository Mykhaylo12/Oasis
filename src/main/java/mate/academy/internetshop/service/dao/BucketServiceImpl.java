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

@Service
public class BucketServiceImpl implements BucketService {
    @Inject
    private static BucketDao bucketDao;
    @Inject
    private static ItemDao itemDao;

    @Override
    public Bucket create(Bucket bucket) {
        return bucketDao.create(bucket);
    }

    @Override
    public Bucket get(Long idBucket) {
        Optional<Bucket> bucket = bucketDao.get(idBucket);
        if (bucket.isPresent()) {
            return bucket.get();
        } else {
            throw new NoSuchElementException();
        }
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
        Bucket temp;
        if (bucketDao.get(bucket.getBucketId()).isPresent()) {
            temp = bucketDao.get(bucket.getBucketId()).get();
        } else {
            throw new NoSuchElementException();
        }
        temp.getItems().add(item);
        bucketDao.update(temp);
    }

    @Override
    public void deleteItem(Bucket bucket, Item item) {
        Bucket temp;
        if (bucketDao.get(bucket.getBucketId()).isPresent()) {
            temp = bucketDao.get(bucket.getBucketId()).get();
        } else {
            throw new NoSuchElementException();
        }
        List<Item> itemOfBucket = temp.getItems();
        itemOfBucket.remove(item);
        bucketDao.update(temp);
    }

    @Override
    public void clear(Bucket bucket) {
        Bucket temp;
        if (bucketDao.get(bucket.getBucketId()).isPresent()) {
            temp = bucketDao.get(bucket.getBucketId()).get();
        } else {
            throw new NoSuchElementException();
        }
        temp.getItems().clear();
        bucketDao.update(temp);
    }

    @Override
    public List<Item> getAllItems(Bucket bucket) {
        if (bucketDao.get(bucket.getBucketId()).isPresent()) {
            return bucketDao.get(bucket.getBucketId()).get().getItems();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public List<Bucket> getAll() {
        return bucketDao.getAll();
    }

    @Override
    public Bucket getByUser(User user) {
        return Storage.buckets
                .stream()
                .filter(b -> b.getUserId().equals(user.getUserId()))
                .findFirst()
                .orElse(bucketDao.create(new Bucket(user)));
    }
}
