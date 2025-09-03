/**
 * https://www.baeldung.com/java-validation
 */
package nl.justid.todobackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto {

    private UUID id;

    @NotBlank()
    @NotNull(message = "Name cannot be null")
    private String name;

    private String description;

    private Set<String> tags;

    private OffsetDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate deadline;

    private boolean completed;
}
