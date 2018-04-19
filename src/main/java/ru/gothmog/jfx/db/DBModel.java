package ru.gothmog.jfx.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DBModel {

    final Logger logger = LoggerFactory.getLogger(DBModel.class);

    Properties properties = new Properties();
    InputStream inputStream;
    String db;

    public void loadPropertiesFile(){
        try {
            String filename = "database.properties";
            inputStream = DBModel.class.getClassLoader().getResourceAsStream(filename);
            if (inputStream==null){
                logger.info("Sorry, unable to find " + filename);
                return;
            }
            properties.load(inputStream);
            db = properties.getProperty("db");
            System.out.println(" db = " + db);
        } catch (IOException ex){
            logger.error("no load database properties" + ex);
        }
    }

    PreparedStatement preparedStatement;

    public void createDataBase(){
        loadPropertiesFile();
        DBConnection dbConnection = new DBConnection();
        try {
            preparedStatement = dbConnection.mkDataBase().prepareStatement("DROP DATABASE IF EXISTS " + db);
            preparedStatement.execute();
            preparedStatement = dbConnection.mkDataBase().prepareStatement("CREATE DATABASE " + db + " ENCODING = 'UTF8'\n" +
                    "       TABLESPACE = pg_default; ");
            preparedStatement.execute();

            preparedStatement = dbConnection.mkDataBase().prepareStatement("DROP TABLE IF EXISTS customer;");
            preparedStatement.execute();
            preparedStatement = dbConnection.mkDataBase().prepareStatement("CREATE TABLE customer(\n"
                    + " id bigserial  NOT NULL,\n"
                    + " customer_name varchar(100),\n"
                    + " customer_cont_no varchar(100),\n"
                    + " customer_address varchar(512),\n"
                    + " total_buy varchar(50),\n"
                    + " creator_id  bigint,\n"
                    + " date_customer date,\n"
                    + " CONSTRAINT pk_customer_id PRIMARY KEY ( id ));"
            );
            preparedStatement.execute();
            preparedStatement = dbConnection.mkDataBase().prepareStatement("DROP TABLE IF EXISTS rma;");
            preparedStatement.execute();
            preparedStatement = dbConnection.mkDataBase().prepareStatement("CREATE TABLE rma (\n"
                    + " id bigserial  NOT NULL,\n"
                    + " rma_name varchar(100),\n"
                    + " rma_days date,\n"
                    + " comment_rma varchar(512),\n"
                    + " creator_id bigint,\n"
                    + " date_rma date,\n"
                    + " CONSTRAINT pk_rma_id PRIMARY KEY ( id ));"
            );
            preparedStatement.execute();
            preparedStatement = dbConnection.mkDataBase().prepareStatement("DROP TABLE IF EXISTS supplyer;");
            preparedStatement.execute();

            preparedStatement = dbConnection.mkDataBase().prepareStatement("DROP TABLE IF EXISTS users;");
            preparedStatement.execute();
            preparedStatement = dbConnection.mkDataBase().prepareStatement("CREATE TABLE users(\n"
                    + " id bigserial  NOT NULL,\n"
                    + " username varchar(100),\n"
                    + " full_name varchar(200),\n"
                    + " email_address varchar(100,\n"
                    + " contact_number varchar(100),\n"
                    + " salary numeric(19,2),\n"
                    + " address varchar(512),\n"
                    + " passwd varchar(45),\n"
                    + " status  bool,\n"
                    + " user_image bytea,\n"
                    + " date_reg date,\n"
                    + " creator_id bigint,\n"
                    + " CONSTRAINT pk_user_id PRIMARY KEY ( id ));"
            );
            preparedStatement.execute();
            preparedStatement = dbConnection.mkDataBase().prepareStatement("DROP TABLE IF EXISTS user_permission;");
            preparedStatement.execute();
//            preparedStatement = dbConnection.mkDataBase().prepareStatement("CREATE TABLE user_permission (\n"
//                    + " id bigserial  NOT NULL,\n"
//                    + " add_product integer,\n"
//                    + " add_supplyer integer,\n"
//                    + " add_brand integer,\n"
//                    + " add_category integer,\n"
//                    + " "
//
//            );
        } catch (SQLException ex){
            logger.error("not create database" + db + ex);
        }
    }
}
