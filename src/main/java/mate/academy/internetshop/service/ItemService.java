package mate.academy.internetshop.service;

import mate.academy.internetshop.exeption.DataProcessingException;
import mate.academy.internetshop.model.Item;

import java.util.List;

public interface ItemService {
    Item create(Item item) throws DataProcessingException;

    Item get(Long id) throws DataProcessingException;

    Item update(Item item) throws DataProcessingException;

    void deleteById(Long id) throws DataProcessingException;

    void delete(Item item) throws DataProcessingException;

    List<Item> getAll() throws DataProcessingException;
}
