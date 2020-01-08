package mate.academy.internetshop.dao;

import java.util.Optional;

import mate.academy.internetshop.model.Item;

public interface ItemDao {
    Item create(Item item);

    Optional<Item> get(Long id);

    Item update(Item item);

    boolean deleteById(Long id);

    void delete(Item item);
}
