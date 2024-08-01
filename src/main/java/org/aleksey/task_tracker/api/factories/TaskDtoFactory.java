package org.aleksey.task_tracker.api.factories;

import org.aleksey.task_tracker.api.dto.ProjectDTO;
import org.aleksey.task_tracker.api.dto.TaskDTO;
import org.aleksey.task_tracker.store.models.ProjectEntity;
import org.aleksey.task_tracker.store.models.TaskEntity;
import org.springframework.scheduling.config.Task;

public class TaskDtoFactory {
    public TaskDTO makeTaskDtoFactory(TaskEntity entity) {
        return TaskDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .description(entity.getDescription())
                .build();
    }
}
