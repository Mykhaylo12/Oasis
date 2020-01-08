package mate.academy.internetshop.dao.impl;

import java.util.NoSuchElementException;

import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.IdGenerator;
import mate.academy.internetshop.model.User;

@Dao
public class UserDaoImpl implements UserDao {
    @Override
    public User create(User user) {
        User temp = user;
        temp.setUserId(IdGenerator.userIdGenerator());
        Storage.users.add(temp);
        return null;
    }

    @Override
    public User get(Long userId) {
        return Storage.users.stream()
                .filter(x -> x.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("This user doesn't exist"));
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
    public void deleteById(Long userId) {
        User temp = Storage.users.stream()
                .filter(x -> x.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("This user doesn't exist"));
        Storage.users.remove(temp);
    }

    @Override
    public void delete(User user) {
        Storage.users.remove(user);
    }
}
