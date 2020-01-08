package mate.academy.internetshop.dao.impl;

import java.util.NoSuchElementException;

import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.IdGenerator;
import mate.academy.internetshop.model.Item;

@Dao
public class ItemDaoImpl implements ItemDao {
    @Override
    public Item create(Item item) {
        Item temp = item;
        temp.setItemId(IdGenerator.itemIdGenerator());
        Storage.items.add(temp);
        return temp;
    }

    @Override
    public Item get(Long id) {
        return Storage.items.stream()
                .filter(x -> x.getItemId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Can't find item with id " + id));
    }

    @Override
    public Item update(Item item) {
        Item temp = Storage.items.stream()
                .filter(x -> x.getItemId().equals(item.getItemId()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Item with id: "
                        + item.getItemId() + " doesn't exist"));
        Storage.items.set(Storage.items.indexOf(temp), item);
        return item;
    }

    @Override
    public void deleteById(Long id) {
        Item item = Storage.items.stream()
                .filter(x -> x.getItemId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Item with id: "
                        + id + " doesn't exist"));
        Storage.items.remove(item);
    }

    @Override
    public void delete(Item item) {
        Item temp = Storage.items.stream()
                .filter(x -> x.equals(item))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("This item doesn't exist"));
        Storage.items.remove(temp);
    }
}
