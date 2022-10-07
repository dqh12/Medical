package com.henu.user.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.henu.user.mapper")
public class UserConfig {
}
