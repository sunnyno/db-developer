package com.dzytsiuk.dbdeveloper.service

import com.dzytsiuk.dbdeveloper.dao.jdbc.JdbcQueryDao
import com.dzytsiuk.dbdeveloper.entity.Data
import com.dzytsiuk.dbdeveloper.entity.Result
import com.dzytsiuk.dbdeveloper.locator.ServiceLocator
import org.junit.Test

import static org.junit.Assert.assertTrue;

class QueryMessageServiceTest {

    @Test
    void executeTest() {
        String[] query = ["select * from user;"]
        def data = new Data(columnNames: ['id', 'name'], data: [['1', '2'], ['test1', 'test2']])
        def result = new Result(data: data, hasData: true, message: '2 rows fetched\n')
        List<Result> expectedResult = new ArrayList<>();
        expectedResult.add(result)
        def queryDao = [select: { q -> data }] as JdbcQueryDao
        ServiceLocator.registerService("queryDao", queryDao)

        QueryMessageService queryMessageService = new QueryMessageService()
        def actualResult = queryMessageService.execute(query) as List<Result>
        expectedResult.each { assertTrue(actualResult.remove(it)) }


    }

    @Test
    void applyFunctionTest() {
        def queryTypes = ["insert", "update", "delete", "drop", "create"]
        def expectedResult = ["1 row inserted\n", "1 row updated\n", "1 row deleted\n", "Table dropped\n", "Table created\n"]
        def queryDao = [insert: { q -> 1 }, update: { q -> 1 },
                        delete: { q -> 1 }, drop: { q -> true }, create: { q -> true }
        ] as JdbcQueryDao

        ServiceLocator.registerService("queryDao", queryDao)

        QueryMessageService queryMessageService = new QueryMessageService()
        List<String> actualResult = new ArrayList<>();
        queryTypes.each { actualResult.add(queryMessageService.applyFunction(it)) }

        expectedResult.each { assertTrue(actualResult.remove(it)) }
    }
}
