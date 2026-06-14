package com.thai.pham.inventoryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class StorageRoutingApplication {
    public static void main(String[] args) {
        SpringApplication.run(StorageRoutingApplication.class, args);
    }
}
