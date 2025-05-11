package org.kgromov;

import org.junit.jupiter.api.Test;
import org.kgromov.model.DayTemperature;
import org.kgromov.repository.DailyTemperatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

//@ActiveProfiles("test")
//@Import(TestcontainersConfiguration.class)
//@SpringBootTest(properties = {
//        "mongock.migration-scan-package=org.kgromov.changelogs",
//        "mongock.enabled=true"
//})
@MongoDbIntegrationTest
class MongodbTestcontainersApplicationTests {
    @Autowired private DailyTemperatureRepository temperatureRepository;


    @Test
    void testCount() {
        long count = temperatureRepository.count();

        assertThat(count).isGreaterThan(5_000);
    }

    @Test
    void testMinTemperature() {
        var minTemperature = temperatureRepository.getMinTemperature();

        assertThat(minTemperature).isPresent();
        assertThat(minTemperature.map(DayTemperature::getTemperature).orElseThrow()).isLessThan(0.0);
    }
}
