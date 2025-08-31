package com.bridgeon.app.database;

import com.bridgeon.app.BaseTest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DatabaseConnectionTest extends BaseTest {

    @Autowired
    private EntityManager entityManager;

    // DB connection status
    @Test
    void databaseConnectionTest() {
        Object result = entityManager
                .createNativeQuery("SELECT 'JPA Connection OK' as message")
                .getSingleResult();

        Assertions.assertNotNull(result);
        Assertions.assertEquals("JPA Connection OK", result);
        System.out.println("JPA Connection OK:" + result);
    }

    // DB info
    @Test
    void databaseInfoTest() {
        Object databaseName = entityManager
                .createNativeQuery("SELECT CURRENT_DATABASE() as current_db")
                .getSingleResult();

        Object databaseVersion = entityManager
                .createNativeQuery("SELECT VERSION() as db_version")
                .getSingleResult();

        System.out.println("Database name: " + databaseName);
        System.out.println("Database version: " + databaseVersion);

        Assertions.assertNotNull(databaseName);
        Assertions.assertNotNull(databaseVersion);
    }
}
