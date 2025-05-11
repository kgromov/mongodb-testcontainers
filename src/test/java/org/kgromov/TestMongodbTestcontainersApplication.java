package org.kgromov;

import org.springframework.boot.builder.SpringApplicationBuilder;

public class TestMongodbTestcontainersApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(MongodbTestcontainersApplication.class)
                .profiles("test")
                .properties(
                        "mongock.migration-scan-package=org.kgromov.changelogs",
                        "mongock.enabled=true",
                        "spring.docker.compose.skip.in-tests=false",
                        "server.port=0"
                )
                .run(args);
    }

}
