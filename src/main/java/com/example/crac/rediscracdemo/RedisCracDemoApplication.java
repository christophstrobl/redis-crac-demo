package com.example.crac.rediscracdemo;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@SpringBootApplication
public class RedisCracDemoApplication {


	public static void main(String[] args) throws IOException {


		SpringApplication.run(RedisCracDemoApplication.class, args);
	}

	@Bean
	LettuceConnectionFactory connectionFactory() {
		return new LettuceConnectionFactory(new RedisStandaloneConfiguration("host.docker.internal"));
	}


}
