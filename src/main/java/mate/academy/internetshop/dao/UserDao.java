package mate.academy.internetshop.dao;

import java.util.List;
import java.util.Optional;

import mate.academy.internetshop.exeption.DataProcessingException;
import mate.academy.internetshop.exeption.LoginExistExeption;
import mate.academy.internetshop.model.User;

public interface UserDao {
    User create(User user) throws DataProcessingException;

    Optional<User> get(Long userId) throws DataProcessingException;

    User update(User user) throws DataProcessingException;

    boolean deleteById(Long userId) throws DataProcessingException;

    boolean delete(User user) throws DataProcessingException;

    Optional<User> findByLogin(String login) throws DataProcessingException;

    Optional<User> getByToken(String token) throws DataProcessingException;

    List<User> getAll() throws DataProcessingException;

    void checkUserLoginForRegistration(String login) throws LoginExistExeption;

    void checkUserLoginForLogin(String login) throws LoginExistExeption, DataProcessingException;
}
