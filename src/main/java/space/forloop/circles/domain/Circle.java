package space.forloop.circles.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Chris Turner (chris@forloop.space)
 */
@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Circle {

    @Id
    private String id;

    @NotBlank(message = "Name value cannot be blank")
    @Indexed(unique = true)
    private String name;

    private String description;

    private String featureImage;

    @Builder.Default
    private Set<String> members = new HashSet<>();

    @Builder.Default
    private Set<String> owners = new HashSet<>();

    @Builder.Default
    private long created = Instant.now().toEpochMilli();

}
