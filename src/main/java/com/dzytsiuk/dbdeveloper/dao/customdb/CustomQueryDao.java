package com.dzytsiuk.dbdeveloper.dao.customdb;

import com.dzytsiuk.dbdeveloper.dao.QueryDao;
import com.dzytsiuk.dbdeveloper.dao.customdb.mapper.CustomRowMapper;
import com.dzytsiuk.dbdeveloper.entity.Data;
import com.dzytsiuk.dbdeveloper.exception.QueryExecuteException;

import java.io.*;

public class CustomQueryDao implements QueryDao {
    private static final CustomRowMapper CUSTOM_ROW_MAPPER = new CustomRowMapper();
    private static final String ESCAPE_CHAR = "\\";


    private CustomDataSource customDataSource;

    public CustomQueryDao(CustomDataSource customDataSource) {
        this.customDataSource = customDataSource;

    }

    @Override
    public boolean create(String query) {
        try {
            BufferedWriter bufferedWriter = customDataSource.getWriter();
            BufferedReader bufferedReader = customDataSource.getReader();
            String sb = getResult(query, bufferedWriter, bufferedReader);
            return Integer.valueOf(sb) == 1;

        } catch (Exception e) {
            throw new QueryExecuteException("Error creating table " + query, e);
        }
    }


    @Override
    public Data select(String query) {

        try {
            InputStream inputStream = customDataSource.getInputStream();
            BufferedWriter bufferedWriter = customDataSource.getWriter();
            bufferedWriter.write(query);
            bufferedWriter.newLine();
            bufferedWriter.write(ESCAPE_CHAR);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            return CUSTOM_ROW_MAPPER.mapRow(inputStream);

        } catch (Exception e) {
            throw new QueryExecuteException("Error executing query " + query, e);
        }
    }

    @Override
    public int insert(String query) {
        try {

            BufferedWriter writer = customDataSource.getWriter();
            BufferedReader reader = customDataSource.getReader();
            String sb = getResult(query, writer, reader);
            return Integer.valueOf(sb);

        } catch (Exception e) {
            throw new QueryExecuteException("Insert error " + query, e);
        }
    }

    @Override
    public int update(String query) {
        try {
            BufferedWriter writer = customDataSource.getWriter();
            BufferedReader reader = customDataSource.getReader();

            String sb = getResult(query, writer, reader);
            return Integer.valueOf(sb);

        } catch (Exception e) {
            throw new QueryExecuteException("Update error " + query, e);
        }
    }

    @Override
    public int delete(String query) {
        try {

            BufferedWriter writer = customDataSource.getWriter();
            BufferedReader reader = customDataSource.getReader();

            String sb = getResult(query, writer, reader);
            return Integer.valueOf(sb);

        } catch (Exception e) {
            throw new QueryExecuteException("Delete error " + query, e);
        }
    }

    @Override
    public boolean drop(String query) {
        try {
            BufferedWriter writer = customDataSource.getWriter();
            BufferedReader reader = customDataSource.getReader();

            String sb = getResult(query, writer, reader);
            return Integer.valueOf(sb) == 1;

        } catch (Exception e) {
            throw new QueryExecuteException("Error dropping table " + query, e);
        }
    }

    private String getResult(String query, BufferedWriter bufferedWriter, BufferedReader bufferedReader) throws IOException {

        bufferedWriter.write(query);
        bufferedWriter.newLine();
        bufferedWriter.write(ESCAPE_CHAR);
        bufferedWriter.newLine();
        bufferedWriter.flush();

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null && !line.equals(ESCAPE_CHAR)) {
            sb.append(line);
        }
        return sb.toString();
    }
}
