package com.be.monolithic.controller;

import org.testcontainers.containers.MySQLContainer;

public abstract class AbstractContainerBaseTest {
    static final MySQLContainer MY_SQL_CONTAINER;

    static {
        MY_SQL_CONTAINER = new MySQLContainer("mysql:latest");
        MY_SQL_CONTAINER.start();
    }

}
