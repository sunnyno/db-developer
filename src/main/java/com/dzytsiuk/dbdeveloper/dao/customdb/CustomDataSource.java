package com.dzytsiuk.dbdeveloper.dao.customdb;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Properties;

public class CustomDataSource implements AutoCloseable {

    private Socket socket;

    public CustomDataSource(String host, int port) {
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {

            throw new RuntimeException("Unable to establish connection", e);
        }
    }

    public CustomDataSource(Properties properties) {
        this(properties.getProperty("host"), Integer.valueOf(properties.getProperty("port")));
    }

    public InputStream getInputStream() throws IOException {
        return socket.getInputStream();
    }

    public OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }

    @Override
    public void close() throws Exception {
        socket.close();
    }
}
