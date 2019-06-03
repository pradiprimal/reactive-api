package com.reactive.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class ReactiveApplicationStarter {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveApplicationStarter.class, args);
    }

}
