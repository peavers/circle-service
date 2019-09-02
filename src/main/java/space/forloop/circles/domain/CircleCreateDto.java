package space.forloop.circles.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

/** @author Chris Turner (chris@forloop.space) */
@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class CircleCreateDto {

  @NotBlank(message = "Name value cannot be blank")
  private String name;

  private String description;

  private String featureImage;
}
