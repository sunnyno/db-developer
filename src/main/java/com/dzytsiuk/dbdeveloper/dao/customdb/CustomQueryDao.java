package com.dzytsiuk.dbdeveloper.dao.customdb;

import com.dzytsiuk.dbdeveloper.dao.QueryDao;
import com.dzytsiuk.dbdeveloper.dao.customdb.mapper.CustomRowMapper;
import com.dzytsiuk.dbdeveloper.entity.Data;
import com.dzytsiuk.dbdeveloper.exception.QueryExecuteException;

import java.io.*;

public class CustomQueryDao implements QueryDao {
    private static final CustomRowMapper CUSTOM_ROW_MAPPER = new CustomRowMapper();
    private static final String ESCAPE_CHAR = "\\";
    private static final String SEPARATOR = System.getProperty("line.separator");
    private static final String TMP_PATH = "src/main/resources/tmp/" + "tmp.xml";

    private CustomDataSource customDataSource;

    public CustomQueryDao(CustomDataSource customDataSource) {
        this.customDataSource = customDataSource;

    }

    @Override
    public boolean create(String query) {
        try {
            OutputStream outputStream = customDataSource.getOutputStream();
            InputStream inputStream = customDataSource.getInputStream();
            System.out.println(query);
            String sb = getResult(query, outputStream, inputStream);
            return Integer.valueOf(sb) == 1;

        } catch (Exception e) {
            throw new QueryExecuteException("Error executing query " + query, e);
        }
    }


    @Override
    public Data select(String query) {
        File tmp = new File(TMP_PATH);

        try {
            OutputStream outputStream = customDataSource.getOutputStream();
            InputStream inputStream = customDataSource.getInputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write(query);
            bufferedWriter.newLine();
            bufferedWriter.write(ESCAPE_CHAR);
            bufferedWriter.newLine();
            bufferedWriter.flush();


            try (FileWriter fileWriter = new FileWriter(tmp, true);) {

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null && !line.equals(ESCAPE_CHAR)) {
                    if (!line.equals(ESCAPE_CHAR) && !line.equals(SEPARATOR)) {
                        fileWriter.append(line);
                    }
                }

            }
            Data data = CUSTOM_ROW_MAPPER.mapRow(tmp);
            return data;

        } catch (Exception e) {
            throw new QueryExecuteException("Error executing query " + query, e);
        } finally {
            tmp.delete();
        }
    }

    @Override
    public int insert(String query) {
        try {

            OutputStream outputStream = customDataSource.getOutputStream();
            InputStream inputStream = customDataSource.getInputStream();
            String sb = getResult(query, outputStream, inputStream);
            return Integer.valueOf(sb);

        } catch (IOException e) {
            throw new QueryExecuteException("Error executing query " + query, e);
        }
    }

    @Override
    public int update(String query) {
        try {
            OutputStream outputStream = customDataSource.getOutputStream();
            InputStream inputStream = customDataSource.getInputStream();

            String sb = getResult(query, outputStream, inputStream);
            return Integer.valueOf(sb);

        } catch (Exception e) {
            throw new QueryExecuteException("Error executing query " + query, e);
        }
    }

    @Override
    public int delete(String query) {
        try {

            OutputStream outputStream = customDataSource.getOutputStream();
            InputStream inputStream = customDataSource.getInputStream();
            String sb = getResult(query, outputStream, inputStream);
            return Integer.valueOf(sb + "");

        } catch (IOException e) {
            throw new QueryExecuteException("Error executing query " + query, e);
        }
    }

    @Override
    public boolean drop(String query) {
        try {
            OutputStream outputStream = customDataSource.getOutputStream();
            InputStream inputStream = customDataSource.getInputStream();

            String sb = getResult(query, outputStream, inputStream);
            return Integer.valueOf(sb) == 1;

        } catch (Exception e) {
            throw new QueryExecuteException("Error executing query " + query, e);
        }
    }

    private String getResult(String query, OutputStream outputStream, InputStream inputStream) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        bufferedWriter.write(query);
        bufferedWriter.newLine();
        bufferedWriter.write(ESCAPE_CHAR);
        bufferedWriter.newLine();
        bufferedWriter.flush();

        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null && !line.equals(ESCAPE_CHAR)) {
            sb.append(line);
        }
        return sb.toString();
    }
}
