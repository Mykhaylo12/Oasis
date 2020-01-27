package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exeption.DataProcessingException;
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
    public boolean delete(User user) {
        return Storage.users.remove(user);
    }

    @Override
    public Optional<User> findByLogin(String login) throws DataProcessingException {
        return Storage.users.stream()
                .filter(u -> u.getLogin().equals(login))
                .findFirst();
    }

    @Override
    public Optional<User> getByToken(String token) {
        return Storage.users.stream()
                .filter(u -> u.getToken().equals(token))
                .findFirst();
    }

    @Override
    public List<User> getAll() {
        return Storage.users;
    }
}
