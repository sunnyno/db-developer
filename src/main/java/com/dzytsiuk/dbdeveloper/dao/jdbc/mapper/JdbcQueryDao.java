package com.dzytsiuk.dbdeveloper.dao.jdbc.mapper;

import com.dzytsiuk.dbdeveloper.dao.QueryDao;
import com.dzytsiuk.dbdeveloper.dao.jdbc.mapper.mapper.ResultSetMapper;
import com.dzytsiuk.dbdeveloper.exception.QueryExecuteException;
import com.dzytsiuk.dbdeveloper.entity.Data;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcQueryDao implements QueryDao {
    private static final ResultSetMapper RESULT_SET_MAPPER = new ResultSetMapper();
    private DataSource dataSource;

    public JdbcQueryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public JdbcQueryDao() {
    }

    @Override
    public boolean create(String query) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);) {
            return stmt.executeUpdate() == 0;
        } catch (SQLException e) {
            throw new QueryExecuteException("Unable to execute query " + query, e);
        }
    }

    @Override
    public Data select(String query) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet resultSet = stmt.executeQuery();) {
            return RESULT_SET_MAPPER.mapResultSet(resultSet);
        } catch (SQLException e) {
            throw new QueryExecuteException("Unable to execute query " + query, e);
        }
    }

    @Override
    public int insert(String query) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);) {
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new QueryExecuteException("Unable to execute query " + query, e);
        }
    }

    @Override
    public int update(String query) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);) {
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new QueryExecuteException("Unable to execute query " + query, e);
        }
    }

    @Override
    public int delete(String query) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);) {
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new QueryExecuteException("Unable to execute query " + query, e);
        }
    }

    @Override
    public boolean drop(String query) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);) {
            return stmt.executeUpdate() == 0;
        } catch (SQLException e) {
            throw new QueryExecuteException("Unable to execute query " + query, e);
        }
    }


}
