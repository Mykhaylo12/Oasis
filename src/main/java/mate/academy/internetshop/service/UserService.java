package mate.academy.internetshop.service;

import mate.academy.internetshop.model.User;

import java.util.List;

public interface UserService {
    User create(User user);

    User get(Long idUser);

    User update(User user);

    void deleteById(Long userId);

    void delete(User user);

    List<User> getAll();
}
