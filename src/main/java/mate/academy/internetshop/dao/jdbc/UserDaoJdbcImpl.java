package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import org.apache.log4j.Logger;

@Dao
public class UserDaoJdbcImpl extends AbstractDao<User> implements UserDao {
    private static Logger LOGGER = Logger.getLogger(UserDaoJdbcImpl.class);

    public UserDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public User create(User user) {
        String query = "INSERT INTO internet_shop.users(login, password, email, name,token)"
                + " VALUES(?, ?, ?, ?, ?);";
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getName());
            preparedStatement.setString(5, user.getToken());
            int rows = preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            while (rs.next()) {
                user.setUserId(rs.getLong(1));
            }
            updateUserRoles(user);
            LOGGER.info(rows + " rows were affected");
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return user;
    }

    private void updateUserRoles(User user) {
        deleteAllUserRoles(user);
        String query = "INSERT INTO internet_shop.roles (role_id, role_name, user_id)"
                + " VALUES (?, ?, ?);";
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            for (Role role : user.getRoles()) {
                if (role.equals(Role.of("ADMIN"))) {
                    preparedStatement.setLong(1, 1L);
                    preparedStatement.setString(2, "ADMIN");
                } else {
                    preparedStatement.setLong(1, 2L);
                    preparedStatement.setString(2, "USER");
                }
                preparedStatement.setLong(3, user.getUserId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    private void deleteAllUserRoles(User user) {
        String query = "DELETE FROM internet_shop.roles WHERE user_id=?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, user.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Optional<User> get(Long userId) {
        User user = new User();
        String query = "SELECT * FROM internet_shop.users WHERE user_id=?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, userId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                user.setUserId(rs.getLong("user_id"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setName(rs.getString("name"));
                user.setToken(rs.getString("token"));
            }
            user.setRoles(getAllUserRoles(user.getUserId()));
            return Optional.of(user);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    private Set<Role> getAllUserRoles(long userId) {
        Set<Role> roles = new HashSet<>();
        String query = "SELECT * FROM internet_shop.roles WHERE user_id=?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, userId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Role role = new Role();
                role.setRoleName(Role.RoleName.valueOf(rs.getString("role_name")));
                role.setId(rs.getLong("role_id"));
                roles.add(role);
            }
            return roles;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public User update(User user) {
        String query = "UPDATE internet_shop.users SET login = ?, password = ?, email= ?,"
                + " name = ?, token = ? WHERE user_id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getName());
            preparedStatement.setString(5, user.getToken());
            preparedStatement.setLong(6, user.getUserId());
            preparedStatement.executeUpdate();
            return get(user.getUserId()).orElseThrow(NoSuchElementException::new);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public boolean deleteById(Long userId) {
        String query = "DELETE FROM internet_shop.users WHERE user_id=?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, userId);
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public boolean delete(User user) {
        String query = "DELETE FROM internet_shop.users WHERE user_id=?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, user.getUserId());
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Optional<User> findByLogin(String login) {
        User user = new User();
        String query = "SELECT * FROM internet_shop.users WHERE login=?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, login);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                user.setUserId(rs.getLong("user_id"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setName(rs.getString("name"));
                user.setToken(rs.getString("token"));
            }
            user.setRoles(getAllUserRoles(user.getUserId()));
            return Optional.of(user);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Optional<User> getByToken(String token) {
        User user = new User();
        String query = "SELECT * FROM internet_shop.users WHERE token=?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, token);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                user.setUserId(rs.getLong("user_id"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setName(rs.getString("name"));
            }
            user.setRoles(getAllUserRoles(user.getUserId()));
            return Optional.of(user);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM internet_shop.users;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getLong("user_id"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setName(rs.getString("name"));
                user.setToken(rs.getString("token"));
                user.setRoles(getAllUserRoles(user.getUserId()));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
