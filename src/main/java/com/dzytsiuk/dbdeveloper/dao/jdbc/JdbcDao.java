package com.dzytsiuk.dbdeveloper.dao.jdbc;

import com.dzytsiuk.dbdeveloper.exception.QueryExecuteException;
import com.dzytsiuk.dbdeveloper.dao.DAO;
import com.dzytsiuk.dbdeveloper.entity.ResultSetData;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcDao implements DAO {
    private static final ResultSetMapper RESULT_SET_MAPPER = new ResultSetMapper();
    private DataSource dataSource;

    public JdbcDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean create(String query) {
        try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(query);) {
            return stmt.executeUpdate() == 0;
        } catch (SQLException e) {
            throw new QueryExecuteException("Unable to execute query " + query, e);
        }
    }

    @Override
    public ResultSetData select(String query) {
        try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(query);) {
            ResultSet resultSet = stmt.executeQuery();
            return RESULT_SET_MAPPER.mapResultSet(resultSet);
        } catch (SQLException e) {
            throw new QueryExecuteException("Unable to execute query " + query, e);
        }
    }

    @Override
    public int insert(String query) {
        try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(query);) {
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new QueryExecuteException("Unable to execute query " + query, e);
        }
    }

    @Override
    public int update(String query) {
        try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(query);) {
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new QueryExecuteException("Unable to execute query " + query, e);
        }
    }

    @Override
    public int delete(String query) {
        try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(query);) {
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new QueryExecuteException("Unable to execute query " + query, e);
        }
    }

    @Override
    public boolean drop(String query) {
        try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(query);) {
            return stmt.executeUpdate() == 0;
        } catch (SQLException e) {
            throw new QueryExecuteException("Unable to execute query " + query, e);
        }
    }


}
