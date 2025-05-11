package org.kgromov.changelogs;

import com.fasterxml.jackson.core.type.TypeReference;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.kgromov.model.DailyTemperature;
import org.kgromov.service.ImportJsonService;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;

@Profile("test")
@ChangeUnit(id="populate-weather-data", order = "001", author = "kgromov")
public class PopulateDataChangeSet {
    private final MongoTemplate mongoTemplate;
    private final ImportJsonService jsonService;

    public PopulateDataChangeSet(MongoTemplate mongoTemplate,
                                 ImportJsonService jsonService) {
        this.mongoTemplate = mongoTemplate;
        this.jsonService = jsonService;
    }

    @Execution
    public void changeSet() throws URISyntaxException {
        URL resource = Thread.currentThread().getContextClassLoader().getResource("weather_archive.json");
        File jsonFile = Paths.get(resource.toURI()).toFile();
        var dailyTemperatures = jsonService.readFromJsonFile(jsonFile, new TypeReference<List<DailyTemperature>>() {});
        mongoTemplate.insertAll(dailyTemperatures);
    }

    @RollbackExecution
    public void rollback() {
        mongoTemplate.dropCollection(DailyTemperature.class);
    }
}
