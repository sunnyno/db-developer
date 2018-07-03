package com.dzytsiuk.dbdeveloper.exception;

public class QueryExecuteException extends RuntimeException {

    public QueryExecuteException(String message, Throwable cause) {
        super(message, cause);
    }
}
