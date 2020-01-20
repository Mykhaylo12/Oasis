package mate.academy.internetshop.service.dao;

import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exeption.AuthenticationException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private static UserDao userDao;

    @Override
    public User create(User user) {
        user.setToken(getToken());
        return userDao.create(user);
    }

    private String getToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public User get(Long idUser) {
        return userDao.get(idUser).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public void deleteById(Long userId) {
        userDao.deleteById(userId);
    }

    @Override
    public void delete(User user) {
        userDao.delete(user);
    }

    @Override
    public List<User> getAll() {
        return Storage.users;
    }

    @Override
    public User login(String login, String password) throws AuthenticationException {
       User user = userDao.findByLogin(login).orElseThrow(NoSuchElementException::new);
        if (!user.getPassword().equals(password)) {
            throw new AuthenticationException("Wrong password or login");
        }
        return user;
    }

    @Override
    public Optional<User> getByToken(String token) {
        return userDao.getByToken(token);
    }
}
