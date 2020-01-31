package mate.academy.internetshop.service.dao;

import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.exeption.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.service.ItemService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ItemServiceImpl implements ItemService {
    @Inject
    private static ItemDao itemDao;

    @Override
    public Item create(Item item) throws DataProcessingException {
        return itemDao.create(item);
    }

    @Override
    public Item get(Long id) throws DataProcessingException {
        return itemDao.get(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Item update(Item item) throws DataProcessingException {
        return itemDao.update(item);
    }

    @Override
    public void deleteById(Long id) throws DataProcessingException {
        itemDao.deleteById(id);
    }

    @Override
    public void delete(Item item) throws DataProcessingException {
        itemDao.delete(item);
    }

    @Override
    public List<Item> getAll() throws DataProcessingException {
        return itemDao.getAll();
    }
}
