package mate.academy.internetshop.service;

import mate.academy.internetshop.exeption.DataProcessingException;
import mate.academy.internetshop.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User create(User user) throws DataProcessingException;

    User get(Long idUser) throws DataProcessingException;

    User update(User user) throws DataProcessingException;

    void deleteById(Long userId) throws DataProcessingException;

    void delete(User user) throws DataProcessingException;

    List<User> getAll() throws DataProcessingException;

    User login(String login, String password) throws DataProcessingException;

    Optional<User> getByToken(String token) throws DataProcessingException;
}
