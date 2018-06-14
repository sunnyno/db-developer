package com.dzytsiuk.dbdeveloper.datasource

import com.mysql.cj.jdbc.MysqlDataSource
import org.junit.Test
import static org.junit.Assert.assertEquals;

class DataSourceProviderTest {
    @Test
    void getDataSourceTest() {
        Properties properties = new Properties()
        properties.setProperty("url", "test")
        properties.setProperty("user", "user")
        properties.setProperty("password", "111")
        def expectedDataSource = new MysqlDataSource(url: "test", user: "user", password: "111")
        def actualDataSource = new DataSourceProvider().getDataSource(properties) as MysqlDataSource
        assertEquals(expectedDataSource.url, actualDataSource.url)
        assertEquals(expectedDataSource.user, actualDataSource.user)
        assertEquals(expectedDataSource.password, actualDataSource.password)
    }
}
