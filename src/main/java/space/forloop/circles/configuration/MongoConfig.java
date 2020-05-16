package space.forloop.circles.configuration;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoDatabase;
import com.mongodb.reactivestreams.client.gridfs.GridFSBucket;
import com.mongodb.reactivestreams.client.gridfs.GridFSBuckets;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Chris Turner (chris@forloop.space)
 */
@Configuration
class MongoConfig {

    /**
     * This is required for GridFS to function. Just reads the values configured for the standard mongo connection.
     */
    @Bean
    public GridFSBucket gridFSBucket(final MongoProperties properties, final MongoClient client) {

        final String databaseName = properties.getDatabase();
        final MongoDatabase database = client.getDatabase(databaseName);

        return GridFSBuckets.create(database);
    }

}
