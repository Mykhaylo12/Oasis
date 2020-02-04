package mate.academy.internetshop.dao;

import java.util.Optional;

import mate.academy.internetshop.exeption.DataProcessingException;
import mate.academy.internetshop.exeption.LoginExistException;
import mate.academy.internetshop.model.User;

public interface UserDao extends GenericDao<User, Long> {

    Optional<User> findByLogin(String login) throws DataProcessingException;

    Optional<User> getByToken(String token) throws DataProcessingException;

    void checkUserLoginForRegistration(String login) throws LoginExistException;

    void checkUserLoginForLogin(String login) throws DataProcessingException;
}
