package com.unionman.springbootdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.zxp.esclientrhl.annotation.EnableESTools;

@SpringBootApplication
@EnableESTools
public class SpringbootDemoApplication {

    public static void main(String[] args) throws Exception{
        SpringApplication.run(SpringbootDemoApplication.class);
    }
}
