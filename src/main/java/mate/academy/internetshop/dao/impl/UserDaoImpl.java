package mate.academy.internetshop.dao.impl;

import java.util.NoSuchElementException;
import java.util.Optional;

import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.IdGenerator;
import mate.academy.internetshop.model.User;

@Dao
public class UserDaoImpl implements UserDao {
    @Override
    public User create(User user) {
        user.setUserId(IdGenerator.userIdGenerator());
        Storage.users.add(user);
        return user;
    }

    @Override
    public Optional<User> get(Long userId) {
        return Optional.ofNullable(Storage.users.stream()
                .filter(x -> x.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("This user doesn't exist")));
    }

    @Override
    public User update(User user) {
        User temp = Storage.users.stream()
                .filter(x -> x.equals(user))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("This user doesn't exist"));
        Storage.users.set(Storage.users.indexOf(temp), user);
        return user;
    }

    @Override
    public boolean deleteById(Long userId) {
        User temp = Storage.users.stream()
                .filter(x -> x.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("This user doesn't exist"));
        return Storage.users.remove(temp);
    }

    @Override
    public void delete(User user) {
        Storage.users.remove(user);
    }
}
