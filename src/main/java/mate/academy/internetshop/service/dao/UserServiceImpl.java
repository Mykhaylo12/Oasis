package mate.academy.internetshop.service.dao;

import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exeption.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;
import mate.academy.internetshop.util.HashUtil;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private static UserDao userDao;

    @Override
    public User create(User user) throws DataProcessingException {
        byte[] salt = HashUtil.getSalt();
        String hashPassword = HashUtil.hashPassword(user.getPassword(), salt);
        user.setPassword(hashPassword);
        user.setSalt(salt);
        userDao.checkUserLoginForRegistration(user.getLogin());
        user.setToken(getToken());
        return userDao.create(user);
    }

    private String getToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public User get(Long idUser) throws DataProcessingException {
        return userDao.get(idUser).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public User update(User user) throws DataProcessingException {
        return userDao.update(user);
    }

    @Override
    public void deleteById(Long userId) throws DataProcessingException {
        userDao.deleteById(userId);
    }

    @Override
    public void delete(User user) throws DataProcessingException {
        userDao.delete(user);
    }

    @Override
    public List<User> getAll() throws DataProcessingException {
        return userDao.getAll();
    }

    @Override
    public User login(String login, String password) throws DataProcessingException {
        userDao.checkUserLoginForLogin(login);
        User user = userDao.findByLogin(login).orElseThrow(NoSuchElementException::new);
        String hashPasswordFromLoginController = HashUtil.hashPassword(password, user.getSalt());
        if (!user.getPassword().equals(hashPasswordFromLoginController)) {
            throw new DataProcessingException("Wrong password or login");
        }
        return user;
    }

    @Override
    public Optional<User> getByToken(String token) throws DataProcessingException {
        return userDao.getByToken(token);
    }
}
