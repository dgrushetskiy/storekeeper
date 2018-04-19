package ru.gothmog.jfx.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    final Logger logger = LoggerFactory.getLogger(DBConnection.class);

    Properties properties = new Properties();
    InputStream inputStream;

    public Connection connection;

    String url;
    String user;
    String pass;
    String db;

    public void loadPropertiesFile() {
        try {
            String filename = "database.properties";
            inputStream = DBConnection.class.getClassLoader().getResourceAsStream(filename);
            if (inputStream==null){
                logger.info("Sorry, unable to find " + filename);
                return;
            }
            properties.load(inputStream);
            db = properties.getProperty("db");
            url = "jdbc:postgresql://" + properties.getProperty("host") + ":" + properties.getProperty("port") + "/" + db;
            user = properties.getProperty("user");
            pass = properties.getProperty("password");
        }catch (IOException ex){
            logger.error("no connect db" + ex);
        }
    }

    public Connection mkDataBase() {
        loadPropertiesFile();
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("url = " + url);
            System.out.println("user = " + user);
            System.out.println("pasword = " + pass);
            connection = DriverManager.getConnection(url,user,pass);
        } catch (ClassNotFoundException|SQLException ex){
           logger.error("user password url no connect " + ex);
        }
        return connection;
    }

    public Connection getConnection(){
        loadPropertiesFile();
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url,user,pass);
        }catch (ClassNotFoundException|SQLException ex){
            logger.error("user password url no connect" + ex);
        }
        return connection;
    }
}
