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
import mate.academy.internetshop.exeption.DataProcessingException;
import mate.academy.internetshop.exeption.LoginExistExeption;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;

@Dao
public class UserDaoJdbcImpl extends AbstractDao<User> implements UserDao {
    public UserDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public User create(User user) throws DataProcessingException {
        String query = "INSERT INTO internet_shop.users(login, password, email, name,token)"
                + " VALUES(?, ?, ?, ?, ?);";
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getName());
            preparedStatement.setString(5, user.getToken());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            while (rs.next()) {
                user.setUserId(rs.getLong(1));
            }
            updateUserRoles(user);
            return user;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to update User Roles: " + e);
        }
    }

    public void checkUserLoginForRegistration(String login) throws LoginExistExeption {
        String query = "SELECT * FROM internet_shop.users;";
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                if (rs.getString("login").equals(login)) {
                    throw new SQLException();
                }
            }
        } catch (SQLException e) {
            throw new LoginExistExeption("Failed to update User Roles: " + e);
        }
    }

    @Override
    public void checkUserLoginForLogin(String login) throws DataProcessingException {
        String query = "SELECT login FROM internet_shop.users;";
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ResultSet rs = preparedStatement.executeQuery();
            List<String> logins = new ArrayList<>();
            while (rs.next()) {
                logins.add(rs.getString("login"));
            }
            if (logins.stream().noneMatch(l -> l.equals(login))) {
                throw new SQLException();
            }
        } catch (SQLException e) {
            throw new LoginExistExeption("Failed to update User Roles: " + e);
        }
    }

    private void updateUserRoles(User user) throws DataProcessingException {
        deleteAllUserRoles(user);
        String query = "INSERT INTO internet_shop.users_roles (user_id, role_id) values (?, ?);";
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            for (Role role : user.getRoles()) {
                if (role.equals(Role.of("ADMIN"))) {
                    preparedStatement.setLong(2, 1L);
                } else {
                    preparedStatement.setLong(2, 2L);
                }
                preparedStatement.setLong(1, user.getUserId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to create User: " + e);
        }
    }

    private void deleteAllUserRoles(User user) throws DataProcessingException {
        String query = "DELETE FROM internet_shop.users_roles WHERE user_id=?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, user.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete All User Roles: " + e);
        }
    }

    @Override
    public Optional<User> get(Long userId) throws DataProcessingException {
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
            throw new DataProcessingException("Failed to get user : " + e);
        }
    }

    private Set<Role> getAllUserRoles(long userId) throws DataProcessingException {
        Set<Role> roles = new HashSet<>();
        String query = "SELECT internet_shop.users_roles.user_id,"
                + " internet_shop.users_roles.role_id, internet_shop.roles.role_name"
                + " FROM internet_shop.users_roles INNER JOIN internet_shop.roles "
                + "ON internet_shop.roles.role_id=internet_shop.users_roles.role_id "
                + "WHERE user_id=?;";
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
            throw new DataProcessingException("Failed to all user roles: " + e);
        }
    }

    @Override
    public User update(User user) throws DataProcessingException {
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
            throw new DataProcessingException("Failed to update user by id: " + e);
        }
    }

    @Override
    public boolean deleteById(Long userId) throws DataProcessingException {
        String query = "DELETE FROM internet_shop.users WHERE user_id=?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, userId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete user by id: " + e);
        }
    }

    @Override
    public boolean delete(User user) throws DataProcessingException {
        String query = "DELETE FROM internet_shop.users WHERE user_id=?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, user.getUserId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete user: " + e);
        }
    }

    @Override
    public Optional<User> findByLogin(String login) throws DataProcessingException {
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
                user.setLogin(rs.getString("login"));
            }
            user.setRoles(getAllUserRoles(user.getUserId()));
            return Optional.of(user);
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to find users by login: " + e);
        }
    }

    @Override
    public Optional<User> getByToken(String token) throws DataProcessingException {
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
            throw new DataProcessingException("Failed to get users by token: " + e);
        }
    }

    @Override
    public List<User> getAll() throws DataProcessingException {
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
            throw new DataProcessingException("Failed to get all users: " + e);
        }
    }
}
