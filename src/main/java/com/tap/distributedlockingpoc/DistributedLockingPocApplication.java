package com.tap.distributedlockingpoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
public class DistributedLockingPocApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistributedLockingPocApplication.class, args);
    }

}
