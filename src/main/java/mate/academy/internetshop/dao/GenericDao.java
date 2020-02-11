package mate.academy.internetshop.dao;

import java.util.List;
import java.util.Optional;

import mate.academy.internetshop.exeption.DataProcessingException;

public interface GenericDao<T, I> {
    T create(T entity) throws DataProcessingException;

    Optional<T> get(I entityId) throws DataProcessingException;

    T update(T entity) throws DataProcessingException;

    boolean deleteById(I entityId) throws DataProcessingException;

    boolean delete(T entity) throws DataProcessingException;

    List<T> getAll() throws DataProcessingException;
}
