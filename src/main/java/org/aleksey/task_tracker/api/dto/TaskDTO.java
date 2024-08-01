package org.aleksey.task_tracker.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskDTO {
    @NonNull
    long id;
    @NonNull
    String name;
    @NonNull
    String description;
    @NonNull
    @JsonProperty("created_at")
    Instant createdAt;
}
