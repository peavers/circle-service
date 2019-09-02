package space.forloop.circles.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.time.Instant;

/** @author Chris Turner (chris@forloop.space) */
@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Post {

  @Id private String id;

  @NotBlank private String message;

  private String circleId;

  private String ownerId;

  @Builder.Default private long created = Instant.now().toEpochMilli();
}
