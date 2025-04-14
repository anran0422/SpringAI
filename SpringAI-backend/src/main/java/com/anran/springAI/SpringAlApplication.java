package com.anran.springAI;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.anran.springAI.mapper")
@SpringBootApplication
public class SpringAlApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAlApplication.class, args);
    }

}
