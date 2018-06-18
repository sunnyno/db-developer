package com.dzytsiuk.dbdeveloper.dao.jdbc.mapper;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.util.Properties;

public class DataSourceProvider {

    public DataSource getDataSource(Properties properties) {

        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL(properties.getProperty("url"));
        dataSource.setUser(properties.getProperty("user"));
        dataSource.setPassword(properties.getProperty("password"));
        return dataSource;

    }

}
