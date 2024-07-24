package org.example.repository;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.domain.Role;
import org.example.domain.RoleName;
import org.example.utils.DataSource;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * RoleRepository provides CRUD operations for Role entities using a DataSource for database connections.
 */
public class RoleCrudRepository implements CrudRepository<Role, Integer> {

    private final DataSource dataSource;

    private static final Logger LOGGER = LogManager.getLogger(RoleCrudRepository.class);

    public RoleCrudRepository() {
        dataSource = new DataSource();
    }

    /**
     * Saves a given Role to the database.
     *
     * @param role the role to save
     * @return the saved role
     */
    @Override
    public Role save(Role role) {
        if (role != null) {
            final String sql = "INSERT INTO role(id, name) VALUES(?, ?)";
            statementExecute(sql, role);
        }
        return role;
    }

    /**
     * Saves all given roles to the database.
     *
     * @param roles the roles to save
     * @return the list of saved roles
     */
    @Override
    public List<Role> saveAll(List<Role> roles) {
        return roles.stream().map(this::save).toList();
    }

    /**
     * Finds a role by its ID.
     *
     * @param id the ID of the role to find
     * @return an Optional containing the found role, or empty if not found
     */
    @Override
    public Optional<Role> findById(Integer id) {
        final String sql = "SELECT * FROM role WHERE id = ?";
        Role role = new Role();

        Connection connection = dataSource.openConnectionDB();
        try (PreparedStatement prepstmt = connection.prepareStatement(sql)) {
            boolean defaultAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);

            prepstmt.setInt(1, id);
            ResultSet roleResult = prepstmt.executeQuery();

            connection.commit();

            while (roleResult.next()) {
                role.id(roleResult.getInt("id"))
                        .name(RoleName.valueOf(roleResult.getString("name")));
            }

            connection.setAutoCommit(defaultAutoCommit);

        } catch (SQLException e) {
            try {
                LOGGER.error(e);
                connection.rollback();
            } catch (SQLException ex) {
                LOGGER.error("Failed to roll back transaction.", ex);
            }
        } finally {
            dataSource.closeConnectionDB();
        }
        return Optional.of(role);
    }

    /**
     * Finds all roles in the database.
     *
     * @return a list of all roles
     */
    @Override
    public List<Role> findAll() {
        final String sql = "SELECT * FROM role";
        List<Role> roles = new ArrayList<>();

        Connection connection = dataSource.openConnectionDB();

        try (Statement statement = connection.createStatement()) {
            boolean defaultAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            ResultSet resultSet = statement.executeQuery(sql);
            connection.commit();

            while (resultSet.next()) {
                roles.add(new Role().id(resultSet.getInt("id"))
                        .name(RoleName.valueOf(resultSet.getString("name"))));
            }
            connection.setAutoCommit(defaultAutoCommit);
        } catch (SQLException e) {
            try {
                LOGGER.error(e);
                connection.rollback();
            } catch (SQLException ex) {
                LOGGER.error("Failed to roll back transaction.", ex);
            }
        } finally {
            dataSource.closeConnectionDB();
        }
        return roles;
    }

    /**
     * Checks if a role exists by its ID.
     *
     * @param id the ID of the role to check
     * @return true if the role exists, false otherwise
     */
    @Override
    public boolean existById(Integer id) {
        final String sql = "SELECT id FROM role WHERE id = ?";
        boolean isExist = false;

        Connection connection = dataSource.openConnectionDB();

        try (PreparedStatement prepstmt = connection.prepareStatement(sql)) {
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


    /**
     * Updates a role's ID with the provided role information.
     *
     * @param id the ID of the role to update
     * @param role the role information to update
     * @return true if the update was successful, false otherwise
     */
    @Override
    public boolean updateId(Integer id, Role role) {
        final String sql = "UPDATE role SET name = ? WHERE id = ?";

        Connection connection = dataSource.openConnectionDB();

        try (PreparedStatement prepstmt = connection.prepareStatement(sql)) {
            boolean defaultAutoCommit = connection.getAutoCommit();

            connection.setAutoCommit(false);

            prepstmt.setString(1, role.getName().getUserRole());
            prepstmt.setInt(2, role.getId());
            prepstmt.executeUpdate();

            connection.commit();
            connection.setAutoCommit(defaultAutoCommit);
            return true;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                LOGGER.error("Failed to roll back transaction.", ex);
            }
        } finally {
            dataSource.closeConnectionDB();
        }

        return false;
    }

    /**
     * Deletes a role by its ID.
     *
     * @param id the ID of the role to delete
     */
    @Override
    public void deleteById(Integer id) {
        final String roleDelete = "DELETE FROM role WHERE id = ?";
        final String cascadeDelete = "UPDATE \"user\" SET roles_id = NULL WHERE role.id = roles_id";

        statementExecute(cascadeDelete);
        statementExecute(roleDelete, id);
    }

    /**
     * Deletes a role from the database.
     *
     * @param role the role to delete
     */
    @Override
    public void delete(Role role) {
        final String roleDelete = "DELETE FROM role WHERE id = ? AND name = ?";
        final String cascadeDelete = "UPDATE \"user\" SET roles_id = NULL WHERE roles_id = ?";

        statementExecute(cascadeDelete, role.getId());
        statementExecute(roleDelete, role);
    }

    /**
     * Deletes all roles from the database.
     */
    @Override
    public void deleteAll() {
        final String roleDelete = "DELETE FROM role";
        final String cascadeDelete = "UPDATE \"user\" SET roles_id = NULL";

        statementExecute(cascadeDelete);
        statementExecute(roleDelete);
    }

    /**
     * Deletes all given roles from the database.
     *
     * @param roles the roles to delete
     */
    @Override
    public void deleteAll(List<Role> roles) {
        roles.forEach(this::delete);
    }


//////////////////////////////////////////////////////////////////////////////////////////////
    private void statementExecute(String sql){
        Connection connection = dataSource.openConnectionDB();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            boolean defaultAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            preparedStatement.executeUpdate();
            connection.commit();
            connection.setAutoCommit(defaultAutoCommit);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                LOGGER.error("Failed to roll back transaction.", ex);
            }
        } finally {
            dataSource.closeConnectionDB();
        }
    }

    private void statementExecute(String sql, Integer id){
        Connection connection = dataSource.openConnectionDB();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            boolean defaultAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
            connection.setAutoCommit(defaultAutoCommit);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                LOGGER.error("Failed to roll back transaction.", ex);
            }
        } finally {
            dataSource.closeConnectionDB();
        }
    }

    private void statementExecute(String sql, Role role){
        Connection connection = dataSource.openConnectionDB();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            boolean defaultAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);

            preparedStatement.setInt(1, role.getId());
            preparedStatement.setString(2, role.getName().getUserRole());
            preparedStatement.executeUpdate();
            connection.commit();
            connection.setAutoCommit(defaultAutoCommit);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                LOGGER.error("Failed to roll back transaction.", ex);
            }
        } finally {
            dataSource.closeConnectionDB();
        }
    }
}