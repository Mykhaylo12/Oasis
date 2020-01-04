package mate.academy.internetshop.service.dao;

import java.util.List;

import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
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
        return bucketDao.get(idBucket);
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
        Bucket temp = bucketDao.get(bucket.getBucketId());
        temp.getItems().add(item);
        bucketDao.update(temp);
    }

    @Override
    public void deleteItem(Bucket bucket, Item item) {
        Bucket temp = bucketDao.get(bucket.getBucketId());
        List<Item> itemOfBucket = temp.getItems();
        itemOfBucket.remove(item);
        bucketDao.update(temp);
    }

    @Override
    public void clear(Bucket bucket) {
        Bucket temp = bucketDao.get(bucket.getBucketId());
        temp.getItems().clear();
        bucketDao.update(temp);
    }

    @Override
    public List<Item> getAllItems(Bucket bucket) {
        return bucketDao.get(bucket.getBucketId()).getItems();
    }
}
