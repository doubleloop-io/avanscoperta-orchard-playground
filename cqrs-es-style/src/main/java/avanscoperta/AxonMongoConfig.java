package avanscoperta;

import com.mongodb.MongoClient;
import org.axonframework.eventhandling.saga.repository.SagaStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.mongo.DefaultMongoTemplate;
import org.axonframework.mongo.eventhandling.saga.repository.MongoSagaStore;
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MongoProperties.class)
public class AxonMongoConfig {

    private final MongoClient client;
    private final MongoProperties properties;

    public AxonMongoConfig(MongoClient client, MongoProperties properties) {
        this.client = client;
        this.properties = properties;
    }

    @Bean
    public EventStorageEngine eventStorageEngine() {
        return new MongoEventStorageEngine(new DefaultMongoTemplate(client, properties.getDatabase()));
    }

    @Bean
    public SagaStore sagaStore() {
        return new MongoSagaStore(new DefaultMongoTemplate(client, properties.getDatabase()));
    }
}
