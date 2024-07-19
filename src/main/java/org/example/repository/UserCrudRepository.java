package org.example.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

//    @Override
//    public void create(User user){
//        final String sql = "INSERT INTO \"user\" (id, firstname, email, password) VALUES(?, ?, ?, ?)";
//
//        Connection connection = dataSource.openConnectionDB();
//        try (PreparedStatement prepstmt = connection.prepareStatement(sql)) {
//            boolean defaultAutoCommit = connection.getAutoCommit();
//            connection.setAutoCommit(false);
//            prepstmt.setInt(1, user.getId());
//            prepstmt.setString(2, user.getFirstName());
//            prepstmt.setString(3, user.getEmail());
//            prepstmt.setString(4, user.getPassword());
//            prepstmt.executeUpdate();
//
//            connection.commit();
//            connection.setAutoCommit(defaultAutoCommit);
//        } catch (SQLException e) {
//            try{
//                connection.rollback();
//            } catch(SQLException ex){
//                LOGGER.error("Failed to roll back transaction.", ex);
//            }
//        } finally {
//            dataSource.closeConnectionDB();
//        }
//    }


    @Override
    public User save(User entity) {
        return null;
    }

    @Override
    public List<User> saveAll(List<User> entities) {
        return List.of();
    }

    @Override
    public Optional<User> findById(Integer id) {
        return Optional.empty();
    }

//    @Override
//    public User findById(Integer id) {
//        final String sql = "SELECT * FROM \"user\" WHERE id = ?";
//
//        Connection connection = dataSource.openConnectionDB();
//        User user = new User();
//        try(PreparedStatement prepstmt = connection.prepareStatement(sql)){
//            boolean defaultAutoCommit = connection.getAutoCommit();
//            connection.setAutoCommit(false);
//            prepstmt.setInt(1, id);
//            ResultSet userResult = prepstmt.executeQuery();
//
//            connection.commit();
//            while (userResult.next()) {
//                user.id(userResult.getInt("id"))
//                        .firstName(userResult.getString("firstname"))
//                        .email(userResult.getString("email"))
//                        .password(userResult.getString("password"));
//            }
//            connection.setAutoCommit(defaultAutoCommit);
//        } catch (SQLException e) {
//            try{
//                connection.rollback();
//            } catch(SQLException ex){
//                LOGGER.error("Failed to roll back transaction.", ex);
//            }
//        } finally {
//            dataSource.closeConnectionDB();
//        }
//        return user;
//    }

    @Override
    public List<User> findAll(){
        final String sql = "SELECT * FROM \"user\"";

        Connection connection = dataSource.openConnectionDB();
        List<User> users = new ArrayList<>();

        try (Statement statement = connection.createStatement()){
            boolean defaultAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            ResultSet resultSet = statement.executeQuery(sql);
            connection.commit();

            while(resultSet.next()) {
                users.add(new User().id(resultSet.getInt("id"))
                                    .email(resultSet.getString("email"))
                                    .firstName(resultSet.getString("firstname"))
                                    .email(resultSet.getString("email"))
                                    .password(resultSet.getString("password")));
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
        return false;
    }

    @Override
    public boolean updateId(Integer id, User entity) {
        return false;
    }

//    @Override
//    public void update(Integer id, User user) {
//        final String sql = "UPDATE \"user\" SET firstname = ?,  email = ?, password = ? WHERE id = ?";
//        Connection connection = dataSource.openConnectionDB();
//
//        try(PreparedStatement prepstmt = connection.prepareStatement(sql)){
//            boolean defaultAutoCommit = connection.getAutoCommit();
//
//            connection.setAutoCommit(false);
//
//            prepstmt.setString(1, user.getFirstName());
//            prepstmt.setString(2, user.getEmail());
//            prepstmt.setString(3, user.getPassword());
//            prepstmt.setInt(4, id);
//            prepstmt.executeUpdate();
//
//            connection.commit();
//            connection.setAutoCommit(defaultAutoCommit);
//        } catch (SQLException e) {
//            try{
//                connection.rollback();
//            } catch(SQLException ex){
//                LOGGER.error("Failed to roll back transaction.", ex);
//            }
//        } finally {
//            dataSource.closeConnectionDB();
//        }
//    }

    @Override
    public void deleteById(Integer id) {
        final String sql = "DELETE FROM \"user\" WHERE id = ?";

        Connection connection = dataSource.openConnectionDB();

        try(PreparedStatement prepstmt = connection.prepareStatement(sql)){
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
    public void delete(User entity) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void deleteAll(List<User> entities) {

    }
}
