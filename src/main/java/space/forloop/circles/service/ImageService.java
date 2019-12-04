package space.forloop.circles.service;

import org.springframework.data.mongodb.gridfs.GridFsResource;
import reactor.core.publisher.Mono;

import java.io.InputStream;

/**
 * @author Chris Turner (chris@forloop.space)
 */
public interface ImageService {

    Mono<String> uploadFromStream(String filename, InputStream inputStream);

    Mono<GridFsResource> getImageFromDatabase(String id);

}
