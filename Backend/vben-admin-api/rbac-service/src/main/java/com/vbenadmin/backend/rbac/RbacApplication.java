package com.vbenadmin.backend.rbac;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.vbenadmin.backend.rbac.mapper")
@EnableDubbo(scanBasePackages = "com.vbenadmin.backend.rbac.rpcImpl")
public class RbacApplication {

    public static void main(String[] args) {
        SpringApplication.run(RbacApplication.class, args);
    }
}
