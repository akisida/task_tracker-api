package org.aleksey.task_tracker.api.factories;

import org.aleksey.task_tracker.api.dto.ProjectDTO;
import org.aleksey.task_tracker.api.dto.TaskStateDTO;
import org.aleksey.task_tracker.store.models.ProjectEntity;
import org.aleksey.task_tracker.store.models.TaskStateEntity;

public class TaskStateDtoFactory {
    public TaskStateDTO makeTaskStateDtoFactory(TaskStateEntity entity) {
        return TaskStateDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .ordinal(entity.getOrdinal())
                .build();
    }
}
