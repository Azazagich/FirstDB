package org.example.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.domain.Role;
import org.example.domain.User;
import org.example.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserCrudRepository implements CrudRepository<User, Integer> {

    private final DataSource dataSource;

    private static final Logger LOGGER = LogManager.getLogger(UserCrudRepository.class.getName());

    public UserCrudRepository(){
        dataSource = new DataSource();
    }

    private static final String SQL_INSERT_USER = "INSERT INTO \"user\" (id, firstname, email, password, roles_id) VALUES(?, ?, ?, ?, ?)";

    private static final String SQL_SELECT_USER_BY_ID = "SELECT * FROM \"user\" WHERE id = ?";

    private static final String SQL_SELECT_ALL_USERS = "SELECT * FROM \"user\"";

    private static final String SQL_SELECT_ID_IN_USER_BY_ID = "SELECT id FROM \"user\" WHERE id = ?";

    private static final String SQL_UPDATE_USER_BY_ID = "UPDATE \"user\" SET firstname = ?,  email = ?, password = ?, roles_id = ? WHERE id = ?";

    private static final String SQL_DELETE_USER_BY_ID = "DELETE FROM \"user\" WHERE id = ?";

    private static final String SQL_DELETE_USER_BY_USER = "DELETE FROM \"user\" WHERE id = ? AND firstname = ? AND email = ? AND password = ? AND roles_id = ?";

    private static final String SQL_DELETE_ALL_ROLES = "DELETE FROM \"user\"";

    @Override
    public User save(User user) {
        Connection connection = dataSource.openConnectionDB();
        try (PreparedStatement prepstmt = connection.prepareStatement(SQL_INSERT_USER)) {
            boolean defaultAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);

            prepstmt.setInt(1, user.getId());
            prepstmt.setString(2, user.getFirstName());
            prepstmt.setString(3, user.getEmail());
            prepstmt.setString(4, user.getPassword());
            prepstmt.setInt(5, user.getRole().getId());
            prepstmt.executeUpdate();

            connection.commit();
            connection.setAutoCommit(defaultAutoCommit);
        } catch (SQLException e) {
            try{
                connection.rollback();
            } catch(SQLException ex){
                LOGGER.error("Failed to roll back transaction.", ex);
            }
        } finally {
            dataSource.closeConnectionDB();
        }
        return user;
    }

    @Override
    public List<User> saveAll(List<User> users) {
        return users.stream().map(this::save).toList();
    }

    @Override
    public Optional<User> findById(Integer id) {
        Connection connection = dataSource.openConnectionDB();
        User user = new User();
        try(PreparedStatement prepstmt = connection.prepareStatement(SQL_SELECT_USER_BY_ID)){
            boolean defaultAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            prepstmt.setInt(1, id);
            ResultSet userResult = prepstmt.executeQuery();

            connection.commit();
            while (userResult.next()) {
                user.id(userResult.getInt("id"))
                        .firstName(userResult.getString("firstname"))
                        .email(userResult.getString("email"))
                        .password(userResult.getString("password"))
                        .setRole(new Role().id(userResult.getInt("roles_id")));
            }
            connection.setAutoCommit(defaultAutoCommit);
        } catch (SQLException e) {
            try{
                connection.rollback();
            } catch(SQLException ex){
                LOGGER.error("Failed to roll back transaction.", ex);
            }
        } finally {
            dataSource.closeConnectionDB();
        }

        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll(){
        Connection connection = dataSource.openConnectionDB();
        List<User> users = new ArrayList<>();

        try (Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)){
            boolean defaultAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            ResultSet userResult = statement.executeQuery(SQL_SELECT_ALL_USERS);
            connection.commit();

            while(userResult.next()) {
                User user = new User().id(userResult.getInt("id"))
                        .firstName(userResult.getString("firstname"))
                        .email(userResult.getString("email"))
                        .password(userResult.getString("password"));
                user.setRole(new Role().id(userResult.getInt("roles_id")));
                users.add(user);
            }
            connection.setAutoCommit(defaultAutoCommit);
        } catch (SQLException e) {
            try{
                connection.rollback();
            } catch(SQLException ex){
                LOGGER.error("Failed to roll back transaction.", ex);
            }
        } finally {
            dataSource.closeConnectionDB();
        }
        return users;
    }

    @Override
    public boolean existById(Integer id) {
        boolean isExist = false;
        Connection connection = dataSource.openConnectionDB();

        try (PreparedStatement prepstmt = connection.prepareStatement(SQL_SELECT_ID_IN_USER_BY_ID)) {
            connection.setAutoCommit(false);
            prepstmt.setInt(1, id);
            ResultSet resultSet = prepstmt.executeQuery();
            if (resultSet.next()){
                isExist = true;
            }

        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            dataSource.closeConnectionDB();
        }

        return isExist;
    }

    @Override
    public boolean updateId(Integer id, User user) {
        Connection connection = dataSource.openConnectionDB();

        try(PreparedStatement prepstmt = connection.prepareStatement(SQL_UPDATE_USER_BY_ID)){
            boolean defaultAutoCommit = connection.getAutoCommit();

            connection.setAutoCommit(false);

            prepstmt.setString(1, user.getFirstName());
            prepstmt.setString(2, user.getEmail());
            prepstmt.setString(3, user.getPassword());
            prepstmt.setInt(4, user.getRole().getId());
            prepstmt.setInt(5, id);
            prepstmt.executeUpdate();

            connection.commit();
            connection.setAutoCommit(defaultAutoCommit);
            return true;
        } catch (SQLException e) {
            try{
                connection.rollback();
            } catch(SQLException ex){
                LOGGER.error("Failed to roll back transaction.", ex);
            }
        } finally {
            dataSource.closeConnectionDB();
        }
        return false;
    }

    @Override
    public void deleteById(Integer id) {
        Connection connection = dataSource.openConnectionDB();

        try(PreparedStatement prepstmt = connection.prepareStatement(SQL_DELETE_USER_BY_ID)){
            boolean defaultAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            prepstmt.setInt(1, id);
            prepstmt.executeUpdate();
            connection.commit();
            connection.setAutoCommit(defaultAutoCommit);

        } catch (SQLException e) {
            try{
                connection.rollback();
            } catch(SQLException ex){
                LOGGER.error("Failed to roll back transaction.", ex);
            }
        } finally {
            dataSource.closeConnectionDB();
        }
    }

    @Override
    public void delete(User user) {
        Connection connection = dataSource.openConnectionDB();

        try(PreparedStatement prepstmt = connection.prepareStatement(SQL_DELETE_USER_BY_USER)){
            boolean defaultAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);

            prepstmt.setInt(1, user.getId());
            prepstmt.setString(2, user.getFirstName());
            prepstmt.setString(3, user.getEmail());
            prepstmt.setString(4, user.getPassword());
            prepstmt.setInt(5, user.getRole().getId());
            prepstmt.executeUpdate();

            connection.commit();
            connection.setAutoCommit(defaultAutoCommit);

        } catch (SQLException e) {
            try{
                connection.rollback();
            } catch(SQLException ex){
                LOGGER.error("Failed to roll back transaction.", ex);
            }
        } finally {
            dataSource.closeConnectionDB();
        }
    }

    @Override
    public void deleteAll() {
        Connection connection = dataSource.openConnectionDB();

        try(PreparedStatement prepstmt = connection.prepareStatement(SQL_DELETE_ALL_ROLES)){
            boolean defaultAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            prepstmt.executeUpdate();
            connection.commit();
            connection.setAutoCommit(defaultAutoCommit);

        } catch (SQLException e) {
            try{
                connection.rollback();
            } catch(SQLException ex){
                LOGGER.error("Failed to roll back transaction.", ex);
            }
        } finally {
            dataSource.closeConnectionDB();
        }
    }

    @Override
    public void deleteAll(List<User> users) {
        users.forEach(this::delete);
    }
}
