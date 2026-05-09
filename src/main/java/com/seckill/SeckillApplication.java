package com.seckill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 秒杀系统启动入口。
 * 统一开启 Spring Boot 自动配置，并扫描 MyBatis Mapper 接口。
 */
@SpringBootApplication
@MapperScan("com.seckill.dao")
public class SeckillApplication {

    /**
     * 应用主函数。
     */
    public static void main(String[] args) {
        SpringApplication.run(SeckillApplication.class, args);
    }
}
