package mate.academy.internetshop.service;

import mate.academy.internetshop.exeption.AuthenticationException;
import mate.academy.internetshop.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User create(User user);

    User get(Long idUser);

    User update(User user);

    void deleteById(Long userId);

    void delete(User user);

    List<User> getAll();

    User login(String login, String password) throws AuthenticationException;

    Optional<User> getByToken(String token);
}
