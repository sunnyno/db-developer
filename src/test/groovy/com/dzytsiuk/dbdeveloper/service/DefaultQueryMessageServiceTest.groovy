package com.dzytsiuk.dbdeveloper.service

import com.dzytsiuk.dbdeveloper.dao.DAO
import org.junit.Test

import static org.junit.Assert.assertEquals

class DefaultQueryMessageServiceTest {

    @Test
    void createTest() {
        def dao = [create: { true }] as DAO
        DefaultQueryMessageService defaultQueryMessageService = new DefaultQueryMessageService(dao: dao);
        assertEquals("Table created\n", defaultQueryMessageService.create(""))
    }

    @Test
    void insertTest() {
        def dao = [insert: { 2 }] as DAO
        DefaultQueryMessageService defaultQueryMessageService = new DefaultQueryMessageService(dao: dao);
        assertEquals("2 rows inserted\n", defaultQueryMessageService.insert(""))
    }

    @Test
    void updateTest() {
        def dao = [update: { 2 }] as DAO
        DefaultQueryMessageService defaultQueryMessageService = new DefaultQueryMessageService(dao: dao);
        assertEquals("2 rows updated\n", defaultQueryMessageService.update(""))
    }

    @Test
    void deleteTest() {
        def dao = [delete: { 2 }] as DAO
        DefaultQueryMessageService defaultQueryMessageService = new DefaultQueryMessageService(dao: dao);
        assertEquals("2 rows deleted\n", defaultQueryMessageService.delete(""))
    }

    @Test
    void dropTest() {
        def dao = [drop: { false }] as DAO
        DefaultQueryMessageService defaultQueryMessageService = new DefaultQueryMessageService(dao: dao);
        assertEquals("Error dropping table\n", defaultQueryMessageService.drop(""))
    }
}
