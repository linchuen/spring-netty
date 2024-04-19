package com.cooba;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyApplication {
    public static void main(String[] args) {
        SpringApplication.run(NettyApplication.class,args);
    }
}