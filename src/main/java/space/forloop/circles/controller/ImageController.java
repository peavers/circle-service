package space.forloop.circles.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import space.forloop.circles.domain.UploadResponse;
import space.forloop.circles.service.ImageService;

/**
 * @author Chris Turner (chris@forloop.space)
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping(value = "/v1/circles/image/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public Mono<GridFsResource> getImage(@PathVariable final String imageId) {

        return imageService.getImageFromDatabase(imageId);
    }

    @PostMapping(
            value = "/v1/circles/image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<UploadResponse> uploadImage(@RequestBody final Flux<Part> parts) {

        return parts
                .collectMap(Part::name)
                .flatMap(
                        formData -> {
                            final FilePart filePart = (FilePart) formData.get("image");

                            return DataBufferUtils.join(filePart.content())
                                    .flatMap(
                                            buffer ->
                                                    imageService.uploadFromStream(
                                                            filePart.filename(), buffer.asInputStream()))
                                    .flatMap(id -> Mono.just(UploadResponse.builder().id(id).build()));
                        });
    }

}
