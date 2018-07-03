package com.dzytsiuk.dbdeveloper.dao.jdbc;

import com.dzytsiuk.dbdeveloper.dao.QueryDao;
import com.dzytsiuk.dbdeveloper.dao.jdbc.mapper.ResultSetMapper;
import com.dzytsiuk.dbdeveloper.entity.Data;
import com.dzytsiuk.dbdeveloper.exception.QueryExecuteException;

import java.sql.*;
import java.util.Properties;

public class JdbcQueryDao implements QueryDao {
    private static final ResultSetMapper RESULT_SET_MAPPER = new ResultSetMapper();
    private static final String URL = "url";

    private Properties properties;

    public JdbcQueryDao() {
    }

    public JdbcQueryDao(Properties properties) {
        this.properties = properties;
    }

    @Override
    public boolean create(String query) {
        try (Connection connection = DriverManager.getConnection(properties.getProperty(URL), properties);
             PreparedStatement stmt = connection.prepareStatement(query)) {
            return stmt.executeUpdate() == 0;
        } catch (SQLException e) {
            throw new QueryExecuteException("Unable to execute query " + query, e);
        }
    }

    @Override
    public Data select(String query) {
        try (Connection connection = DriverManager.getConnection(properties.getProperty(URL), properties);
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet resultSet = stmt.executeQuery();) {
            return RESULT_SET_MAPPER.mapResultSet(resultSet);
        } catch (SQLException e) {
            throw new QueryExecuteException("Unable to execute query " + query, e);
        }
    }

    @Override
    public int insert(String query) {
        try (Connection connection = DriverManager.getConnection(properties.getProperty(URL), properties);
             PreparedStatement stmt = connection.prepareStatement(query);) {
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new QueryExecuteException("Unable to execute query " + query, e);
        }
    }

    @Override
    public int update(String query) {
        try (Connection connection = DriverManager.getConnection(properties.getProperty(URL), properties);
             PreparedStatement stmt = connection.prepareStatement(query);) {
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new QueryExecuteException("Unable to execute query " + query, e);
        }
    }

    @Override
    public int delete(String query) {
        try (Connection connection = DriverManager.getConnection(properties.getProperty(URL), properties);
             PreparedStatement stmt = connection.prepareStatement(query);) {
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new QueryExecuteException("Unable to execute query " + query, e);
        }
    }

    @Override
    public boolean drop(String query) {
        try (Connection connection = DriverManager.getConnection(properties.getProperty(URL), properties);
             PreparedStatement stmt = connection.prepareStatement(query);) {
            return stmt.executeUpdate() == 0;
        } catch (SQLException e) {
            throw new QueryExecuteException("Unable to execute query " + query, e);
        }
    }
}
