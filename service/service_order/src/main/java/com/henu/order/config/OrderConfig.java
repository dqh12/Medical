package com.henu.order.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan("com.henu.order.mapper")
public class OrderConfig {

}
