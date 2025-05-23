package org.kgromov;

import io.mongock.runner.springboot.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableMongock
@SpringBootApplication
public class MongodbTestcontainersApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongodbTestcontainersApplication.class, args);
    }

}
