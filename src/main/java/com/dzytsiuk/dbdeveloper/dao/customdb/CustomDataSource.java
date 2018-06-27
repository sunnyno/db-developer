package com.dzytsiuk.dbdeveloper.dao.customdb;

import java.io.*;
import java.net.Socket;
import java.util.Properties;

public class CustomDataSource {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public CustomDataSource(String host, int port) {
        try {
            socket = new Socket(host, port);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {

            throw new RuntimeException("Unable to establish connection", e);
        }
    }

    public CustomDataSource(Properties properties) {
        this(properties.getProperty("host"), Integer.valueOf(properties.getProperty("port")));
    }

    public BufferedReader getReader() {
        return bufferedReader;
    }

    public BufferedWriter getWriter() {
        return bufferedWriter;
    }

    public InputStream getInputStream() throws IOException {
        return socket.getInputStream();
    }

    public void close() {
        try {
            bufferedReader.close();
            bufferedWriter.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing socket", e);
        }
    }
}
