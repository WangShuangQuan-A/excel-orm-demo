package com.wsq.excel.ormdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class OrmdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrmdemoApplication.class, args);
    }

}
