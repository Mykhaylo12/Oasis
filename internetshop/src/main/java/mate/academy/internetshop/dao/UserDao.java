package mate.academy.internetshop.dao;

import mate.academy.internetshop.model.User;

public interface UserDao {
    User create(User user);

    User get(Long userId);

    User update(User user);

    void deleteById(Long userId);

    void delete(User user);
}
