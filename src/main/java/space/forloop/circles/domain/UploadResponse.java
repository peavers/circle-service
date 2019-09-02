package space.forloop.circles.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/** @author Chris Turner (chris@forloop.space) */
@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class UploadResponse {

  private String id;
}
