# MongoDB Testcontainers

A robust Spring Boot application demonstrating how to use MongoDB with Testcontainers for development and testing. This project showcases best practices for working with MongoDB in a containerized environment, handling weather temperature data through a comprehensive data model and repository layer.

## 📋 Project Overview

This application manages weather temperature data using MongoDB as the database backend. The core functionality includes:

- Storing daily temperature readings (morning, afternoon, evening, night)
- Calculating seasonal and yearly temperature statistics
- Performing complex aggregation queries on temperature data
- Using Mongock for database migrations and initial data seeding
- Integration testing with Testcontainers

## 🛠️ Technology Stack

- **Java**: JDK 17+
- **Spring Boot**: Core framework
- **Spring Data MongoDB**: Database access layer
- **Mongock**: MongoDB migration tool
- **Testcontainers**: Integration testing with containerized MongoDB
- **Jackson**: JSON processing
- **JUnit 5**: Testing framework

## 🧩 Key Dependencies

```xml
<!-- These dependencies are inferred from the code -->
<dependencies>
    <!-- Spring Boot -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-mongodb</artifactId>
    </dependency>
    
    <!-- Mongock for MongoDB migrations -->
    <dependency>
        <groupId>io.mongock</groupId>
        <artifactId>mongock-springboot</artifactId>
    </dependency>
    
    <!-- Testcontainers -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-testcontainers</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.testcontainers</groupId>
        <artifactId>mongodb</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.testcontainers</groupId>
        <artifactId>junit-jupiter</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## 🚀 Getting Started

### Prerequisites

- Docker installed and running
- JDK 17 or higher
- Maven or Gradle build tool

### Setting Up the Project

1. Configure your MongoDB connection in `application.properties`:

```properties
spring.data.mongodb.uri=mongodb://admin:root@mongo_db:27015/weather
spring.data.mongodb.authentication-database=admin
spring.docker.compose.lifecycle-management=start-and-stop

mongock.migration-scan-package=org.kgromov.changelogs
mongock.enabled=true
```

2. Ensure you have a `weather_archive.json` file in your resources directory with sample data.

## 🔄 Data Model

The application works with several data models:

### DailyTemperature

The primary entity stored in MongoDB:

```java
@Document(collection = "weather_archive")
public class DailyTemperature {
    @Id
    private ObjectId id;
    @JsonDeserialize(using = MongoDateConverter.class)
    private LocalDateTime date;
    private Double morningTemperature;
    private Double afternoonTemperature;
    private Double eveningTemperature;
    private Double nightTemperature;
    
    // Getters, setters, equals, hashCode, toString...
}
```

### Support Models

Several data transfer objects (DTOs) for aggregation results:

- `DayTemperature`: Simplified temperature for a specific day
- `YearAverageTemperature`: Average temperature by year
- `SeasonTemperature`: Temperature statistics by season
- `YearBySeasonTemperature`: Seasonal temperatures grouped by year

## 📊 Key Features

### MongoDB Aggregation Pipelines

The repository layer demonstrates powerful MongoDB aggregation pipelines:

```java
@Aggregation(pipeline = {
    "{$project: {...}}",
    "{$group: {...}}",
    "{$project: {...}}"
})
List<YearAverageTemperature> getYearAverageTemperature(Sort sort);
```

### Temperature Analysis

The application can:

- Find minimum and maximum temperatures across the dataset
- Calculate average temperatures by year
- Group temperature data by seasons
- Find temperature trends across multiple years

### Database Migrations with Mongock

The application uses Mongock to manage database schema evolution and data seeding:

```java
@ChangeUnit(id="populate-weather-data", order = "001", author = "kgromov")
public class PopulateDataChangeSet {
    @Execution
    public void changeSet() throws URISyntaxException {
        // Load initial data from JSON file
        URL resource = Thread.currentThread().getContextClassLoader()
            .getResource("weather_archive.json");
        File jsonFile = Paths.get(resource.toURI()).toFile();
        var dailyTemperatures = jsonService.readFromJsonFile(
            jsonFile, new TypeReference<List<DailyTemperature>>() {});
        mongoTemplate.insertAll(dailyTemperatures);
    }
    
    @RollbackExecution
    public void rollback() {
        mongoTemplate.dropCollection(DailyTemperature.class);
    }
}
```

## 🧪 Integration Testing

The project showcases best practices for integration testing with Testcontainers:

```java
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
        assertThat(minTemperature.map(DayTemperature::getTemperature)
            .orElseThrow()).isLessThan(0.0);
    }
}
```

The `@MongoDbIntegrationTest` annotation is a meta-annotation that sets up Testcontainers:

```java
@SpringBootTest(properties = {
        "mongock.migration-scan-package=org.kgromov.changelogs",
        "mongock.enabled=true",
})
@ActiveProfiles("test")
@Import(TestcontainersConfiguration.class)
@Testcontainers(disabledWithoutDocker = true)
@DisabledInNativeImage
@DisabledInAotMode
public @interface MongoDbIntegrationTest {
}
```

## 🏃‍♂️ Running the Application

### Regular Run

```bash
./mvnw spring-boot:run
```

### Test Mode

```bash
./mvnw spring-boot:test-run -Dspring-boot.run.profiles=test
```

Alternatively, you can run the `TestMongodbTestcontainersApplication` class directly from your IDE.

## 💻 Using the API

Once running, the application exposes several endpoints for retrieving temperature data:

- Get yearly temperature averages
- Retrieve min/max temperatures
- Get temperatures grouped by seasons
- Find temperatures for specific date ranges

## 🔍 Code Highlights

### MongoDB Repository Queries

The project demonstrates various ways to query MongoDB:

1. Simple repository methods:

```java
Optional<DailyTemperature> findByDate(LocalDate date);
```

2. Custom Query annotations:

```java
@Query("{'date': {$regex: ?0}}")
List<DailyTemperature> findByDateInRange(String monthDay, Pageable pageable);
```

3. Complex aggregation pipelines:

```java
@Aggregation(pipeline = {
    // Pipeline stages for getting min temperature
})
Optional<DayTemperature> getMinTemperature();
```

4. Java stream-based post-processing:

```java
default List<YearBySeasonTemperature> getYearsBySeasonsTemperature(Pageable pageable) {
    return this.getSeasonsTemperature(pageable)
            .stream()
            .collect(groupingBy(SeasonTemperature::getYear))
            .entrySet()
            .stream()
            .map(groupByYear -> {
                var seasons = groupByYear.getValue()
                        .stream()
                        .sorted(Comparator.comparing(s -> s.getSeason().ordinal()))
                        .toList();
                return new YearBySeasonTemperature(groupByYear.getKey(), seasons);
            })
            .sorted(Comparator.comparing(YearBySeasonTemperature::getYear))
            .toList();
}
```

## 🌟 Summary

This project offers a comprehensive example of working with MongoDB in a Spring Boot application, using Testcontainers for testing and Mongock for database migrations. It demonstrates advanced MongoDB techniques like aggregation pipelines and custom queries, while applying proper software architecture principles.

The weather temperature data use case showcases real-world scenarios where MongoDB's document structure and aggregation capabilities shine, making this project both educational and practical for similar applications.

## 📚 Further Reading

- [Spring Data MongoDB Documentation](https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/)
- [Mongock Documentation](https://docs.mongock.io/)
- [Testcontainers for MongoDB](https://www.testcontainers.org/modules/databases/mongodb/)
- [MongoDB Aggregation Pipeline](https://www.mongodb.com/docs/manual/core/aggregation-pipeline/)
- [MongoDB docker image](https://hub.docker.com/_/mongo)