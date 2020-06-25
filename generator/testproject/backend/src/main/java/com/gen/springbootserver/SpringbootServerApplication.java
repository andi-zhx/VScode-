package com.gen.springbootserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// import com.gen.springbootserver.config.Config;

import org.mybatis.spring.annotation.MapperScan;
@SpringBootApplication
// @EnableAutoConfiguration(exclude={Config.class})
@MapperScan({"com.gen.springbootserver.mybatis.dao"})
public class SpringbootServerApplication {

	public static void main(final String[] args) {
		SpringApplication.run(SpringbootServerApplication.class, args);
	}
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
