package com.rolinsf.novelbackend;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ljq4946
 */
@SpringBootApplication
@MapperScan("com.rolinsf.novelbackend.dao.mapper")
@Slf4j
public class NovelBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(NovelBackendApplication.class, args);
    }

}
