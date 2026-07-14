package com.thai.pham.inventoryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class StorageRoutingApplication {
    public static void main(String[] args) {
        SpringApplication.run(StorageRoutingApplication.class, args);
    }
}
