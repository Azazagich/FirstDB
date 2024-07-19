package org.example.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataSource {

    private String dbUserName;

    private String dbPassword;

    private String dbConnectionURL;

    private Connection connection;

    private static final Logger LOGGER = LogManager.getLogger(DataSource.class.getName());

    public DataSource() {
        isFileExist();
        setProperties();
    }

    private void setProperties() {
        Properties properties = new Properties();

        try {
            InputStream inputStream = new FileInputStream("src/main/resources/db.properties");
            properties.load(inputStream);

            dbUserName = properties.getProperty("userName");
            dbPassword = properties.getProperty("password");
            dbConnectionURL = properties.getProperty("connectionURL");

        } catch (IOException ex) {
            LOGGER.warn(ex.getMessage());
            ex.printStackTrace();
        }
    }


    private void isFileExist() {
        File dbFile = new File("src/main/resources/db.properties");
        try {
            if (!dbFile.exists()) {
                dbFile.createNewFile();
            }
        } catch (IOException ex) {
            LOGGER.warn(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public Connection openConnectionDB() {
        try {
           connection = DriverManager.getConnection(dbConnectionURL, dbUserName, dbPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        LOGGER.info("We connected");
        return connection;
    }

    public void closeConnectionDB(){
        try {
            LOGGER.info("We disconnected");
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


// How to create crud in relation many to many;
// do the same and create one to many
//connection pool existing implementations, integrate in the DataSource class

//https://stackoverflow.com/questions/63824271/how-to-do-one-to-many-relationships-in-jdbc