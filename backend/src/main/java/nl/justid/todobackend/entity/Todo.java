/**
 *
 * https://www.baeldung.com/jpa-postgres-persisting-uuids
 */

package nl.justid.todobackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.*;
import java.util.*;

//@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "todos")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String description;

    private Set<String> tags = new HashSet<>();

    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime createdAt;

    private LocalDate deadline;

    private boolean completed;
}
