package mate.academy.internetshop.service;

import mate.academy.internetshop.exeption.DataProcessingException;
import mate.academy.internetshop.model.User;

import java.util.Optional;

public interface UserService extends GenericService<User, Long> {
    User login(String login, String password) throws DataProcessingException;

    Optional<User> getByToken(String token) throws DataProcessingException;
}
