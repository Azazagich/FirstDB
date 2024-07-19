package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.repository.RoleRepository;
import org.example.domain.Role;
import org.example.domain.RoleName;
import java.sql.*;
import java.util.List;


public class App
{
//    private static final int INDEX_COLUMN_NAME = 2;

    private static final Logger LOGGER = LogManager.getLogger(App.class.getName());

    public static void main( String[] args ) throws SQLException, ClassNotFoundException {
        //DataSource dbLoader = new DataSource();

        Role role = new Role().id(7).name(RoleName.USER);
        Role role2 = new Role().id(6).name(RoleName.ADMINISTRATOR);
        RoleRepository roleDao = new RoleRepository();
//        List<Role> roles = List.of(role, role2);
//        roleDao.delete(role2);
      //  roleDao.save(role);
       // System.out.println(roleDao.existById(5));
        //roleDao.saveAll(roles);
        // roleDao.create(role);
//        roleDao.findById(5);
//        LOGGER.info(roleDao.findById(5));
//        //roleDao.update(3, role);
//        roleDao.deleteById(5);
//        roleDao.findAll().forEach(x -> LOGGER.info("roles" + x));

//        LOGGER.info(role.getName());
//        LOGGER.info(role.getName().getUserRole());
        //User user = new User().id(10).firstName("ggg").email("vvvvv@gmail.com").password("1234");

        //UserDao userDao = new UserDao();
        //userDao.create(user);
        //userDao.update(8, user);
        //userDao.deleteById(10);
        ///LOGGER.info(userDao.findById(3));
        //userDao.findAll();
//
        //userDao.findAll().forEach(x -> LOGGER.info("users" + x));

//        ArrayList<String> names = new ArrayList<>();
//
//        Statement statement = dbLoader.openConnectionDB().createStatement();
//        ResultSet resultSet = statement.executeQuery("SELECT * FROM charactersinfo");
//        while (resultSet.next()){
//            names.add(resultSet.getString(INDEX_COLUMN_NAME));
//        }
//        dbLoader.closeConnectionDB();
//
//
//         names.stream().forEach((name) -> {LOGGER.info("Name: " + name);});


    }
}




//user: id, firstname, email, password
//role: id, name
//
//ROLE_USER, ROLE_ADMIN
//
//user and role -> many to many

//property loader


//        Properties properties = new Properties();
//        try {
//            InputStream inputStream = new FileInputStream("src/main/resources/db.properties");
//            properties.load(inputStream);
//
//            userName = properties.getProperty("userName");
//            password = properties.getProperty("password");
//            connectionURL = properties.getProperty("connectionURL");
//        }catch (IOException ex) {
//            LOGGER.warn(ex.getMessage());
//        }

//        ArrayList<String> names = new ArrayList<>();
//
//        Class.forName("org.postgresql.Driver");
//        try (Connection connection = DriverManager.getConnection(connectionURL, userName, password)){
//            System.out.println("We connected");
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM charactersinfo");
//            while (resultSet.next()){
//                names.add(resultSet.getString(INDEX_COLUMN_NAME));
//            }
//        }
//        names.stream().forEach((name) -> {LOGGER.info("Name: " + name);});
