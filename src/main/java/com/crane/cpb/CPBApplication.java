package com.crane.cpb;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Xanthos
 */
@SpringBootApplication
@MapperScan("com.crane.cpb.mapper")
@Slf4j
public class CPBApplication {

    public static void main(String[] args) {
        SpringApplication.run(CPBApplication.class, args);
        log.info("knife4j的接口文档地址为：http://localhost:8089/doc.html");
        log.info("主页：http://localhost:8089/index");
    }

}
