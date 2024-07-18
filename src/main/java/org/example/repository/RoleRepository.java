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


public class RoleRepository implements CrudRepository<Role, Integer> {

    private final DataSource dataSource;

    private static final Logger LOGGER = LogManager.getLogger(RoleRepository.class);

    public RoleRepository() {
        dataSource = new DataSource();
    }

    @Override
    public Role save(Role role) {
        if (role != null) {
            final String sql = "INSERT INTO role(id, name) VALUES(?, ?)";
            Connection connection = dataSource.openConnectionDB();

            try (PreparedStatement prepstmt = connection.prepareStatement(sql)) {
                boolean defaultAutoCommit = connection.getAutoCommit();

                connection.setAutoCommit(false);

                prepstmt.setInt(1, role.getId());
                prepstmt.setString(2, role.getName().getUserRole());
                prepstmt.executeUpdate();

                connection.commit();
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
        }
        return role;
    }

    @Override
    public List<Role> saveAll(List<Role> roles) {
        return roles.stream().map(this::save).toList();
    }

    @Override
    public Optional<Role> findById(Integer id) {
        final String sql = "SELECT * FROM role WHERE id = ?";

        Connection connection = dataSource.openConnectionDB();
        Role role = new Role();

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

    @Override
    public List<Role> findAll() {
        final String sql = "SELECT * FROM role";

        Connection connection = dataSource.openConnectionDB();
        List<Role> roles = new ArrayList<>();

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

    @Override
    public boolean existById(Integer id) {
        final String sql = "SELECT id FROM role WHERE id = ?";
        boolean isExist = false;

        Connection connection = dataSource.openConnectionDB();

        try (PreparedStatement prepstmt = connection.prepareStatement(sql)) {
            boolean defaultAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            prepstmt.setInt(1, id);
            ResultSet resultSet = prepstmt.executeQuery();

            if (resultSet.next()){
                isExist = true;
            }

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

        return isExist;
    }

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

    @Override
    public void deleteById(Integer id) {
        final String sql = "DELETE FROM role WHERE id = ?";

        Connection connection = dataSource.openConnectionDB();

        try (PreparedStatement prepstmt = connection.prepareStatement(sql)) {
            boolean defaultAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            prepstmt.setInt(1, id);
            prepstmt.executeUpdate();
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

    @Override
    public void delete(Role role) {
        final String sql = "DELETE FROM role WHERE id = ? AND name = ?";

        Connection connection = dataSource.openConnectionDB();

        try (PreparedStatement prepstmt = connection.prepareStatement(sql)) {
            boolean defaultAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            prepstmt.setInt(1, role.getId());
            prepstmt.setString(2, role.getName().getUserRole());
            prepstmt.executeUpdate();
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

    @Override
    public void deleteAll() {
        final String sql = "DELETE FROM role";

        Connection connection = dataSource.openConnectionDB();

        try (PreparedStatement prepstmt = connection.prepareStatement(sql)) {
            boolean defaultAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            deleteRoleIdFromUser();
            prepstmt.executeUpdate();
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

    @Override
    public void deleteAll(List<Role> roles) {
        roles.forEach(this::delete);
    }

    public void deleteRoleIdFromUser(){
        final String sql = "DELETE role_id FROM \"user\"";

        Connection connection = dataSource.openConnectionDB();

        try (PreparedStatement prepstmt = connection.prepareStatement(sql)) {
            boolean defaultAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            prepstmt.executeUpdate();
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


// findByName в services создати нового юзера і в юзері назву
