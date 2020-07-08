package com.cloudera.poverty;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cloudera.*.mapper")
public class PovertyApplication {

    public static void main(String[] args) {
        SpringApplication.run(PovertyApplication.class, args);
    }

}
