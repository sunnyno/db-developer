package com.dzytsiuk.dbdeveloper.handler

import com.dzytsiuk.dbdeveloper.exception.QueryExecuteException
import com.dzytsiuk.dbdeveloper.service.DefaultQueryMessageService
import org.junit.Test

import static groovy.test.GroovyAssert.shouldFail
import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

class RequestHandlerTest {
    @Test
    void getFunctionTest() {
        def queryTypes = ["insert", "update", "delete", "drop", "create"]
        def service = [insert: { "insert" }, update: { "update" },
                       delete: { "delete" }, drop: { "drop" }, create: { "create" }
        ] as DefaultQueryMessageService
        RequestHandler requestHandler = new RequestHandler()
        requestHandler.setQueryMessageService(service)
        def collect = queryTypes.each { requestHandler.getFunction(it).apply(it) }.collect()
        queryTypes.each { assertTrue(collect.remove(it)) }

    }

    @Test
    void getFunctionExceptionTest() {
        RequestHandler requestHandler = new RequestHandler()

        def message = shouldFail(QueryExecuteException) {
            requestHandler.getFunction("test")
        }
        assertEquals("Unable to parse query", message.message)
    }
}
