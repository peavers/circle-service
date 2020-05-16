package space.forloop.circles.service;

import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import com.mongodb.reactivestreams.client.gridfs.AsyncInputStream;
import com.mongodb.reactivestreams.client.gridfs.GridFSBucket;
import com.mongodb.reactivestreams.client.gridfs.helpers.AsyncStreamHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.InputStream;
import java.util.Objects;

/**
 * @author Chris Turner (chris@forloop.space)
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final GridFSBucket gridFsBucket;

    private final GridFsTemplate gridFsTemplate;

    @Override
    public Mono<String> uploadFromStream(final String filename, final InputStream inputStream) {

        final AsyncInputStream asyncInputStream = AsyncStreamHelper.toAsyncInputStream(inputStream);

        return Mono.from(
                gridFsBucket.uploadFromStream(
                        filename, asyncInputStream, createUploadOptions(filename)))
                .map(ObjectId::toHexString);
    }

    private GridFSUploadOptions createUploadOptions(final String filename) {

        final GridFSUploadOptions gridFSUploadOptions = new GridFSUploadOptions();

        final Document document = new Document();
        document.append("title", filename);
        document.append("contentType", MediaType.IMAGE_JPEG_VALUE);
        gridFSUploadOptions.metadata(document);

        return gridFSUploadOptions;
    }

    @Override
    public Mono<GridFsResource> getImageFromDatabase(final String id) {

        return Mono.fromCallable(
                () ->
                        this.gridFsTemplate.getResource(
                                Objects.requireNonNull(
                                        this.gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id))))
                                        .getFilename()));
    }

}
