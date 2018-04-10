package ru.gothmog.jfx.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBModel {

    final Logger logger = LoggerFactory.getLogger(DBModel.class);

    Properties properties = new Properties();
    InputStream inputStream;
    String db;

    public void loadPropertiesFile(){
        try {
            inputStream = new FileInputStream("database.properties");
            properties.load(inputStream);
            db = properties.getProperty("db");
        } catch (IOException ex){
            logger.error("" + ex);
        }
    }
}
