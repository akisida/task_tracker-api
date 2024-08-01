package org.aleksey.task_tracker.api.factories;


import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.aleksey.task_tracker.api.dto.ProjectDTO;
import org.aleksey.task_tracker.api.dto.TaskStateDTO;
import org.aleksey.task_tracker.store.models.ProjectEntity;
import org.aleksey.task_tracker.store.models.TaskStateEntity;
import org.springframework.stereotype.Component;

@Component
public class ProjectDtoFactory {

    public ProjectDTO makeProjectDto(ProjectEntity entity) {
        return ProjectDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
